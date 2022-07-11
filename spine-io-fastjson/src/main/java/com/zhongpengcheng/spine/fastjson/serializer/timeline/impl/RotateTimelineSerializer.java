package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.RotateTimeline;

/**
 * {@link RotateTimeline}序列化器
 *
 * @author zhongpengcheng
 * @since 2022-02-18 17:03:01
 **/
public class RotateTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!support(timeline)) return null;

        RotateTimeline rotateTimeline = (RotateTimeline) timeline;
        JSONObject ret = new JSONObject(true);
        JSONArray frames = new JSONArray();

        for (int i = 0; i < rotateTimeline.getFrameList().size(); i++) {
            RotateTimeline.Frame frame = rotateTimeline.getFrameList().get(i);
            JSONObject temp = new JSONObject(true);
            temp.put("time", frame.getTime());
            temp.put("angle", frame.getDegrees());
            if (rotateTimeline.getFrameIndexList().contains(i)) {
                int j = rotateTimeline.getFrameIndexList().indexOf(i);
                temp.put("curve", Boolean.TRUE.equals(rotateTimeline.getSteppedList().get(j))
                        ? "stepped" : rotateTimeline.getCurveList().get(j));
            }
            frames.add(temp);
        }
        ret.put(this.framesKey(), frames);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof RotateTimeline;
    }

    @Override
    public String framesKey() {
        return "rotate";
    }
}
