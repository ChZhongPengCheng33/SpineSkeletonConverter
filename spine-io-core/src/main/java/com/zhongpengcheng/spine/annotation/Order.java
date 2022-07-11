package com.zhongpengcheng.spine.annotation;

import com.zhongpengcheng.spine.constants.SpineIOConstants;

import java.lang.annotation.*;

import static com.zhongpengcheng.spine.constants.SpineIOConstants.DEFAULT_ORDER;

/**
 * 排序注解
 *
 * @author zhongpengcheng
 * @since 2022-07-11 16:48:08
 **/
@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    /**
     * 排序值
     * @return 默认0
     */
    int value() default DEFAULT_ORDER;
}
