package com.zhongpengcheng.spine.io.v35;

import cn.hutool.core.util.RandomUtil;
import com.zhongpengcheng.spine.io.SpineDataOutputStream;
import com.zhongpengcheng.spine.io.executor.PipelineExecutor;
import com.zhongpengcheng.spine.io.v35.context.BinaryContext;
import com.zhongpengcheng.spine.io.v35.pojo.Head;
import com.zhongpengcheng.spine.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class Spine35DataInputStreamTest {

    @Test
    void testReadWrite() throws IOException {
        String path = "output/test_io_int_" + System.currentTimeMillis() + ".bin";
        int count = 10000;
        ArrayList<Integer> list = new ArrayList<>(count);

        try (SpineDataOutputStream output = IOUtils.outputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                int number = RandomUtil.randomInt();
                list.add(number);
                output.writeInteger(number);
            }
        }

        try(Spine35DataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                assertEquals(list.get(i), input.readInt(true), count + " " + list.get(i));
            }
        }
    }

    @Test
    void testReadWriteStr() throws IOException {
        String path = "output/test_io_str_" + System.currentTimeMillis() + ".bin";
        int count = 100;
        ArrayList<String> list = new ArrayList<>(count);

        try (SpineDataOutputStream output = IOUtils.outputStreamOf(path)) {
            for (int i = 1; i <= count; i++) {
                String element = "start_中间_" + RandomUtil.randomString(i) + "_end";
                element = i % 10 == 0 ? "" : element;
                list.add(element);
                output.writeString(element);
            }
        }

        try(Spine35DataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                String str = input.readString();
                assertEquals(list.get(i), str, count + " " + list.get(i));
                log.info("读取字符串 {}", str);
            }
        }
    }

    @Test
    void testWriteSkel() throws IOException {
        BinaryContext ctx = BinaryContext.of("spineboy/spineboy.skel");
        boolean result = PipelineExecutor.acceptSync(ctx);

        assertTrue(result);
        ctx.close();

        String outputPath = "output/test_io_skel_" + System.currentTimeMillis() + ".bin";
        Head head = ctx.getHead();

        try(SpineDataOutputStream out = IOUtils.outputStreamOf(outputPath)) {
            out.writeString(head.getHash());
            out.writeString(head.getVersion());
            out.writeFloat(head.getWidth());
            out.writeFloat(head.getHeight());
            out.writeBoolean(head.getNonessential());

            if (head.getNonessential()) {
                out.writeFloat(head.getFps());
                out.writeString(head.getImages());
            }
        }

        try(Spine35DataInputStream input = IOUtils.inputStreamOf(outputPath)) {
            assertEquals(head.getHash(), input.readString());
            assertEquals(head.getVersion(), input.readString());
            assertEquals(head.getWidth(), input.readFloat());
            assertEquals(head.getHeight(), input.readFloat());
            assertEquals(head.getNonessential(), input.readBoolean());

            if (head.getNonessential()) {
                assertEquals(head.getFps(), input.readFloat());
                assertEquals(head.getImages(), input.readString());
            }
        }
    }
}