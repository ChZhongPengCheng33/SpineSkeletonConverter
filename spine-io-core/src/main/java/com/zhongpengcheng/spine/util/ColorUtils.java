package com.zhongpengcheng.spine.util;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 22:11:00
 */
@Slf4j
public class ColorUtils {

    /**
     * 将读取到的int值转换为rgba的值并转换为 #ffaaffaa 的形式
     */
    public static String rgba8888ToHexColor(int value) {
        return new Color(value).toString();
    }

    /**
     * 将读取到的int值转换为rgba的值并转换为{@link Color}对象
     */
    public static Color rgba8888ToColor(int value) {
        return new Color(value);
    }

    /**
     * 将读取到的int值转换为rgba的值并转换为{@link Color}对象
     */
    public static Color rgb8888ToColor(int value) {
        return new Color(
                ((value & 0x00ff0000) >>> 16) / 255f,
                ((value & 0x0000ff00) >>> 8) / 255f,
                ((value & 0x000000ff)) / 255f,
                1F
        );
    }

    /**
     * 将#ffaaddee形式的rgba格式颜色转换为int值
     *
     * @param hexColor 16进制的RGBA颜色字符串，以“#”开头
     */
    public static int hexColorToRgba(String hexColor) {
        if (hexColor == null || "".equals(hexColor) || !hexColor.startsWith("#") || hexColor.length() != 9) {
            throw new IllegalArgumentException("color字符串格式错误：" + hexColor);
        }

        return (Integer.valueOf(hexColor.substring(1, 3), 16) << 24)
                | (Integer.valueOf(hexColor.substring(3, 5), 16) << 16)
                | (Integer.valueOf(hexColor.substring(5, 7), 16) << 8)
                | (Integer.valueOf(hexColor.substring(7, 9), 16));
    }

    @Data
    @Accessors(chain = true)
    public static class Color {
        private float red;
        private float green;
        private float blue;
        private float alpha;

        public Color(float red, float green, float blue, float alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }

        public Color(int color) {
            red = ((color & 0xff000000) >>> 24) / 255f;
            green = ((color & 0x00ff0000) >>> 16) / 255f;
            blue = ((color & 0x0000ff00) >>> 8) / 255f;
            alpha = ((color & 0x000000ff)) / 255f;
        }

        @Override
        public String toString() {
            return Integer.toHexString((int) (red * 255F)) + Integer.toHexString((int) (green * 255F))
                    + Integer.toHexString((int) (blue * 255F)) + Integer.toHexString((int) (alpha * 255F));
        }
    }
}
