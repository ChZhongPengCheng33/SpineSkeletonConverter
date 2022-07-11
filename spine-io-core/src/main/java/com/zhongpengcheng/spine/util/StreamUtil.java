package com.zhongpengcheng.spine.util;

import java.util.function.Predicate;

/**
 * Java流工具
 * @author zhongpengcheng
 * @since 2022-07-11 17:17:26
 **/
public class StreamUtil {

    /**
     * 对函数引用结果取反
     * @param t 函数引用
     * @return 取反后的结果
     */
    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }
}
