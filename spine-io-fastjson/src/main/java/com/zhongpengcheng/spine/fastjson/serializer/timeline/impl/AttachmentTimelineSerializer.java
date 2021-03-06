package com.zhongpengcheng.spine.fastjson.serializer.timeline.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.pojo.timeline.AttachmentTimeline;
import com.zhongpengcheng.spine.pojo.timeline.ITimeline;

/**
 * {@link com.zhongpengcheng.spine.pojo.timeline.AttachmentTimeline}εΊεεε¨
 * @author zhongpengcheng
 * @since 2022-02-18 16:51:02
 **/
public class AttachmentTimelineSerializer implements TimelineSerializer {
    @Override
    public JSONObject serialize(ITimeline timeline) {
        if (!support(timeline)) return null;

        AttachmentTimeline attachmentTimeline = (AttachmentTimeline) timeline;
        JSONObject ret = new JSONObject(true);
        JSONArray attachmentFrames = new JSONArray();
        attachmentFrames.addAll(attachmentTimeline.getFrameList());

        ret.put(this.framesKey(), attachmentFrames);

        return ret;
    }

    @Override
    public boolean support(ITimeline timeline) {
        return timeline instanceof AttachmentTimeline;
    }

    @Override
    public String framesKey() {
        return "attachment";
    }
}
