package com.zhongpengcheng.spine.io.pojo.timeline;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-16 15:53:35
 **/
@Getter
@Setter
public class EventTimeline implements ITimeline {
    private String eventName;
    private List<Frame> frameList;

    public EventTimeline (int frameCount) {
        frameList = new ArrayList<>(frameCount);
    }

    public void addFrame(float time, String name, int intValue, float floatValue, String stringValue) {
        frameList.add(new Frame(time, name, intValue, floatValue, stringValue));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Frame {
        private float time;
        private String name;
        private int intValue;
        private float floatValue;
        private String stringValue;
    }

}
