package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.TwoColorTimeline;

/**
 * {@link TwoColorTimeline}序列化器
 * @author zhongpengcheng
 * @since 2022-02-18 16:57:23
 **/
public class TwoColorTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!support(timeline)) return null;

        TwoColorTimeline twoColorTimeline = (TwoColorTimeline) timeline;
        JSONObject ret = new JSONObject(true);
        JSONArray attachmentFrames = new JSONArray();

        twoColorTimeline.getFrames().forEach(frame -> {
            JSONObject temp = new JSONObject(true);
            temp.put("time", frame.getTime());
            temp.put("light", frame.getLight().toString());
            temp.put("dark", frame.getDark().toString());

            attachmentFrames.add(temp);
        });

        ret.put(this.framesKey(), attachmentFrames);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof TwoColorTimeline;
    }

    @Override
    public String framesKey() {
        return "twoColor";
    }
}
