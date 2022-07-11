package com.zhongpengcheng.spine;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.zhongpengcheng.spine.enums.SpineVersion;
import com.zhongpengcheng.spine.exception.SpineIOException;
import com.zhongpengcheng.spine.io.context.PipelineContext;
import com.zhongpengcheng.spine.io.executor.PipelineExecutor;
import com.zhongpengcheng.spine.io.pojo.Skeleton;
import com.zhongpengcheng.spine.io.stream.SpineDataInputStream;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.util.IOUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * spine IO门面
 *
 * @author zhongpengcheng
 * @since 2022-07-11 11:25:25
 **/
@Slf4j
public class SpineIO {

    /**
     * 从文件解析二进制skel文件
     * @param file 文件对象
     * @return 骨骼对象
     */
    public static Skeleton parseBinSkel(final File file) {
        return parseBinSkel(file, null);
    }

    /**
     * 从文件解析二进制skel文件
     * @param file 文件对象
     * @param version 骨骼文件版本，如果为null会推断该文件的版本
     * @return 骨骼对象
     */
    public static Skeleton parseBinSkel(final File file, final SpineVersion version) {
        return parseBinSkel(FileUtil.getInputStream(file), version);
    }

    /**
     * 从输入流解析二进制skel文件
     * @param stream 输入流
     * @param version 骨骼文件版本，如果为null会推断该文件的版本
     * @return 骨骼对象
     */
    public static Skeleton parseBinSkel(final InputStream stream, SpineVersion version) {
        if (version == null) {
            version = inferVersion(stream);
        }
        try (SpineDataInputStream input = IOUtils.inputStreamOf(stream)) {
            PipelineContext ctx = null;
            switch (Objects.requireNonNull(version)) {
                case V_3_5_51:
                    ctx = BinaryReaderContext.of(input);
                    break;
                case V_3_8_89:
                    throw new SpineIOException("暂不支持该版本的文件: " + version);
                default:
                    log.info("未知的spine version: {}", version);
                    throw new SpineIOException(StrUtil.format("未知的spine version: {}", version));
            }
            boolean success = PipelineExecutor.acceptSync(ctx);
            PipelineContext finalCtx = ctx;
            Assert.isTrue(success, () -> new SpineIOException(finalCtx.getEx()));

            return ctx.toSkeleton();
        } catch (IOException e) {
            throw new SpineIOException(e);
        }
    }

    /**
     * 推断目标骨骼文件的spine版本
     * @param file 文件对象
     * @return 如果推断失败，返回null
     */
    public static SpineVersion inferVersion(final File file) {
        return inferVersion(FileUtil.getInputStream(file));
    }

    /**
     * 推断目标骨骼文件的spine版本
     * @param inputStream 输入流
     * @return 如果推断失败，返回null
     */
    public static SpineVersion inferVersion(final InputStream inputStream) {
        try (SpineDataInputStream input = IOUtils.inputStreamOf(inputStream)) {
            if (input == null) return null;
            input.readString();
            String version = input.readString();
            for (SpineVersion spineVersion : SpineVersion.values()) {
                if (spineVersion.matchMajorAndMinor(version)) {
                    return spineVersion;
                }
            }
        } catch (IOException e) {
            throw new SpineIOException(e);
        }
        throw new SpineIOException("推导骨骼版本失败");
    }
}
