package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-15 16:31:16
 **/
@Getter
@Setter
public class TranslateTimeline extends CurveTimeline {
    private int boneIndex;
    private String boneName;
    private List<Frame> frameList;
    private String timelineType;

    public TranslateTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float x, float y) {
        this.frameList.add(Frame.builder()
                .time(time)
                .x(x)
                .y(y)
                .build());
    }

    @Data
    @Builder
    public static class Frame {
        private float time;
        private float x;
        private float y;
    }
}
