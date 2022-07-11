package com.zhongpengcheng.spine.io.context;

import cn.hutool.core.util.ObjectUtil;
import com.zhongpengcheng.spine.io.pojo.Skeleton;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Date;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 21:06:00
 */
@Slf4j
@Getter
@Setter
public class PipelineContext implements Closeable {
    /**
     * 处理开始时间
     */
    private Date startProcessTime;
    /**
     * 处理结束时间
     */
    private Date endProcessTime;
    /**
     * 骨骼文件路径
     */
    private String skelFilePath;
    /**
     * 管道执行异常时的错误对象
     */
    private Throwable ex;
    /**
     * 获取处理耗时（ms）
     */
    public long getCostTimeMillis() {
        if (ObjectUtil.hasEmpty(startProcessTime, endProcessTime)) return -1;
        return endProcessTime.getTime() - startProcessTime.getTime();
    }

    public Skeleton toSkeleton() {
        throw new UnsupportedOperationException();
    }

        @Override
    public void close() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
