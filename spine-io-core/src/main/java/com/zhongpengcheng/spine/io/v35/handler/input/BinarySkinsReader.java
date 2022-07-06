package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.v35.enums.AttachmentType;
import com.zhongpengcheng.spine.io.v35.pojo.Skin;
import com.zhongpengcheng.spine.io.v35.pojo.Vertices;
import com.zhongpengcheng.spine.io.v35.pojo.attachment.*;
import com.zhongpengcheng.spine.io.SpineDataInputStream;
import com.zhongpengcheng.spine.util.ColorUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 22:38:00
 */
@Slf4j
public class BinarySkinsReader extends AbstractBinaryReader {
    /**
     * 默认皮肤名称
     */
    private static final String DEFAULT_SKIN = "default";

    @Override
    public String getName() {
        return super.getName() + ":皮肤读取器";
    }

    @Override
    public boolean handle(BinaryReaderContext ctx) throws IOException {
        this.readDefaultSkin(ctx);
        this.readAnotherSkin(ctx);

        return true;
    }

    /**
     * 读默认皮肤
     */
    private void readDefaultSkin(BinaryReaderContext ctx) throws IOException {
        Skin skin = this.readSkin(DEFAULT_SKIN, ctx);
        if (skin != null) ctx.getSkins().add(skin);
    }

    /**
     * 读其他皮肤
     */
    private void readAnotherSkin(BinaryReaderContext ctx) throws IOException {
        SpineDataInputStream input = ctx.getInput();
        for (int i = 0, skinCount = input.readInt(true); i < skinCount; i++) {
            Skin skin = readSkin(input.readString(), ctx);
            if (skin != null) ctx.getSkins().add(skin);
        }
    }

    /**
     * 读取单个皮肤
     * @param skinName 皮肤名称
     * @param ctx 上下文实例
     */
    private Skin readSkin(String skinName, BinaryReaderContext ctx) throws IOException  {
        SpineDataInputStream input = ctx.getInput();
        int slotCount = input.readInt(true);
        if (slotCount == 0) {
            return null;
        }

        // key: slotName, value: attachments
        Map<String, List<IAttachment>> attachments = new LinkedHashMap<>();

        for (int i = 0; i < slotCount; i++) {
            int slotIndex = input.readInt(true);
            String slotName = ctx.getSlotName(slotIndex);
            List<IAttachment> slotAttachment = AttachmentsReader.of(ctx, slotName, slotIndex).read();
            attachments.put(slotName, slotAttachment);
        }

        return Skin.builder()
                .name(skinName)
                .attachments(attachments)
                .build();
    }

    @Slf4j
    public static class AttachmentsReader {
        /**
         * 上下文实例
         */
        private final BinaryReaderContext ctx;
        /**
         * 插槽名称
         */
        private final String slotName;
        /**
         * 插槽下标
         */
        private final int slotIndex;
        /**
         * 输入流程
         */
        private final SpineDataInputStream input;

        public static AttachmentsReader of(BinaryReaderContext ctx, String slotName, int slotIndex) {
            return new AttachmentsReader(ctx, slotName, slotIndex);
        }

        public AttachmentsReader(BinaryReaderContext ctx, String slotName, int slotIndex) {
            this.ctx = ctx;
            this.slotName = slotName;
            this.slotIndex = slotIndex;
            this.input = ctx.getInput();
        }

        public List<IAttachment> read() throws IOException {
            List<IAttachment> attachments = new LinkedList<>();
            for (int j = 0, attachmentCount = input.readInt(true); j < attachmentCount; j++) {
                String attachmentName = input.readString();
                String name = Optional.ofNullable(input.readString()).orElse(attachmentName);
                AttachmentType type = AttachmentType.values()[input.readByte()];
                log.debug("读取到{}类型的附件", type);
                switch (type) {
                    case REGION:
                        attachments.add(this.readRegionAttachment(name));
                        break;
                    case BOUNDING_BOX:
                        attachments.add(this.readBoundingBoxAttachment(name));
                        break;
                    case MESH:
                        attachments.add(this.readMeshAttachment(name));
                        break;
                    case LINKED_MESH:
                        attachments.add(this.readLinkedMeshAttachment(name));
                        break;
                    case PATH:
                        attachments.add(this.readPathAttachment(name));
                        break;
                    default:
                }
            }

            return attachments;
        }

        private RegionAttachment readRegionAttachment(String attachmentName) throws IOException {
            return RegionAttachment.builder()
                    .name(attachmentName)
                    .path(Optional.ofNullable(input.readString()).orElse(attachmentName))
                    .rotation(input.readFloat())
                    .x(input.readFloat())
                    .y(input.readFloat())
                    .scaleX(input.readFloat())
                    .scaleY(input.readFloat())
                    .width(input.readFloat())
                    .height(input.readFloat())
                    .color(ColorUtils.rgba8888ToHexColor(input.readInt()))
                    .slot(slotName)
                    .slotIndex(this.slotIndex)
                    .build();
        }

