package com.zhongpengcheng.spine.fastjson.serializer.timeline;

import com.zhongpengcheng.spine.fastjson.serializer.timeline.impl.*;
import com.zhongpengcheng.spine.io.pojo.timeline.*;

import java.util.HashMap;

/**
 * 时间线序列化器工厂
 *
 * @author zhongpengcheng
 * @since 2022-02-18 15:41:01
 **/
public class TimelineSerializerFactory {

    private static final HashMap<Class<? extends ITimeline>, TimelineSerializer> serializerCache;

    static {
        serializerCache = new HashMap<>();
        serializerCache.put(AttachmentTimeline.class, new AttachmentTimelineSerializer());
        serializerCache.put(ColorTimeline.class, new ColorTimelineSerializer());
        serializerCache.put(TwoColorTimeline.class, new TwoColorTimelineSerializer());
        serializerCache.put(DeformTimeline.class, new DeformTimelineSerializer());
        serializerCache.put(DrawOrderTimeline.class, new DrawOrderTimelineSerializer());
        serializerCache.put(EventTimeline.class, new EventTimelineSerializer());
        serializerCache.put(IkTimeline.class, new IkTimelineSerializer());
        serializerCache.put(PathMixTimeline.class, new PathMixTimelineSerializer());
        serializerCache.put(PathPositionTimeline.class, new PathPositionTimelineSerializer());
        serializerCache.put(RotateTimeline.class, new RotateTimelineSerializer());
        serializerCache.put(TransformTimeline.class, new TransformTimelineSerializer());
        serializerCache.put(TranslateTimeline.class, new TranslateTimelineSerializer());
    }

    /**
     * 获取时间线对应的序列化器
     */
    public static TimelineSerializer serializerOf(ITimeline timeline) {
        if (timeline == null) throw new NullPointerException("timeline is null.");
        return serializerCache.get(timeline.getClass());
    }
}
