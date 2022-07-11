package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-16 14:38:46
 **/
@Getter
@Setter
public class PathMixTimeline extends CurveTimeline {
    private int pathIndex;
    private String pathName;
    private String timelineType = "mix";
    private List<Frame> frameList;

    public PathMixTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float rotateMix, float translateMix) {
        this.frameList.add(new Frame(time, rotateMix, translateMix));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frame {
        private float time;
        private float rotateMix;
        private float translateMix;
    }
}
