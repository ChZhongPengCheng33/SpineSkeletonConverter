package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.PathMixTimeline;

/**
 * {@link PathMixTimeline}序列化器
 * @author zhongpengcheng
 * @since 2022-02-18 17:01:39
 **/
public class PathMixTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        JSONObject ret = new JSONObject(true);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof PathMixTimeline;
    }
}
