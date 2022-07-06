package com.zhongpengcheng.spine.io;

import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Spine数据输出流
 *
 * @author zhongpengcheng
 * @since 2022-07-05 15:14:57
 **/
@Slf4j
public class SpineDataOutputStream extends DataOutputStream {

    /**
     * 分块mask
     */
    public static final int PART_MASK = 0x7f;
    /**
     * 标记后续的mask
     */
    public static final int NEXT_MASK = 0x80;

    public SpineDataOutputStream(OutputStream out) {
        super(out);
    }

    /**
     * 写入一个1-5字节的整型数字
     */
    public void writeInteger(int i) throws IOException {
        // 分块
        byte part1 = (byte) (i & PART_MASK);
        byte part2 = (byte) (i >>> 7 & PART_MASK);
        byte part3 = (byte) (i >>> 14 & PART_MASK);
        byte part4 = (byte) (i >>> 21 & PART_MASK);
        byte part5 = (byte) (i >>> 28 & PART_MASK);

        // 如果剩余部分至少一个不为0，则高位置1
        if ((part2 | part3 | part4 | part5) != 0) part1 |= NEXT_MASK;
        // 写入第1部分
        writeByte(part1);

        // 如果part2还有后续，则需要高位置1
        if ((part3 | part4 | part5) != 0) part2 |= NEXT_MASK;
        // part2不为0则写入
        if (part2 != 0) writeByte(part2);

        // 如果part3还有后续，则需要高位置1
        if ((part4 | part5) != 0) part3 |= NEXT_MASK;
        // part3不为0则写入
        if (part3 != 0) writeByte(part3);

        // 如果part4还有后续，则需要高位置1
        if ((part5) != 0) part4 |= NEXT_MASK;
        // part4不为0则写入
        if (part4 != 0) writeByte(part4);

        // part5不为0则写入
        if (part5 != 0) writeByte(part5);

    }

    /**
     * 写入一个UTF8编码的字符串
     */
    public void writeString(String target) throws IOException {
        if (target == null) {
            writeInteger(0);
            return;
        }
        if ("".equals(target)) {
            writeInteger(1);
            return;
        }
        byte[] bytes = target.getBytes(StandardCharsets.UTF_8);
        // 写入字符串长度+1
        writeInteger(bytes.length + 1);
        // 写入字符串
        write(bytes);
    }

    /**
     * 写入一个{@link Float}数组
     */
    public void writeFloatArray(Float[] floats) throws IOException {
        if (floats == null) throw new IOException("浮点数组为null");

        for (Float aFloat : floats) {
            writeFloat(aFloat);
        }
    }

    /**
     * 写入一个{@link Short}数组
     */
    public void writeShortArray(Short[] shorts) throws IOException {
        if (shorts == null) throw new IOException("short数组为null");

        for (Short aShort : shorts) {
            writeShort(aShort);
        }
    }
}
