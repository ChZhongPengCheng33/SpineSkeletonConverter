package com.zhongpengcheng.spine.io.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 插槽
 * @author ZhongPengCheng
 * @since 2021-03-02 1:20:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    /**
     * 槽位的名称. 该参数在一个skeleton中是唯一的.
     */
    private String name;
    /**
     * 编号
     */
    private Integer id;
    /**
     * 对应骨骼名称
     */
    private String bone;
    /**
     * 在setup pose中槽位的附件名称. 如果为空, 表示在setup pose没有附件.
     */
    private String attachment;
    /**
     * 在setup pose中槽位的颜色.
     */
    private String color;
    /**
     * 在绘制槽位的可见附件时要使用的blending类型.
     */
    private String blend;
}
