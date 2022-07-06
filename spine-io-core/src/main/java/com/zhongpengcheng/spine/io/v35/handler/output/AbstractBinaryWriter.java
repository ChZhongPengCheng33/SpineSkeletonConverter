package com.zhongpengcheng.spine.io.v35.handler.output;

import com.zhongpengcheng.spine.io.handler.ContextHandler;
import com.zhongpengcheng.spine.io.v35.context.BinaryWriterContext;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象二进制写入
 *
 * @author zhongpengcheng
 * @since 2022-07-06 14:33:18
 **/
@Slf4j
@Getter
@Setter
public abstract class AbstractBinaryWriter implements ContextHandler<BinaryWriterContext> {
    @Override
    public String getName() {
        return "Spine v3.5二进制骨骼";
    }
}
