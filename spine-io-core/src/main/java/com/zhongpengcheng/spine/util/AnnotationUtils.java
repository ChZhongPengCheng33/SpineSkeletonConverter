package com.zhongpengcheng.spine.util;

import cn.hutool.core.util.ReflectUtil;
import com.zhongpengcheng.spine.annotation.Ignore;
import com.zhongpengcheng.spine.annotation.Order;
import com.zhongpengcheng.spine.annotation.SpineIOField;
import com.zhongpengcheng.spine.enums.SpineVersion;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Optional;

import static com.zhongpengcheng.spine.constants.SpineIOConstants.*;

/**
 * 注解工具
 *
 * @author zhongpengcheng
 * @since 2022-07-11 16:07:35
 **/
@Slf4j
public class AnnotationUtils {
    /**
     * 获取目标类的目标字段上的{@link SpineIOField}注解
     *
     * @param clazz     类对象
     * @param fieldName 字段名称
     * @return 注解对象，默认返回{@link DefaultSpineIOFieldHolder#instance}
     */
    public static SpineIOField getSpineIOField(final Class<?> clazz, final String fieldName) {
        return getSpineIOField(ReflectUtil.getField(clazz, fieldName));
    }

    /**
     * 获取目标字段上的{@link SpineIOField}注解
     *
     * @param field 字段对象
     * @return 注解对象，默认返回{@link DefaultSpineIOFieldHolder#instance}
     */
    public static SpineIOField getSpineIOField(final Field field) {
        return Optional.ofNullable(field.getAnnotation(SpineIOField.class))
                .orElse(DefaultSpineIOFieldHolder.instance);
    }

    /**
     * 获取目标类的目标字段上的{@link Order}注解
     *
     * @param clazz     类对象
     * @param fieldName 字段名称
     * @return 注解对象，默认返回{@link DefaultSpineIOFieldHolder#instance}
     */
    public static Order getOrder(final Class<?> clazz, final String fieldName) {
        return getOrder(ReflectUtil.getField(clazz, fieldName));
    }

    /**
     * 获取目标字段上的{@link Order}注解
     *
     * @param field 字段对象
     * @return 注解对象，默认返回{@link DefaultOrderHolder#instance}
     */
    public static Order getOrder(final Field field) {
        return Optional.ofNullable(field.getAnnotation(Order.class))
                .orElse(DefaultOrderHolder.instance);
    }

    /**
     * 获取目标类的目标字段上的{@link Ignore}注解
     *
     * @param clazz     类名称
     * @param fieldName 字段名称
     * @return 可能为空
     */
    public static Ignore getIgnore(final Class<?> clazz, final String fieldName) {
        return getIgnore(ReflectUtil.getField(clazz, fieldName));
    }

    /**
     * 获取目标字段上的{@link Ignore}注解
     *
     * @param field 字段对象
     * @return 可能为空
     */
    public static Ignore getIgnore(final Field field) {
        return field.getAnnotation(Ignore.class);
    }

    public static int compareFieldOrder(Field o1, Field o2) {
        Order anno1 = AnnotationUtils.getOrder(o1);
        Order anno2 = AnnotationUtils.getOrder(o2);

        return Integer.compare(anno1.value(), anno2.value());
    }

    public static class DefaultSpineIOFieldHolder {
        public static final SpineIOField instance = new SpineIOField() {
            @Override
            public String value() {
                return DEFAULT_FIELD_NAME;
            }

            @Override
            public SpineVersion[] version() {
                return new SpineVersion[]{SpineVersion.GENERAL};
            }

            @Override
            public boolean nonessential() {
                return DEFAULT_NONESSENTIAL;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return SpineIOField.class;
            }
        };
    }

    public static class DefaultOrderHolder {
        public static final Order instance = new Order() {
            @Override
            public int value() {
                return DEFAULT_ORDER;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Order.class;
            }
        };
    }
}
