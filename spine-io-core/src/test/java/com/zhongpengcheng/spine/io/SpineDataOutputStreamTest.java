package com.zhongpengcheng.spine.io;

import cn.hutool.core.util.RandomUtil;
import com.zhongpengcheng.spine.io.executor.PipelineExecutor;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.v35.pojo.Head;
import com.zhongpengcheng.spine.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class SpineDataOutputStreamTest {

    @Tag("output")
    @Test
    void testWriteInt() throws IOException {
        String path = "output/test_io_int_" + System.currentTimeMillis() + ".bin";
        int count = 100;
        ArrayList<Integer> list = new ArrayList<>(count);

        try (SpineDataOutputStream output = IOUtils.outputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                int number = RandomUtil.randomInt();
                list.add(number);
                output.writeInteger(number);
            }
        }

        try(SpineDataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                assertEquals(list.get(i), input.readInt(true), count + " " + list.get(i));
            }
        }
    }

    @Tag("output")
    @Test
    void testWriteStr() throws IOException {
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

        try(SpineDataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                String str = input.readString();
                assertEquals(list.get(i), str, count + " " + list.get(i));
                log.info("读取字符串 {}", str);
            }
        }
    }

    @Tag("output")
    @Test
    void testWriteSkel() throws IOException {
        BinaryReaderContext ctx = BinaryReaderContext.of("v35/spineboy/spineboy.skel");
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

        try(SpineDataInputStream input = IOUtils.inputStreamOf(outputPath)) {
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

    @Tag("output")
    @Test
    void testWriteFloatArray() throws IOException {
        String path = "output/test_io_float[]_" + System.currentTimeMillis() + ".bin";
        int count = 10;
        int size = 3;
        ArrayList<Float[]> list = new ArrayList<>();

        try(SpineDataOutputStream output = IOUtils.outputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                Float[] floats = new Float[size];
                for (int j = 0; j < size; j++) {
                    floats[j] = RandomUtil.randomBigDecimal().floatValue();
                }
                list.add(floats);
                output.writeInteger(floats.length);
                output.writeFloatArray(floats);
            }
        }

        try(SpineDataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                Float[] floats = input.readFloatArray(input.readInt(true));
                log.info("读取到32位浮点数组：{}", Arrays.toString(floats));
                assertArrayEquals(list.get(i), floats);
            }
        }
    }

    @Tag("output")
    @Test
    void testWriteShortArray() throws IOException {
        String path = "output/test_io_short[]_" + System.currentTimeMillis() + ".bin";
        int count = 10;
        int size = 3;

        ArrayList<Short[]> list = new ArrayList<>();
        try(SpineDataOutputStream output = IOUtils.outputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                Short[] shorts = new Short[size];
                for (int j = 0; j < size; j++) {
                    shorts[j] = Integer.valueOf(RandomUtil.randomInt(Short.MIN_VALUE, Short.MAX_VALUE)).shortValue();
                }
                list.add(shorts);
                output.writeInteger(shorts.length);
                output.writeShortArray(shorts);
            }
        }

        try(SpineDataInputStream input = IOUtils.inputStreamOf(path)) {
            for (int i = 0; i < count; i++) {
                Short[] shorts = input.readShortArray(input.readInt(true));
                assertArrayEquals(list.get(i), shorts);
                log.info("读取到short数组：{}", Arrays.toString(shorts));
            }
        }
    }
}