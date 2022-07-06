package com.zhongpengcheng.spine.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilsTest {
    @Test
    void testColorConvert() {
        String color = "#ffffffff";

        assertEquals(color, "#" + ColorUtils.rgba8888ToHexColor(ColorUtils.hexColorToRgba(color)));
    }
}