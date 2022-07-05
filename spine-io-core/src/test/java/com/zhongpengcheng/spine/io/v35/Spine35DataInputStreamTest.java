package com.zhongpengcheng.spine.io.v35;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.zhongpengcheng.spine.io.SpineDataOutputStream;
import com.zhongpengcheng.spine.util.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class Spine35DataInputStreamTest {
    int mask1 = 0x7f;
    int mask2 = 0x80;
    @Test
    void testReadStringHead() {
        int data = 0x1c;
        System.out.println(to32BitBinStr(data));
        System.out.println(to32BitBinStr(mask1));
        System.out.println(to32BitBinStr(data & mask1));
        System.out.println(to32BitBinStr((data & mask1) << 7));
        System.out.println(to32BitBinStr((data & mask1) << 14));
        System.out.println(to32BitBinStr((data & mask1) << 21));
        System.out.println(to32BitBinStr((data & mask1) << 28));
        System.out.println(to32BitBinStr(mask2));
        System.out.println(to32BitBinStr(Integer.MAX_VALUE));
    }

    @Test
    void testWriteInteger() {
        int maxValue = Integer.MAX_VALUE;
        int low7to1 = maxValue & 0b1111111;
        int low14to8 = maxValue & 0b11111110000000;
        System.out.println(Integer.toHexString(low7to1));
        System.out.println(Integer.toHexString(low14to8));
    }

    @Test
    void testAdd1() {
        byte b = 0b1111111;
        System.out.println(Integer.toBinaryString(b | 0x80));
        System.out.println(Integer.toBinaryString(b << 1));
        System.out.println(Integer.toBinaryString(b << 1 >> 1));
    }

    @Test
    void testReadInt() throws IOException {
        Spine35DataInputStream input = new Spine35DataInputStream(IOUtils.inputStreamOf("output/intTest.bin"));
        int num1 = input.readInt(true);
        int num2 = input.readInt(true);
        System.out.println(Integer.MAX_VALUE == num1);
        System.out.println(Integer.MAX_VALUE == num2);
        input.close();
    }

    @Test
    void testWriteInt() throws IOException {
        BufferedOutputStream outputStream = FileUtil.getOutputStream("output/" + System.currentTimeMillis() + ".bin");
        SpineDataOutputStream output = new SpineDataOutputStream(outputStream);
        output.writeInteger(1007632432);
        output.close();
    }

    @Test
    void testReadWrite() throws IOException {
        String path = "output/test_io_int_" + System.currentTimeMillis() + ".bin";
        int count = 10000;
        ArrayList<Integer> list = new ArrayList<>(count);
        try (SpineDataOutputStream output = new SpineDataOutputStream(FileUtil.getOutputStream(path))) {
            for (int i = 0; i < count; i++) {
                int number = RandomUtil.randomInt();
                list.add(number);
                output.writeInteger(number);
            }
        }
        try(Spine35DataInputStream input = new Spine35DataInputStream(IOUtils.inputStreamOf(path))) {
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
        try (SpineDataOutputStream output = new SpineDataOutputStream(FileUtil.getOutputStream(path))) {
            for (int i = 0; i < count; i++) {
                String element = RandomUtil.randomString(10 * count);
                element = i % 10 == 0 ? "" : element;
                list.add(element);
                output.writeString(element);
            }
        }
        try(Spine35DataInputStream input = new Spine35DataInputStream(IOUtils.inputStreamOf(path))) {
            for (int i = 0; i < count; i++) {
                assertEquals(list.get(i), input.readString(), count + " " + list.get(i));
            }
        }
    }

    private String to32BitBinStr(int i) {
        String str = Integer.toBinaryString(i);
        if (str.length() >= 32) return str;
        StringBuilder sb = new StringBuilder();
        for (int i1 = 0; i1 < 32 - str.length(); i1++) {
            sb.append("0");
        }
        sb.append(str);

        return sb.toString();
    }
}