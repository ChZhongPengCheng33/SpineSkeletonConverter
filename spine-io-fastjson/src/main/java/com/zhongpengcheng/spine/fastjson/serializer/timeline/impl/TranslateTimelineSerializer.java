package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.TranslateTimeline;

/**
 * 将{@link TranslateTimeline}序列化为{@link JSONObject}
 *
 * @author zhongpengcheng
 * @since 2022-02-23 16:27:05
 **/
public class TranslateTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!support(timeline)) return null;

        TranslateTimeline translateTimeline = (TranslateTimeline) timeline;
        JSONObject ret = new JSONObject(true);
        JSONArray frames = new JSONArray();

        for (int i = 0; i < translateTimeline.getFrameList().size(); i++) {
            TranslateTimeline.Frame frame = translateTimeline.getFrameList().get(i);
            JSONObject temp = new JSONObject(true);
            temp.put("time", frame.getTime());
            temp.put("x", frame.getX());
            temp.put("y", frame.getY());
            if (translateTimeline.getFrameIndexList().contains(i)) {
                int j = translateTimeline.getFrameIndexList().indexOf(i);
                temp.put("curve", Boolean.TRUE.equals(translateTimeline.getSteppedList().get(j))
                        ? "stepped" : translateTimeline.getCurveList().get(j));
            }
            frames.add(temp);
        }

        ret.put(translateTimeline.getTimelineType(), frames);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof TranslateTimeline;
    }
}
