package com.zhongpengcheng.spine.fastjson.serializer.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializerFactory;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.AttachmentTimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.ColorTimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.TwoColorTimeline;

import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-18 16:23:53
 **/
public class SlotBlockSerializer implements TimelineBlockSerializer {

    @Override
    public JSONObject serialize(List<ITimeline> timelines) {
        JSONObject ret = new JSONObject(true);

        timelines.forEach(timeline -> {
            JSONObject serializeResult = TimelineSerializerFactory.serializerOf(timeline).serialize(timeline);
            String slotName = this.parseSlotName(timeline);
            ret.put(slotName, serializeResult);
        });

        return ret;
    }

    private String parseSlotName(ITimeline timeline) {
        if (timeline instanceof AttachmentTimeline) return ((AttachmentTimeline) timeline).getSlotName();
        if (timeline instanceof ColorTimeline) return ((ColorTimeline) timeline).getSlotName();
        if (timeline instanceof TwoColorTimeline) return ((TwoColorTimeline) timeline).getSlotName();
        return null;
    }
}
