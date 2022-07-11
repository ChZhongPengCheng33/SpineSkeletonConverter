package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-15 16:20:38
 **/
@Getter
@Setter
public class RotateTimeline extends CurveTimeline {
    private int boneIndex;
    private String boneName;
    private List<Frame> frameList;
    private String timelineType;

    public RotateTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float degrees) {
        this.frameList.add(Frame.builder()
                .time(time)
                .degrees(degrees)
                .build());
    }

    @Data
    @Builder
    public static class Frame {
        private float time;
        private float degrees;
    }
}
