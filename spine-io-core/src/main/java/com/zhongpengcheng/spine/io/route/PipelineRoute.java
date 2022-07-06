package com.zhongpengcheng.spine.io.route;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import com.zhongpengcheng.spine.io.context.PipelineContext;
import com.zhongpengcheng.spine.io.handler.ContextHandler;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.v35.context.BinaryWriterContext;
import com.zhongpengcheng.spine.io.v35.handler.input.*;
import com.zhongpengcheng.spine.io.v35.handler.output.BinaryHeadWriter;
import com.zhongpengcheng.spine.io.v35.handler.output.OutputStreamCloseHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 21:27:00
 */
@Slf4j
public class PipelineRoute {
    /**
     * 路由表
     */
    private static final Map<Class<? extends PipelineContext>, List<Class<? extends ContextHandler<? extends PipelineContext>>>> ROUTE_MAP;

    static {
        ROUTE_MAP = new HashMap<>();

        // Spine v3.5.**
        ROUTE_MAP.put(BinaryReaderContext.class, CollectionUtil.toList(
                BinaryHeadReader.class,
                BinaryBonesReader.class,
                BinarySlotsReader.class,
                BinaryIksReader.class,
                BinaryTransformsReader.class,
                BinaryPathsReader.class,
                BinarySkinsReader.class,
                BinaryEventsReader.class,
                BinaryAnimationsReader.class,
                InputStreamCloseHandler.class
        ));
        ROUTE_MAP.put(BinaryWriterContext.class, CollectionUtil.toList(
                BinaryHeadWriter.class,
                OutputStreamCloseHandler.class
        ));
        ROUTE_MAP.put(com.zhongpengcheng.spine.io.v35.context.JsonContext.class, CollectionUtil.toList(
        ));

        // Spine v3.8.**
        ROUTE_MAP.put(com.zhongpengcheng.spine.io.v38.context.BinaryContext.class, CollectionUtil.toList(
        ));
        ROUTE_MAP.put(com.zhongpengcheng.spine.io.v38.context.JsonContext.class, CollectionUtil.toList(
        ));
    }

    /**
     * 获取路由实例
     */
    public static Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> getRoute() {
        try {
            log.debug("获取路由表");
            return InstanceHolder.cacheRoute;
        } finally {
            log.debug("获取路由表结束");
        }
    }

    /**
     * 将管道中的处理器实例化并返回
     */
    private static List<ContextHandler<? extends PipelineContext>> toPipeline(Map.Entry<Class<? extends PipelineContext>,
            List<Class<? extends ContextHandler<? extends PipelineContext>>>> entry) {
        return entry.getValue().stream()
                .map(ReflectUtil::newInstanceIfPossible)
                .collect(Collectors.toList());
    }

    /**
     * 使用静态内部类实现处理器路由表的单例
     */
    public static class InstanceHolder {

        /**
         * 路由实例缓存
         */
        public static Map<Class<? extends PipelineContext>, List<? extends ContextHandler<? extends PipelineContext>>> cacheRoute;

        static {
            log.debug("进行处理器路表初始化");
            cacheRoute = ROUTE_MAP.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, PipelineRoute::toPipeline));
            log.debug("路由表初始化完成");
        }
    }
}