package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-15 17:13:48
 **/
@Getter
@Setter
public class IkTimeline extends CurveTimeline {

    private int ikIndex;
    private String ikName;
    private List<Frame> frameList;

    public IkTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float mix, int bendDirection) {
        this.frameList.add(new Frame(time, mix, bendDirection));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frame {
        private float time;
        private float mix;
        private int bendDirection;
    }
}
