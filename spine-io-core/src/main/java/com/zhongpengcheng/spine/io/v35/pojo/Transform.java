package com.zhongpengcheng.spine.io.v35.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Transform约束
 *
 * @author ZhongPengCheng
 * @since 2021-03-02 17:08:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transform {
    /**
     * 约束的名称. 该参数在一个skeleton中是唯一的.
     */
    private String name;
    /**
     * 应用约束的顺序.
     */
    private Integer order;
    private List<String> bones;
    /**
     * T目标骨骼的索引值.
     */
    private String target;
    /*以下默认值值0*/
    /**
     * 从目标骨骼的旋转偏移量.
     */
    private Float rotation = 0F;
    /**
     * 从目标骨骼的位移偏移量的X分量.
     */
    private Float x = 0F;
    /**
     * 从目标骨骼的位移偏移量的Y分量..
     */
    private Float y = 0F;
    /**
     * 从目标骨骼的缩放偏移量的X分量..
     */
    private Float scaleX = 0F;
    /**
     * 从目标骨骼的缩放偏移量的Y分量..
     */
    private Float scaleY = 0F;
    /**
     * 从目标骨骼的斜切偏移量的Y分量..
     */
    private Float shearY = 0F;
    /*以下默认值值1*/
    /**
     * 一个从0到1的值, 表示约束对骨骼的影响. 其中0表示没有影响, 1表示完全约束, 中间值表示正常姿势和约束的混合.
     */
    private Float rotateMix = 1F;
    /**
     * 参见rotate mix.
     */
    private Float translateMix = 1F;
    /**
     * 参见rotate mix.
     */
    private Float scaleMix = 1F;
    /**
     * 参见rotate mix.
     */
    private Float shearMix = 1F;
}
