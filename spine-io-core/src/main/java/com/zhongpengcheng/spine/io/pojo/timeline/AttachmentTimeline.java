package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhongPengCheng
 * @since 2022-01-26 21:10:00
 */
@Data
public class AttachmentTimeline implements ITimeline {
    private int slotIndex;
    private String slotName;
    private List<Frame> frameList;

    public AttachmentTimeline (int frameCount) {
        frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, String attachmentName) {
        frameList.add(Frame.of(time, attachmentName));
    }

    @Data
    @Builder
    public static class Frame {
        private float time;
        private String name;

        public static Frame of(float time, String name) {
            return new Frame(time, name);
        }
    }
}
