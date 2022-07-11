package com.zhongpengcheng.spine.fastjson.serializer.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.timeline.TimelineSerializerFactory;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-24 17:18:11
 **/
@Slf4j
public class IkBlockSerializer implements TimelineBlockSerializer {
    @Override
    public JSONObject serialize(List<ITimeline> timelines) {
        JSONObject ret = new JSONObject(true);

        timelines.forEach(timeline -> {
            TimelineSerializer serializer = TimelineSerializerFactory.serializerOf(timeline);
            if (serializer == null) {
                log.error("未匹配到对应的序列化器，timeline={}", timeline);
                return;
            }
            JSONObject serializeRet = serializer.serialize(timeline);
            ret.putAll(serializeRet);
        });

        return ret;
    }
}
