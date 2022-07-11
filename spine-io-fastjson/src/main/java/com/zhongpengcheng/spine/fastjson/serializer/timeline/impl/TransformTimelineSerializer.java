package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.TransformTimeline;

/**
 * @author zhongpengcheng
 * @since 2022-02-18 17:03:37
 **/
public class TransformTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        JSONObject ret = new JSONObject(true);
        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof TransformTimeline;
    }
}
