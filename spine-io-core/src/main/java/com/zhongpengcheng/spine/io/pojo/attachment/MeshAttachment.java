package com.zhongpengcheng.spine.io.pojo.attachment;

import com.zhongpengcheng.spine.io.v35.enums.AttachmentType;
import lombok.Builder;
import lombok.Data;

/**
 * @author ZhongPengCheng
 * @since 2021-03-02 18:48:00
 */
@Data
@Builder
public class MeshAttachment implements IAttachment {
    private String slot;
    @Builder.Default
    private String type = AttachmentType.MESH.getCode();
    private String name;
    private String path;
    private String color;
    private Float width;
    private Float height;
    private String parent;
    private Boolean deform;
    private Float[] uvs;
    private Short[] triangles;
    private Integer hull;
    private Short[] edges;
    private Integer[] bones;
    private Float[] vertices;
    private Integer worldVerticesLength;
    private int slotIndex;

    @Override
    public boolean weighted() {
        return bones != null;
    }

    @Override
    public Float[] getVertices() {
        return vertices;
    }

    @Override
    public String getAttachmentName() {
        return this.name;
    }
}
