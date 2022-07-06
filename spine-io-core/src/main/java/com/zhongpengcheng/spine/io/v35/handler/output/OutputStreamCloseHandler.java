package com.zhongpengcheng.spine.io.v35.handler.output;

import com.zhongpengcheng.spine.io.v35.context.BinaryWriterContext;

import java.io.IOException;

/**
 * 输出流关闭处理器
 *
 * @author zhongpengcheng
 * @since 2022-07-06 14:50:22
 **/
public class OutputStreamCloseHandler extends AbstractBinaryWriter {
    @Override
    public String getName() {
        return "输出流关闭处理器";
    }

    @Override
    public boolean handle(BinaryWriterContext ctx) throws IOException {
        ctx.close();
        return true;
    }
}
