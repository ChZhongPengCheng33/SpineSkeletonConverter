package com.zhongpengcheng.spine.io.v35.handler;

import com.zhongpengcheng.spine.io.v35.context.BinaryContext;
import com.zhongpengcheng.spine.io.v35.pojo.Head;
import com.zhongpengcheng.spine.io.v35.Spine35DataInputStream;
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
    public boolean handle(BinaryContext ctx) throws IOException {
        Spine35DataInputStream input = ctx.getInput();

        Head.HeadBuilder builder = Head.builder()
                .hash(input.readString(null))
                .version(input.readString(null))
                .width(input.readFloat())
                .height(input.readFloat());

        boolean nonessential = input.readBoolean();
        ctx.setNonessential(nonessential);

        if (nonessential) {
            builder.nonessential(true)
                    .fps(input.readFloat())
                    .images(input.readString(null));
        }
        ctx.setHead(builder.build());

        return true;
    }
}
