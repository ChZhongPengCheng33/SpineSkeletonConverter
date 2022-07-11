package com.zhongpengcheng.spine.io.pojo.attachment;

import lombok.Builder;
import lombok.Data;

/**
 * @author ZhongPengCheng
 * @since 2021-03-02 18:48:00
 */
@Builder
@Data
public class PathAttachment implements IAttachment {
    private String slot;
    private String type;
    private String name;
    private Float[] lengths;
    private Boolean closed;
    private Boolean constantSpeed;
    private String color;
    private Integer vertexCount;
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
