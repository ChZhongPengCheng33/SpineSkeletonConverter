package com.zhongpengcheng.spine.io;

import com.zhongpengcheng.spine.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SpineDataInputStreamTest {
    @Test
    void testReadV38Skeleton() throws IOException {
        String path = "v38/coin/coin-pro.skel";
        try (SpineDataInputStream input = IOUtils.inputStreamOf(path)) {
            log.info("hash        \t: {}", input.readString());
            log.info("version     \t: {}", input.readString());
            log.info("x           \t: {}", input.readFloat());
            log.info("y           \t: {}", input.readFloat());
            log.info("width       \t: {}", input.readFloat());
            log.info("height      \t: {}", input.readFloat());
            boolean nonessential = input.readBoolean();
            log.info("nonessential\t: {}", nonessential);
            if (nonessential) {
                log.info("fps         \t: {}", input.readFloat());
                log.info("imagesPath  \t: {}", input.readString());
                log.info("audioPath   \t: {}", input.readString());
            }
            int stringCount = input.readInt(true);
            log.info("strings size\t: {}", stringCount);
            for (int i = 0; i < stringCount; i++) {
                log.info("\t{}.{}", i + 1, input.readString());
            }
        }
    }
}