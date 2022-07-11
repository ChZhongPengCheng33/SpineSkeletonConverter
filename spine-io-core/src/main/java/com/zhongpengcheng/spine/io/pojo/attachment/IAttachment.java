package com.zhongpengcheng.spine.io.pojo.attachment;

/**
 * @author ZhongPengCheng
 * @since 2021-03-02 18:48:00
 */
public interface IAttachment {
    /**
     * 获取附件名称
     */
    String getAttachmentName();

    default boolean weighted() {
        return false;
    }

    default Float[] getVertices() {
        return new Float[0];
    }

    default float[] vertices() {
        Float[] vertices = getVertices();
        if (vertices == null) return new float[0];
        float[] unboxArray = new float[vertices.length];
        for (int i = 0; i < vertices.length; i++) {
            unboxArray[i] = vertices[i];
        }
        return unboxArray;
    }

    int getSlotIndex();
}
