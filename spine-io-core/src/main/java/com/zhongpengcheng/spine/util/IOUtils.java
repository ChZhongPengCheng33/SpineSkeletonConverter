package com.zhongpengcheng.spine.util;

import cn.hutool.core.io.FileUtil;
import com.zhongpengcheng.spine.io.v35.Spine35DataInputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * IO工具类
 *
 * @author zhongpengcheng
 * @since 2022-01-26 14:10:10
 **/
@Slf4j
public class IOUtils {
    /**
     * 从文件读取spine骨骼文件输入流
     * @param file 目标文件
     * @return 可能为空
     */
    public static Spine35DataInputStream inputStreamOf(File file) {
        try {
            return new Spine35DataInputStream(new BufferedInputStream(new FileInputStream(file), 512));
        } catch (FileNotFoundException e) {
            log.error("从文件读取流异常：" + file.getName(), e);
        }

        return null;
    }

    /**
     * 从指定路径读取spine骨骼文件输入流
     * @param url 文件路径
     * @return 可能为空
     */
    public static Spine35DataInputStream inputStreamOf(String url) {
        return inputStreamOf(FileUtil.file(url));
    }

}
