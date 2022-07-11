package com.zhongpengcheng.spine.io.v35.serializer;

import com.zhongpengcheng.spine.io.pojo.Skeleton;

/**
 * 序列化器接口
 *
 * @author zhongpengcheng
 * @since 2022-02-18 15:04:10
 **/
public interface ISerializer<R> {
    /**
     * 将骨骼对象序列化为目标对象
     */
    R serialize(Skeleton skeleton);
}
