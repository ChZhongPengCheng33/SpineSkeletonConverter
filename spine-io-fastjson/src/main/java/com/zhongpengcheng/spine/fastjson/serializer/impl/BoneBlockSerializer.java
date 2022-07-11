package com.zhongpengcheng.spine.fastjson.serializer.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.GroupTimelineSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.RotateTimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.TranslateTimeline;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 骨骼时间线块序列化器
 *
 * @author zhongpengcheng
 * @since 2022-02-23 15:27:59
 **/
@Slf4j
public class BoneBlockSerializer implements TimelineBlockSerializer {
    @Override
    public JSONObject serialize(List<ITimeline> timelines) {

        JSONObject ret = new JSONObject(true);
        LinkedHashMap<String, List<ITimeline>> cache = new LinkedHashMap<>();
        timelines.forEach(timeline -> {
            String boneName = parseBoneName(timeline);
            if (boneName == null) {
                log.error("骨骼时间线的骨骼对象为空，timeline={}", timeline);
                return;
            }
            if (!cache.containsKey(boneName)) cache.put(boneName, new ArrayList<>());
            cache.get(boneName).add(timeline);
        });

        cache.forEach(GroupTimelineSerializer.of(ret));
        return ret;
    }

    private String parseBoneName(ITimeline timeline) {
        if (timeline instanceof RotateTimeline) return ((RotateTimeline) timeline).getBoneName();
        if (timeline instanceof TranslateTimeline) return ((TranslateTimeline) timeline).getBoneName();
        return null;
    }
}
