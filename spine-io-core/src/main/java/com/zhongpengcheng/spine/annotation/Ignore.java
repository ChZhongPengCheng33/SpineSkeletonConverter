package com.zhongpengcheng.spine.annotation;

import com.zhongpengcheng.spine.enums.SpineVersion;

import java.lang.annotation.*;

/**
 * 序列化时忽略该字段
 *
 * @author zhongpengcheng
 * @since 2022-07-11 16:56:06
 **/
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ignore {
    /**
     * 标注在哪些版本忽略
     * @return 默认全部忽略
     */
    SpineVersion[] value() default SpineVersion.GENERAL;
}
