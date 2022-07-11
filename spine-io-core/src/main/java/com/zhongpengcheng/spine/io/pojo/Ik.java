package com.zhongpengcheng.spine.io.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * IK约束
 *
 * @author ZhongPengCheng
 * @since 2021-03-02 2:03:00
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ik {
    /**
     * 约束的名称. 该参数在一个skeleton中是唯一的.
     */
    private String name;
    /**
     * 应用约束的顺序.
     */
    private Integer order;
    /**
     * 约束附加的骨骼名称列表
     */
    private List<String> bones;
    /**
     * 目标骨骼的名称
     */
    private String target;
    /**
     * 一个从0到1的值, 表示约束对骨骼的影响; 其中0表示只有FK, 1表示只有IK, 中间值表示FK和IK之间的混合.
     */
    private Float mix = 1F;
    /**
     * 若为1, 则骨骼的弯曲方向为正的旋转方向. 若为-1, 则骨骼的弯曲方向则与旋转方向反向.
     */
    private Integer bendPositive = 1;
}
