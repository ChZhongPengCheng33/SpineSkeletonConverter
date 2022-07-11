package com.zhongpengcheng.spine.io.pojo.timeline;

import com.zhongpengcheng.spine.util.ColorUtils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhongPengCheng
 * @since 2022-01-28 13:35:00
 */
@Getter
@Setter
public class TwoColorTimeline extends CurveTimeline {
    static public final int ENTRIES = 8;
    static private final int PREV_TIME = -8, PREV_R = -7, PREV_G = -6, PREV_B = -5, PREV_A = -4;
    static private final int PREV_R2 = -3, PREV_G2 = -2, PREV_B2 = -1;
    static private final int R = 1, G = 2, B = 3, A = 4, R2 = 5, G2 = 6, B2 = 7;

    private int slotIndex;
    private String slotName;
    private List<Frame> frames;

    public TwoColorTimeline(int frameCount) {
        frames = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, ColorUtils.Color light, ColorUtils.Color dark) {
        frames.add(new Frame(time, light, dark));
    }

    @Data
    @Builder
    public static class Frame {
        private float time;
        private ColorUtils.Color light;
        private ColorUtils.Color dark;
    }
}
