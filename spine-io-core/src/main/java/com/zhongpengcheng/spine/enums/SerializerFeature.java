package com.zhongpengcheng.spine.enums;

import java.util.Arrays;

/**
 * 序列化特性
 *
 * @author zhongpengcheng
 * @since 2022-07-11 14:33:28
 **/
public enum SerializerFeature {
    /**
     * 写入非必须参数
     */
    NONESSENTIAL,
    /**
     * 更美观的格式输出，仅在输出JSON文件时适用
     */
    PRETTY_FORMAT;

    public final int mask;

    SerializerFeature() {
        mask = (1 << ordinal());
    }

    public int getMask() {
        return mask;
    }

    public static boolean isEnabled(int features, SerializerFeature feature) {
        return (features & feature.mask) != 0;
    }

    public static SerializerFeature[] of(int mask) {
        return Arrays.stream(values())
                .filter(item -> (item.mask & mask) > 0)
                .toArray(SerializerFeature[]::new);
    }

    public static int of(SerializerFeature[] features) {
        if (features == null) {
            return 0;
        }

        int value = 0;

        for (SerializerFeature feature: features) {
            value |= feature.mask;
        }

        return value;
    }
}
