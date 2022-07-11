package com.zhongpengcheng.spine.fastjson.serializer.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.fastjson.serializer.GroupTimelineSerializer;
import com.zhongpengcheng.spine.fastjson.serializer.TimelineBlockSerializer;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.PathMixTimeline;
import com.zhongpengcheng.spine.io.pojo.timeline.PathPositionTimeline;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zhongpengcheng
 * @since 2022-02-24 17:34:17
 **/
@Slf4j
public class PathBlockSerializer implements TimelineBlockSerializer {
    @Override
    public JSONObject serialize(List<ITimeline> timelines) {
        JSONObject ret = new JSONObject(true);
        LinkedHashMap<String, List<ITimeline>> cache = new LinkedHashMap<>();

        timelines.forEach(timeline -> {
            String pathName = parsePathName(timeline);
            if (pathName == null) {
                log.error("骨骼时间线的骨骼对象为空，timeline={}", timeline);
                return;
            }
            if (!cache.containsKey(pathName)) cache.put(pathName, new ArrayList<>());
            cache.get(pathName).add(timeline);
        });

        cache.forEach(GroupTimelineSerializer.of(ret));

        return ret;
    }

    private String parsePathName(ITimeline timeline) {
        if (timeline instanceof PathMixTimeline) return ((PathMixTimeline) timeline).getPathName();
        if (timeline instanceof PathPositionTimeline) return ((PathPositionTimeline) timeline).getPathName();
        return null;
    }
}
