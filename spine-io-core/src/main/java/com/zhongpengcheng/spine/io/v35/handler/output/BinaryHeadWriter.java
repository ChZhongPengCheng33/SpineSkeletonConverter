package com.zhongpengcheng.spine.io.v35.handler.output;

import com.zhongpengcheng.spine.io.SpineDataOutputStream;
import com.zhongpengcheng.spine.io.v35.context.BinaryWriterContext;
import com.zhongpengcheng.spine.io.v35.pojo.Head;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 二进制骨骼文件写入器
 * @author zhongpengcheng
 * @since 2022-07-06 14:39:27
 **/
@Slf4j
public class BinaryHeadWriter extends AbstractBinaryWriter {
    @Override
    public String getName() {
        return super.getName() + ":二进制骨骼文件head写入器";
    }

    @Override
    public boolean handle(BinaryWriterContext ctx) throws IOException {
        Head head = ctx.getHead();
        SpineDataOutputStream output = ctx.getOutput();

        output.writeString(head.getHash());
        output.writeString(head.getVersion());
        output.writeFloat(head.getWidth());
        output.writeFloat(head.getHeight());
        output.writeBoolean(ctx.isNonessential());
        if (ctx.isNonessential()) {
            output.writeFloat(head.getFps());
            output.writeString(head.getImages());
        }

        return true;
    }
}
