package com.zhongpengcheng.spine.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.TypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型工具类
 * @author zhongpengcheng
 * @since 2022-07-11 17:44:44
 **/
@Slf4j
public class GenericTypeUtils {
    /**
     * 从字段对象获取父类是{@link Iterable}的类的泛型类型
     * @param field 字段对象
     * @return 泛型类型
     */
    public static Class<?> getGenericTypeOfIterable(final Field field) {
        if (field == null) {
            // 字段为空返回空
            return null;
        }
        if (!field.getType().isAssignableFrom(Iterable.class)) {
            // 字段不是Iterable的子类返回null
            return null;
        }
        return TypeUtil.getClass(TypeUtil.getTypeArguments(field.getGenericType())[0]);
    }

    /**
     * 获取目标字段上的1个泛型参数类型
     */
    public static Class<?> getSingleGenericType(final Field field) {
        if (field == null) {
            // 字段为空返回空
            return null;
        }
        Type[] typeArguments = TypeUtil.getTypeArguments(field.getGenericType());
        Assert.notNull(typeArguments, "目标字段无泛型参数");
        Assert.isTrue(typeArguments.length >= 1, "目标字段的泛型参数个数小于1");

        return TypeUtil.getClass(typeArguments[0]);
    }

    /**
     * 获取目标字段上的2个泛型参数类型
     */
    public static Pair<Class<?>, Class<?>> getPairGenericType(final Field field) {
        if (field == null) {
            // 字段为空返回空
            return null;
        }
        Type[] typeArguments = TypeUtil.getTypeArguments(field.getGenericType());
        Assert.notNull(typeArguments, "目标字段无泛型参数");
        Assert.isTrue(typeArguments.length >= 2, "目标字段的泛型参数个数小于2");

        return new ImmutablePair<>(TypeUtil.getClass(typeArguments[0]), TypeUtil.getClass(typeArguments[1]));
    }

    /**
     * 获取目标字段上的3个泛型参数类型
     */
    public static Triple<Class<?>, Class<?>, Class<?>> getTripleGenericType(final Field field) {
        if (field == null) {
            // 字段为空返回空
            return null;
        }
        Type[] typeArguments = TypeUtil.getTypeArguments(field.getGenericType());
        Assert.notNull(typeArguments, "目标字段无泛型参数");
        Assert.isTrue(typeArguments.length >= 3, "目标字段的泛型参数个数小于3");

        return new ImmutableTriple<>(TypeUtil.getClass(typeArguments[0]), TypeUtil.getClass(typeArguments[1]),
                TypeUtil.getClass(typeArguments[2]));
    }

    /**
     * 获取目标字段上的所有泛型参数类型
     */
    public static Class<?>[] getAllGenericType(final Field field) {
        if (field == null) {
            // 字段为空返回空
            return null;
        }
        Type[] typeArguments = TypeUtil.getTypeArguments(field.getGenericType());
        Assert.notNull(typeArguments, "目标字段无泛型参数");
        return Arrays.stream(typeArguments)
                .map(TypeUtil::getClass)
                .toArray(Class<?>[]::new);
    }
}
