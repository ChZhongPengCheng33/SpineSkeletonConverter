package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.v35.pojo.Head;
import com.zhongpengcheng.spine.io.SpineDataInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 文件头读取器
 *
 * @author ZhongPengCheng
 * @since 2022-06-06 22:19:00
 */
@Slf4j
public class BinaryHeadReader extends AbstractBinaryReader {
    @Override
    public String getName() {
        return super.getName() + ":文件头读取器";
    }

    @Override
    public boolean handle(BinaryReaderContext ctx) throws IOException {
        SpineDataInputStream input = ctx.getInput();

        Head.HeadBuilder builder = Head.builder()
                .hash(input.readString())
                .version(input.readString())
                .width(input.readFloat())
                .height(input.readFloat());

        boolean nonessential = input.readBoolean();
        ctx.setNonessential(nonessential);

        if (nonessential) {
            builder.nonessential(true)
                    .fps(input.readFloat())
                    .images(input.readString());
        }
        ctx.setHead(builder.build());

        return true;
    }
}
