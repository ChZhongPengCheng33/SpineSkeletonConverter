package com.zhongpengcheng.spine.io.v35.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * path约束
 * @author ZhongPengCheng
 * @since 2021-03-02 18:05:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Path {
    /**
     * 约束的名称. 该参数在一个skeleton中是唯一的.
     */
    private String name;
    /**
     * 应用约束的顺序.
     */
    private Integer order;
    /**
     * 附加的骨骼
     */
    private List<String> bones;
    /**
     * 目标slot的名称
     */
    private String target;
    /**
     * 决定路径位置的计算方式.
     */
    private String positionMode;
    /**
     * 决定骨骼间间距的计算方式.
     */
    private String spacingMode;
    /**
     * 决定骨骼旋转角度的计算方式.
     */
    private String rotateMode;
    /*default 0*/
    /**
     * 路径旋转中的旋转偏移量.
     */
    @Builder.Default
    private Float rotation = 0F;
    /**
     * 路径位置.
     */
    @Builder.Default
    private Float position = 0F;
    /**
     * 骨骼间距.
     */
    @Builder.Default
    private Float spacing = 0F;
    /*default 1*/
    /**
     * 一个从0到1的值, 表示约束对骨骼的影响. 其中0表示没有影响, 1表示完全约束, 中间值表示正常姿势和约束的混合.
     */
    @Builder.Default
    private Float rotateMix = 1F;
    /**
     * 参见rotate mix.
     */
    @Builder.Default
    private Float translateMix = 1F;
}
