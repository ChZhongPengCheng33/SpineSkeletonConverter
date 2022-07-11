package com.zhongpengcheng.spine.util;

import cn.hutool.core.lang.Filter;
import cn.hutool.core.util.*;
import com.alibaba.fastjson.JSON;
import com.zhongpengcheng.spine.annotation.SpineIOField;
import com.zhongpengcheng.spine.io.pojo.Head;
import com.zhongpengcheng.spine.io.pojo.Skeleton;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.zhongpengcheng.spine.util.StreamUtil.not;

class AnnotationUtilsTest {
    @Test
    void testGetNoAnnoField() {
        SpineIOField ioFiled = AnnotationUtils.getSpineIOField(Head.class, "hash");
        System.out.println(JSON.toJSONString(ioFiled));
    }

    @Test
    void testOrder() {
        Arrays.stream(ReflectUtil.getFields(Skeleton.class))
                .forEach(this::printField);
    }

    private void printField(final Field afield) {
        Class<?> clazz = afield.getType();
        System.out.println("-------------------------------------------");
        if (clazz.isAssignableFrom(List.class)) {
            System.out.println("目标类为List的子类");
            System.out.println(TypeUtil.getTypeArguments(afield.getGenericType())[0]);
            clazz = TypeUtil.getClass(TypeUtil.getTypeArguments(afield.getGenericType())[0]);
        }
        Arrays.stream(ReflectUtil.getFields(clazz))
                // 过滤静态字段
                .filter(not(ModifierUtil::isStatic))
                // 过滤需忽略的字段
                .filter(field -> AnnotationUtils.getIgnore(field) == null)
                // 排序
                .sorted(AnnotationUtils::compareFieldOrder)
                // 打印
                .map(field -> StrUtil.format("{}-{}", field.getName(), field.getType().getSimpleName()))
                .forEach(System.out::println);
    }
}