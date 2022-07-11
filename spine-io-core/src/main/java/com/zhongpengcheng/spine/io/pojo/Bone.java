package com.zhongpengcheng.spine.io.pojo;

import com.zhongpengcheng.spine.annotation.SpineIOField;
import com.zhongpengcheng.spine.enums.SpineVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bone部分数据
 * @author ZhongPengCheng
 * @since 2021-03-02 0:35:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bone {
    /**
     * 骨骼名称. 该参数在一个skeleton中是唯一的.
     */
    private String name;
    /**
     * 骨骼排序
     */
    private Integer id;
    /**
     * 父骨骼名称
     */
    private String parent;
    /**
     * 在setup pose中, 该骨骼相对于父骨骼的旋转角度.
     */
    private Float rotation;
    /**
     * 在setup pose中骨骼相对于父骨骼坐标的X分量.
     */
    private Float x;
    /**
     * 在setup pose中骨骼相对于父骨骼坐标的Y分量.
     */
    private Float y;
    /**
     * 在setup pose中骨骼在X方向的缩放值.
     */
    private Float scaleX;
    /**
     * 在setup pose中骨骼在Y方向的缩放值.
     */
    private Float scaleY;
    /**
     * 在setup pose中骨骼在X方向斜切角度.
     */
    private Float shearX;
    /**
     * 在setup pose中骨骼在Y方向斜切角度.
     */
    private Float shearY;
    /**
     * 骨骼长度. 除了2骨骼IK和为骨骼绘制调试线外, 骨骼长度这个参数在运行时不常使用.
     */
    private Float length;
    /**
     * 决定了该骨骼如何继承父骨骼的transform.
     */
    private String transformMode;
    /**
     * 骨骼的颜色, 与Spine中一致. 非必要的参数.
     */
    private String color;
    /**
     * 如果为true, 则只有当活动皮肤中涉及到该骨骼时, 该骨骼才活动.
     */
    @SpineIOField(version = SpineVersion.V_3_8_89, nonessential = true)
    private Boolean skinRequired;
}
