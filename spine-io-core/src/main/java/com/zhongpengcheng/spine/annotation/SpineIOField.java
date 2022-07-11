package com.zhongpengcheng.spine.annotation;

import com.zhongpengcheng.spine.enums.SpineVersion;

import java.lang.annotation.*;

import static com.zhongpengcheng.spine.constants.SpineIOConstants.*;

/**
 * SpineIO字段注解，用于配置字段属性
 *
 * @author zhongpengcheng
 * @since 2022-07-11 15:37:22
 **/
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpineIOField {
    /**
     * 字段序列化名称
     * @return 默认使用字段原始名称
     */
    String value() default DEFAULT_FIELD_NAME;

    /**
     * 字段适用版本
     * @return 默认所有版本适用
     */
    SpineVersion[] version() default SpineVersion.GENERAL;

    /**
     * 标注字段是否为非必须字段
     * @return 默认必须字段
     */
    boolean nonessential() default DEFAULT_NONESSENTIAL;
}
