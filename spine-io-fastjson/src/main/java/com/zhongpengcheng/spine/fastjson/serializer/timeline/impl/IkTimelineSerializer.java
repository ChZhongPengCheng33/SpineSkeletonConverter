package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.IkTimeline;

/**
 * {@link IkTimeline}序列化器
 * @author zhongpengcheng
 * @since 2022-02-18 17:00:53
 **/
public class IkTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!this.support(timeline)) return null;

        JSONObject ret = new JSONObject(true);
        JSONArray frameList = new JSONArray();
        IkTimeline ikTimeline = (IkTimeline) timeline;
        for (int i = 0; i < ikTimeline.getFrameList().size(); i++) {
            IkTimeline.Frame frame = ikTimeline.getFrameList().get(i);
            JSONObject temp = new JSONObject(true);
            temp.put("time", frame.getTime());
            temp.put("mix", frame.getMix());
            temp.put("bendDirection", frame.getBendDirection());
            if (ikTimeline.getFrameIndexList().contains(i)) {
                int j = ikTimeline.getFrameIndexList().indexOf(i);
                temp.put("curve", Boolean.TRUE.equals(ikTimeline.getSteppedList().get(j))
                        ? "stepped" : ikTimeline.getCurveList().get(j));
            }

            frameList.add(temp);
        }

        ret.put(ikTimeline.getIkName(), frameList);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof IkTimeline;
    }
}
