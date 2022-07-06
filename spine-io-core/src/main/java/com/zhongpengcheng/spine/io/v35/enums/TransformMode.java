package com.zhongpengcheng.spine.io.v35.enums;

/**
 * 确定骨骼如何从父骨骼继承世界变换
 *
 * @author ZhongPengCheng
 * @since 2021-03-02 1:56:00
 */
public enum TransformMode {
    /**
     * 正常
     */
    NORMAL("normal", "正常变换", 0),
    /**
     * 仅翻译
     */
    ONLY_TRANSLATION("onlyTranslation", "仅翻译", 1),
    /**
     * 没有旋转或反射
     */
    NO_ROTATION_OR_REFLECTION("noRotationOrReflection", "没有旋转或反射", 2),
    /**
     * 无缩放
     */
    NO_SCALE("noScale", "无缩放", 3),
    /**
     * 无缩放或反射
     */
    NO_SCALE_OR_REFLECTION("noScaleOrReflection", "无缩放或反射", 4);

    /**
     * 转换模式在skel文件中的key
     */
    private final String code;
    /**
     * 转换模式描述
     */
    private final String desc;

    private final Integer index;

    TransformMode(String code, String desc, Integer index) {
        this.code = code;
        this.desc = desc;
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getIndex() {
        return index;
    }

    public static TransformMode ofCode(final String code) {
        for (TransformMode transformMode : values()) {
            if (transformMode.getCode().equals(code)) return transformMode;
        }
        return NORMAL;
    }
}
