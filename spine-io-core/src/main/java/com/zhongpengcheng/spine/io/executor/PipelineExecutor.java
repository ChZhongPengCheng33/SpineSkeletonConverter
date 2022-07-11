package com.zhongpengcheng.spine.io.executor;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import com.zhongpengcheng.spine.io.handler.CommonPreHandler;
import com.zhongpengcheng.spine.io.handler.ContextHandler;
import com.zhongpengcheng.spine.io.context.PipelineContext;
import com.zhongpengcheng.spine.io.handler.CommonPostHandler;
import com.zhongpengcheng.spine.io.route.PipelineRoute;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 21:49:00
 */
@Slf4j
public class PipelineExecutor {
    /**
     * 路由map
     */
    private static final Map<Class<? extends PipelineContext>, List<Object>> routeMap;
    /**
     * 通用前置处理
     */
    private static final CommonPreHandler commonPreHandler;
    /**
     * 通用后置处理
     */
    private static final CommonPostHandler commonPostHandler;

    static {
        commonPreHandler = new CommonPreHandler();
        commonPostHandler = new CommonPostHandler();
        routeMap = PipelineRoute.getRoute()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> new ArrayList<>(entry.getValue())));

    }

    /**
     * 同步执行管道
     * @param ctx 管道上下文
     * @return 执行结果
     */
    @SuppressWarnings("unchecked")
    public static boolean acceptSync(final PipelineContext ctx) {
        if (ctx == null) {
            log.error("管道上下文为空");
            return false;
        }

        Class<? extends PipelineContext> ctxClass = ctx.getClass();
        List<Object> pipelines = routeMap.get(ctxClass);
        if (CollectionUtil.isEmpty(pipelines)) {
            log.error("管道[{}]上下文对应的管道为空: ctxClass={}", ctx, ctxClass.getSimpleName());
            ctx.setEx(new IllegalArgumentException("上下文对应的管道为空"));
            return false;
        }

        commonPreHandler.handle(ctx);
        boolean finalRet = true;
        StopWatch sw = new StopWatch();

        for (Object pipeline : pipelines) {
            ContextHandler<? super PipelineContext> handler = (ContextHandler<? super PipelineContext>) pipeline;
            try {
                sw.start(handler.getName());
                finalRet = handler.handle(ctx);
                sw.stop();
                log.debug("管道[{}]的执行器[{}]执行完毕，耗时[{}]ms", ctx, handler.getName(), sw.getLastTaskTimeMillis());
            } catch (Throwable ex) {
                finalRet = false;
                ctx.close();
                ctx.setEx(ex);
                log.error("管道[{}]执行异常: {}", ctx, ex.getMessage());
            }
            if (!finalRet) {
                log.error("管道[{}]的执行器[{}]返回执行失败，result={}", ctx, handler.getName(), finalRet);
                break;
            }
        }
        commonPostHandler.handle(ctx);
        return finalRet;
    }
}
