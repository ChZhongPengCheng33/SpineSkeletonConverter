package com.zhongpengcheng.spine.fastjson;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.exception.SpineIOException;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializerFactory;
import com.zhongpengcheng.spine.io.pojo.*;
import com.zhongpengcheng.spine.io.pojo.attachment.*;
import com.zhongpengcheng.spine.io.v35.serializer.ISerializer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * JSON构造器，将{@link Skeleton}转换为{@link JSONObject}
 *
 * @author zhongpengcheng
 * @since 2022-02-16 16:20:52
 **/
@Slf4j
public class FastjsonSerializer implements ISerializer<JSONObject> {

    private Skeleton skeleton;
    private final JSONObject result;

    public FastjsonSerializer() {
        this.result = new JSONObject(true);
    }

    @Override
    public JSONObject serialize(Skeleton skeleton) {
        if (skeleton == null) throw new SpineIOException("skeleton is null.");
        this.skeleton = skeleton;

        this.serializeHead();
        this.serializeBones();
        this.serializeSlots();
        this.serializeIks();
        this.serializeTransforms();
        this.serializePaths();
        this.serializeSkins();
        this.serializeEvents();
        this.serializeAnimations();

        return this.result;
    }

    private void serializeHead() {
        JSONObject container = new JSONObject(true);
        Head head = skeleton.getHead();

        container.put("hash", head.getHash());
        container.put("spine", head.getVersion());
        container.put("width", head.getWidth());
        container.put("height", head.getHeight());

        if (head.getNonessential()) {
            container.put("fps", head.getFps());
            container.put("images", head.getImages());
        }

        this.result.put("skeleton", container);
    }

    private void serializeBones() {
        List<Bone> bones = skeleton.getBones();
        if (CollectionUtil.isEmpty(bones)) return;

        JSONArray container = new JSONArray(bones.size());

        bones.forEach(bone -> {
            JSONObject temp = new JSONObject(true);
            temp.put("name", bone.getName());
            temp.put("parent", bone.getParent());
            temp.put("length", bone.getLength());
            temp.put("rotation", bone.getRotation());
            temp.put("x", bone.getX());
            temp.put("y", bone.getY());
            temp.put("scaleX", bone.getScaleX());
            temp.put("scaleY", bone.getScaleY());
            temp.put("shearX", bone.getShearX());
            temp.put("shearY", bone.getShearY());
            temp.put("transform", bone.getTransformMode());
            temp.put("color", bone.getColor());

            container.add(temp);
        });

        result.put("bones", container);
    }

    private void serializeSlots() {
        List<Slot> slots = skeleton.getSlots();
        if (CollectionUtil.isEmpty(slots)) return;

        JSONArray container = new JSONArray(slots.size());

        slots.forEach(slot -> {
            JSONObject temp = new JSONObject(true);

            temp.put("name", slot.getName());
            temp.put("bone", slot.getBone());
            temp.put("color", slot.getColor());
            temp.put("attachment", slot.getAttachment());
            temp.put("blend", slot.getBlend());

            container.add(temp);
        });

        result.put("slots", container);
    }

    private void serializeIks() {
        List<Ik> iks = skeleton.getIks();
        if (CollectionUtil.isEmpty(iks)) return;

        JSONArray container = new JSONArray(iks.size());

        iks.forEach(ik -> {
            JSONObject temp = new JSONObject(true);
            temp.put("name", ik.getName());
            temp.put("order", ik.getOrder());
            temp.put("bone", ik.getBones().get(0));
            temp.put("target", ik.getTarget());
            temp.put("bendPositive", ik.getBendPositive());
            temp.put("mix", ik.getMix());

            container.add(temp);
        });

        result.put("ik", container);
    }

    private void serializeTransforms() {
        List<Transform> transforms = skeleton.getTransforms();
        if (CollectionUtil.isEmpty(transforms)) return;

        JSONArray container = new JSONArray(transforms.size());

        transforms.forEach(transform -> {
            JSONObject temp = new JSONObject(true);
            temp.put("name", transform.getName());
            temp.put("order", transform.getOrder());
            temp.put("bones", transform.getBones());
            temp.put("target", transform.getTarget());
            temp.put("rotation", transform.getRotation());
            temp.put("x", transform.getX());
            temp.put("y", transform.getY());
            temp.put("scaleX", transform.getScaleX());
            temp.put("scaleY", transform.getScaleY());
            temp.put("rotateMix", transform.getRotateMix());
            temp.put("translateMix", transform.getTranslateMix());
            temp.put("scaleMix", transform.getScaleMix());
            temp.put("shearMix", transform.getShearMix());

            container.add(temp);
        });

        result.put("transform", container);
    }

    private void serializePaths() {
        List<Path> paths = skeleton.getPaths();
        if (CollectionUtil.isEmpty(paths)) return;

        JSONArray container = new JSONArray(paths.size());

        paths.forEach(path -> {
            JSONObject temp = new JSONObject(true);

            temp.put("name", path.getName());
            temp.put("order", path.getOrder());
            temp.put("bones", path.getBones());
            temp.put("target", path.getTarget());
            temp.put("positionMode", path.getPositionMode());
            temp.put("spacingMode", path.getSpacingMode());
            temp.put("rotation", path.getRotation());
            temp.put("position", path.getPosition());
            temp.put("spacing", path.getSpacing());
            temp.put("translateMix", path.getTranslateMix());

            container.add(temp);
        });

        result.put("path", container);
    }

