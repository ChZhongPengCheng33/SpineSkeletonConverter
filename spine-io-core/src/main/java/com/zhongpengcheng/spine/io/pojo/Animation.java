package com.zhongpengcheng.spine.io.pojo;

import com.zhongpengcheng.spine.io.pojo.timeline.ITimeline;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 动画
 *
 * @author ZhongPengCheng
 * @since 2022-01-26 20:49:00
 */
@Data
@Builder
public class Animation {
    /**
     * 动画的名称
     */
    private String name;
    /**
     * 动画关键帧的映射
     */
    private Map<String, List<ITimeline>> timelineMap;
}
