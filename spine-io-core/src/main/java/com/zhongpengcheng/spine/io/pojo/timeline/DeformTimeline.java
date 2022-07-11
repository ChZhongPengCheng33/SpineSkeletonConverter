package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-16 14:47:15
 **/
@Getter
@Setter
public class DeformTimeline extends CurveTimeline {
    private int slotIndex;
    private int skinIndex;
    private String slotName;
    private String skinName;
    private String attachmentName;
    private List<Frame> frameList;

    public DeformTimeline(int frameCount) {
        this.frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, float[] vertices) {
        this.frameList.add(new Frame(time, vertices));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Frame {
        private float time;
        private float[] vertices;
    }
}
