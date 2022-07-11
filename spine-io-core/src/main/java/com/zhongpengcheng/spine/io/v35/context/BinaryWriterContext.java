package com.zhongpengcheng.spine.io.v35.context;

import com.zhongpengcheng.spine.io.stream.SpineDataOutputStream;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 二进制骨骼写入上下文
 *
 * @author zhongpengcheng
 * @since 2022-07-06 14:34:48
 **/
@Slf4j
@Getter
@Setter
public class BinaryWriterContext extends AbstractContext {
    /**
     * 输出流
     */
    private SpineDataOutputStream output;

    public static BinaryWriterContext of(SpineDataOutputStream output, BinaryReaderContext readerCtx) {
        BinaryWriterContext ctx = new BinaryWriterContext();
        ctx.setOutput(output);
        ctx.setNonessential(readerCtx.isNonessential());
        ctx.setHead(readerCtx.getHead());
        ctx.setBones(readerCtx.getBones());
        ctx.setSlots(readerCtx.getSlots());
        ctx.setIks(readerCtx.getIks());
        ctx.setTransforms(readerCtx.getTransforms());
        ctx.setPaths(readerCtx.getPaths());
        ctx.setSkins(readerCtx.getSkins());
        ctx.setEvents(readerCtx.getEvents());
        ctx.setAnimations(readerCtx.getAnimations());
        return ctx;
    }

    @Override
    public void close() {
        try {
            output.close();
            log.debug("[{}]-关闭文件输出流成功", this);
        } catch (IOException e) {
            log.error("[{}]-关闭文件输出流异常: {}", this, e);
        } catch (Throwable e) {
            log.error("[{}]-未知异常: {}", this, e);
        }
    }

    @Override
    public String toString() {
        return "spine3.5.*-二进制骨骼文件写入上下文";
    }
}