        private BoundingBoxAttachment readBoundingBoxAttachment(String attachmentName) throws IOException {
            int vertexCount = input.readInt(true);
            Vertices vertices = this.readVertices(vertexCount);
            return BoundingBoxAttachment.builder()
                    .worldVerticesLength(vertexCount << 1)
                    .vertices(vertices.getVertices())
                    .bones(vertices.getBones())
                    .color(ColorUtils.rgba8888ToHexColor(ctx.isNonessential() ? input.readInt() : 0))
                    .name(attachmentName)
                    .slot(slotName)
                    .slotIndex(this.slotIndex)
                    .build();
        }

        private MeshAttachment readMeshAttachment(String attachmentName) throws IOException {
            String path = Optional.ofNullable(input.readString()).orElse(attachmentName);
            int color = input.readInt();
            int vertexCount = input.readInt(true);
            Float[] uvs = input.readFloatArray(vertexCount << 1);
            Short[] triangles = input.readShortArray(input.readInt(true));
            Vertices vertices = readVertices(vertexCount);
            int hullLength = input.readInt(true);

            Short[] edges = null;
            float width = 0;
            float height = 0;
            if (ctx.isNonessential()) {
                edges = input.readShortArray(input.readInt(true));
                width = input.readFloat();
                height = input.readFloat();
            }

            return MeshAttachment.builder()
                    .name(attachmentName)
                    .path(path)
                    .color(ColorUtils.rgba8888ToHexColor(color))
                    .bones(vertices.getBones())
                    .vertices(vertices.getVertices())
                    .worldVerticesLength(vertexCount << 1)
                    .triangles(triangles)
                    .uvs(uvs)
                    .hull(hullLength << 1)
                    .edges(edges)
                    .width(width)
                    .height(height)
                    .type(AttachmentType.MESH.getCode())
                    .slot(slotName)
                    .slotIndex(this.slotIndex)
                    .build();
        }

        private MeshAttachment readLinkedMeshAttachment(String attachmentName) throws IOException {
            String path = Optional.ofNullable(input.readString()).orElse(attachmentName);
            int color = input.readInt();
            String skinName = input.readString();
            String parent = input.readString();
            boolean inheritDeform = input.readBoolean();

            float width = 0;
            float height = 0;
            if (ctx.isNonessential()) {
                width = input.readFloat();
                height = input.readFloat();
            }

            return MeshAttachment.builder()
                    .path(path)
                    .parent(parent)
                    .color(ColorUtils.rgba8888ToHexColor(color))
                    .deform(inheritDeform)
                    .width(width)
                    .height(height)
                    .type(AttachmentType.LINKED_MESH.getCode())
                    .name(attachmentName)
                    .slot(slotName)
                    .slotIndex(this.slotIndex)
                    .build();
        }

        private PathAttachment readPathAttachment(String attachmentName) throws IOException {
            boolean closed = input.readBoolean();
            boolean constantSpeed = input.readBoolean();
            int vertexCount = input.readInt(true);
            Vertices vertices = readVertices(vertexCount);
            Float[] lengths = new Float[vertexCount / 3];
            for (int i = 0; i < lengths.length; i++) {
                lengths[i] = input.readFloat();
            }
            int color = ctx.isNonessential() ? input.readInt() : 0;

            return PathAttachment.builder()
                    .name(attachmentName)
                    .closed(closed)
                    .constantSpeed(constantSpeed)
                    .worldVerticesLength(vertexCount << 1)
                    .vertices(vertices.getVertices())
                    .bones(vertices.getBones())
                    .lengths(lengths)
                    .color(ColorUtils.rgba8888ToHexColor(color))
                    .type(AttachmentType.PATH.getCode())
                    .slot(slotName)
                    .slotIndex(this.slotIndex)
                    .build();
        }

        private Vertices readVertices(int vertexCount) throws IOException {
            int verticesLength = vertexCount << 1;
            Vertices.VerticesBuilder builder = Vertices.builder();
            if (!input.readBoolean()) {
                builder.vertices(input.readFloatArray(verticesLength));
                return builder.build();
            }
            ArrayList<Float> weights = new ArrayList<>(verticesLength * 3 * 3);
            ArrayList<Integer> bones = new ArrayList<>(verticesLength * 3);
            for (int i = 0; i < vertexCount; i++) {
                int boneCount = input.readInt(true);
                bones.add(boneCount);
                for (int ii = 0; ii < boneCount; ii++) {
                    bones.add(input.readInt(true));
                    weights.add(input.readFloat());
                    weights.add(input.readFloat());
                    weights.add(input.readFloat());
                }
            }

            return builder.vertices(weights.toArray(new Float[0]))
                    .bones(bones.toArray(new Integer[0]))
                    .build();
        }
    }
}