    private void serializeSkins() {
        List<Skin> skins = skeleton.getSkins();
        if (CollectionUtil.isEmpty(skins)) return;

        // skins
        JSONObject container = new JSONObject(true);
        skins.forEach(skin -> {
            // default
            JSONObject aSkin = new JSONObject(true);
            skin.getAttachments().forEach((slotName, attachments) -> {
                // eye
                JSONObject attachmentMap = new JSONObject(true);
                attachments.forEach(attachment -> {
                    JSONObject attachmentContent = new FastjsonAttachmentSerializer(attachment, skin.getName()).serialize();
                    // 如果是MeshAttachment还需要皮肤名称
                    if (attachment instanceof MeshAttachment) attachmentContent.put("skin", skin.getName());
                    attachmentMap.put(attachment.getAttachmentName(), attachmentContent);
                });

                aSkin.put(slotName, attachmentMap);
            });
            container.put(skin.getName(), aSkin);
        });

        result.put("skins", container);
    }

    private void serializeEvents() {
        List<Event> events = skeleton.getEvents();
        if (CollectionUtil.isEmpty(events)) return;

        JSONObject container = new JSONObject(true);

        events.forEach(event -> {
            JSONObject temp = new JSONObject(true);

            temp.put("int", event.getAInt());
            temp.put("float", event.getAFloat());
            temp.put("string", event.getAString());

            container.put(event.getName(), temp);
        });

        result.put("events", container);
    }

    private void serializeAnimations() {
        List<Animation> animations = skeleton.getAnimations();
        if (CollectionUtil.isEmpty(animations)) return;
        JSONObject container = new JSONObject(true);

        animations.forEach(animation -> {
            JSONObject animationContent = new JSONObject(true);

            animation.getTimelineMap().forEach((timelineType, timelines) -> {
                TimelineBlockSerializer serializer = TimelineBlockSerializerFactory.serializerOf(timelineType);
                if (serializer == null) {
                    log.warn("未匹配到对应的时间线块序列化器，timelineType={}", timelineType);
                    return;
                }
                JSONObject serializeRet = serializer.serialize(timelines);
                animationContent.put(timelineType, serializeRet);
            });

            container.put(animation.getName(), animationContent);
        });
        result.put("animations", container);
    }

    public static class FastjsonAttachmentSerializer {

        private final IAttachment attachment;
        private final JSONObject result;
        private final String skinName;

        private FastjsonAttachmentSerializer(IAttachment attachment, String skinName) {
            this.attachment = attachment;
            this.skinName = skinName;
            this.result = new JSONObject(true);
        }

        public JSONObject serialize() {
            if (attachment == null) throw new NullPointerException("attachment is null.");
            if (attachment instanceof BoundingBoxAttachment) this.serializeBoundingBox();
            if (attachment instanceof MeshAttachment) this.serializeMesh();
            if (attachment instanceof PathAttachment) this.serializePath();
            if (attachment instanceof RegionAttachment) this.serializeRegion();
            if (result.size() > 0) return result;
            throw new SpineIOException("unknown attachment type: " + attachment.getClass());
        }

        private void serializeBoundingBox() {
            BoundingBoxAttachment attachment = (BoundingBoxAttachment) this.attachment;
            result.put("type", "boundingbox");
            result.put("vertexCount", attachment.getVertexCount());
            result.put("vertices", attachment.getVertices());
        }

        private void serializeMesh() {
            MeshAttachment attachment = (MeshAttachment) this.attachment;
            result.put("type", attachment.getType());
            result.put("path", attachment.getPath());
            result.put("color", attachment.getColor());
            result.put("width", attachment.getWidth());
            result.put("height", attachment.getHeight());
            result.put("parent", attachment.getParent());
            result.put("deform", attachment.getDeform());
            result.put("vertices", attachment.getVertices());
            result.put("skin", this.skinName);
            result.put("uvs", attachment.getUvs());
            result.put("triangles", attachment.getTriangles());
            result.put("hull", attachment.getHull());
            result.put("edges", attachment.getEdges());
        }

        private void serializePath() {
            PathAttachment attachment = (PathAttachment) this.attachment;
            result.put("type", attachment.getType());
            result.put("closed", attachment.getClosed());
            result.put("constantSpeed", attachment.getConstantSpeed());
            result.put("vertexCount", attachment.getVertexCount());
            result.put("vertices", attachment.getVertices());
            result.put("lengths", attachment.getLengths());
            result.put("color", attachment.getColor());
        }

        private void serializeRegion() {
            RegionAttachment attachment = (RegionAttachment) this.attachment;
            result.put("path", attachment.getPath());
            result.put("x", attachment.getX());
            result.put("y", attachment.getY());
            result.put("scaleX", attachment.getScaleX());
            result.put("scaleY", attachment.getScaleY());
            result.put("rotation", attachment.getRotation());
            result.put("width", attachment.getWidth());
            result.put("height", attachment.getHeight());
            result.put("color", attachment.getColor());
        }
    }
}
