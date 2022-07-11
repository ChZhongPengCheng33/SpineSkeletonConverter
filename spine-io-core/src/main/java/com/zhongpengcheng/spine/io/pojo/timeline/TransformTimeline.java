package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-16 10:59:58
 **/
@Getter
@Setter
public class TransformTimeline extends CurveTimeline {
    private int transformIndex;
    private String transformName;
    private List<Frame> frameList;

    public TransformTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float rotateMix, float translateMix, float scaleMix, float shearMix) {
        this.frameList.add(new Frame(time, rotateMix, translateMix, scaleMix, shearMix));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frame {
        private float time;
        private float rotateMix;
        private float translateMix;
        private float scaleMix;
        private float shearMix;
    }
}
