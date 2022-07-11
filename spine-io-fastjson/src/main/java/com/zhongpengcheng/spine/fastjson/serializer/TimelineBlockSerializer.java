package com.zhongpengcheng.spine.fastjson.serializer;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;

import java.util.List;

/**
 * 时间线分块序列化器
 * @author zhongpengcheng
 * @since 2022-02-18 16:10:35
 **/
public interface TimelineBlockSerializer {
    JSONObject serialize(List<ITimeline> timelines);
}
