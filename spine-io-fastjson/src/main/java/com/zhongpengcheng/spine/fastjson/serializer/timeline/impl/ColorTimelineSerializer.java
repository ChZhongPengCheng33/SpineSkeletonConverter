package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ColorTimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;

/**
 * {@link ColorTimeline}的序列化器
 * @author zhongpengcheng
 * @since 2022-02-18 16:55:44
 **/
public class ColorTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!support(timeline)) return null;

        ColorTimeline colorTimeline = (ColorTimeline) timeline;
        JSONObject ret = new JSONObject(true);
        JSONArray attachmentFrames = new JSONArray();

        colorTimeline.getFrames().forEach(frame -> {
            JSONObject frameTemp = new JSONObject(true);
            frameTemp.put("time", frame.getTime());
            frameTemp.put("color", frame.getColor().toString());

            attachmentFrames.add(frameTemp);
        });

        ret.put(this.framesKey(), attachmentFrames);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof ColorTimeline;
    }

    @Override
    public String framesKey() {
        return "color";
    }
}
