package com.zhongpengcheng.spine.io.pojo.attachment;

import com.zhongpengcheng.spine.io.v35.enums.AttachmentType;
import lombok.Builder;
import lombok.Data;

/**
 * @author ZhongPengCheng
 * @since 2021-03-02 18:48:00
 */
@Builder
@Data
public class BoundingBoxAttachment implements IAttachment {
    private String slot;
    private String name;
    @Builder.Default
    private String type = AttachmentType.BOUNDING_BOX.getCode();
    /**
     * if null, name
     */
    private String path;
    /**
     * 非必须参数
     */
    private String color;
    private Integer vertexCount;
    private Float[] uvs;
    private Short[] triangles;
    /**
     * 顶点向量
     */
    private Integer[] bones;
    private Float[] vertices;
    private Integer worldVerticesLength;
    private Integer hullLength;
    /*如果nonessential为true则读取*/
    private Short[] edges;
    private Float width;
    private Float height;
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
