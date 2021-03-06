package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.pojo.timeline.DeformTimeline;
import com.zhongpengcheng.spine.pojo.timeline.ITimeline;

/**
 * {@link com.zhongpengcheng.spine.pojo.timeline.DeformTimeline}εΊεεε¨
 * @author zhongpengcheng
 * @since 2022-02-18 16:58:15
 **/
public class DeformTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        return null;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof DeformTimeline;
    }
}
