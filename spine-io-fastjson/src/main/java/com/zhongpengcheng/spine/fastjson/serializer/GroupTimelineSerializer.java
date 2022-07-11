package com.zhongpengcheng.spine.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializerFactory;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author zhongpengcheng
 * @since 2022-02-24 17:41:18
 **/
@Slf4j
public class GroupTimelineSerializer implements BiConsumer<String, List<ITimeline>> {

    private final JSONObject timelineBlock;

    private GroupTimelineSerializer(JSONObject timelineBlock) {
        this.timelineBlock = timelineBlock;
    }

    public static GroupTimelineSerializer of(JSONObject timelineBlock) {
        return new GroupTimelineSerializer(timelineBlock);
    }

    @Override
    public void accept(String groupName, List<ITimeline> timelines) {
        timelines.forEach(timeline -> {
            TimelineSerializer serializer = TimelineSerializerFactory.serializerOf(timeline);
            if (serializer == null) {
                log.warn("未匹配到对应的序列化器，timeline={}", timeline);
                return;
            }
            JSONObject serializeRet = serializer.serialize(timeline);
            timelineBlock.putAll(serializeRet);
        });
    }
}
