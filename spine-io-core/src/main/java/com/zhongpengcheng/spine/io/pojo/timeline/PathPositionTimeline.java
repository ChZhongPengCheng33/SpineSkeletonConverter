package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-16 14:24:47
 **/
@Getter
@Setter
public class PathPositionTimeline extends CurveTimeline {
    private int pathIndex;
    private String pathName;
    private String timelineType = "position";
    private List<Frame> frameList;

    public PathPositionTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float position) {
        this.frameList.add(new Frame(time, position));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frame {
        private float time;
        private float position;
    }
}
