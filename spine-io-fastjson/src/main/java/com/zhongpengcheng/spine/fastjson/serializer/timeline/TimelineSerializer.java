package com.zhongpengcheng.spine.fastjson.serializer.timeline;

import com.alibaba.fastjson.JSONObject;
import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;

/**
 * 时间线序列化器
 *
 * @author zhongpengcheng
 * @since 2022-02-18 15:39:19
 **/
public interface TimelineSerializer {
    /**
     * 将时间线序列化为{@link JSONObject}
     */
    JSONObject serialize(ITimeline timeline);

    /**
     * 判断是否支持对当前时间线进行处理
     * @param timeline 待处理时间线
     * @return 支持返回true，否则返回false
     */
    default boolean support(ITimeline timeline) {
        return true;
    }

    /**
     * 帧列表在json骨骼文件中的key
     */
    default String framesKey() {
        return null;
    }
}
