package com.zhongpengcheng.spine.io.v35.context;

import com.zhongpengcheng.spine.io.SpineDataInputStream;
import com.zhongpengcheng.spine.util.IOUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 二进制骨骼文件读取上下文
 * @author ZhongPengCheng
 * @since 2022-06-06 21:28:00
 */
@Slf4j
@Getter
@Setter
public class BinaryReaderContext extends AbstractContext {

    /**
     * 输入流
     */
    private SpineDataInputStream input;

    public static BinaryReaderContext of(String url) {

        BinaryReaderContext ctx = of(new SpineDataInputStream(IOUtils.inputStreamOf(url)));

        ctx.setSkelFilePath(url);

        return ctx;
    }

    public static BinaryReaderContext of(SpineDataInputStream input) {
        BinaryReaderContext ctx = new BinaryReaderContext();
        ctx.setInput(input);
        return ctx;
    }

    @Override
    public void close() {
        try {
            input.close();
            log.debug("[{}]-关闭文件输入流成功", this);
        } catch (IOException e) {
            log.error("[{}]-关闭文件输入流异常: {}", this, e);
        } catch (Throwable e) {
            log.error("[{}]-未知异常: {}", this, e);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "-二进制骨骼读取上下文";
    }
}
