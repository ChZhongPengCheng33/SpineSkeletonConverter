package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 输入流关闭处理器
 *
 * @author zhongpengcheng
 * @since 2022-07-06 14:36:28
 **/
@Slf4j
public class InputStreamCloseHandler extends AbstractBinaryReader {
    @Override
    public String getName() {
        return super.getName() + ":输入流关闭处理器";
    }

    @Override
    public boolean handle(BinaryReaderContext ctx) throws IOException {
        ctx.close();
        return true;
    }
}
