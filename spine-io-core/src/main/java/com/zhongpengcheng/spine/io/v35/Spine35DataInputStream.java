package com.zhongpengcheng.spine.io.v35;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Spine数据读取流
 *
 * @author zhongpengcheng
 * @since 2022-01-25 15:33:22
 **/
@Slf4j
public class Spine35DataInputStream extends DataInputStream {
    private char[] chars = new char[32];

    public Spine35DataInputStream(InputStream in) {
        super(in);
    }

    /**
     * Reads a 1-5 byte int.
     */
    public int readInt(boolean optimizePositive) throws IOException {
        int b = read();
        // 取该byte作为该Integer的低1-7位
        int result = b & 0x7F;
        // 当且仅当该byte的最高位为1时继续读取，即 1000 0000（128） ~ 1111 1111（255），或[0, -127]
        if ((b & 0x80) != 0) {
            b = read();
            // 屏蔽符号位，然后左移7位，最后通过|操作累加到现有结果上
            result |= (b & 0x7F) << 7;
            if ((b & 0x80) != 0) {
                b = read();
                // 屏蔽符号位，然后左移14位，最后通过|操作累加到现有结果上
                result |= (b & 0x7F) << 14;
                if ((b & 0x80) != 0) {
                    b = read();
                    // 屏蔽符号位，然后左移21位，最后通过|操作累加到现有结果上
                    result |= (b & 0x7F) << 21;
                    if ((b & 0x80) != 0) {
                        b = read();
                        // 屏蔽符号位，然后左移28位，此时仅剩余该byte的右4位，右4位的第一位作为符号位，剩余的右3位作为数字
                        result |= (b & 0x7F) << 28;
                    }
                }
            }
        }
        return optimizePositive ? result : ((result >>> 1) ^ -(result & 1));
    }

    /**
     * Reads the length and string of UTF8 characters, or null.
     *
     * @return May be null.
     */
    public String readString() throws IOException {
        int byteCount = readInt(true);
        switch (byteCount) {
            case 0:
                return null;
            case 1:
                return "";
            default:
        }
        byteCount--;
        if (chars.length < byteCount) chars = new char[byteCount];
        char[] chars = this.chars;
        int charCount = 0;
        for (int i = 0; i < byteCount; ) {
            int b = read();
            switch (b >> 4) {
                case -1:
                    throw new EOFException();
                case 12:
                case 13:
                    chars[charCount++] = (char) ((b & 0x1F) << 6 | read() & 0x3F);
                    i += 2;
                    break;
                case 14:
                    chars[charCount++] = (char) ((b & 0x0F) << 12 | (read() & 0x3F) << 6 | read() & 0x3F);
                    i += 3;
                    break;
                default:
                    chars[charCount++] = (char) b;
                    i++;
            }
        }
        return new String(chars, 0, charCount);
    }

    /**
     * 读取一个UTF8格式的字符串
     *
     * @param defaultValue 字符串为空时返回默认值
     * @return 读取到的非空字符串或传入的默认值
     */
    public String readString(String defaultValue) throws IOException {
        String str = this.readString();
        return str == null || "".equals(str) ? defaultValue : str;
    }

    /**
     * 读取长度为n的float数组
     *
     * @param n 数组长度
     */
    public Float[] readFloatArray(int n) throws IOException {
        Float[] array = new Float[n];
        for (int i = 0; i < n; i++) {
            array[i] = this.readFloat();
        }
        return array;
    }

    /**
     * 读取长度为n的short数组
     *
     * @param n 数组长度
     */
    public Short[] readShortArray(int n) throws IOException {
        Short[] array = new Short[n];
        for (int i = 0; i < n; i++) {
            array[i] = this.readShort();
        }
        return array;
    }

    private void readUtf8Slow(int charCount, int charIndex, int b) throws IOException {
        char[] chars = this.chars;
        while (true) {
            switch (b >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    chars[charIndex] = (char) b;
                    break;
                case 12:
                case 13:
                    chars[charIndex] = (char) ((b & 0x1F) << 6 | read() & 0x3F);
                    break;
                case 14:
                    chars[charIndex] = (char) ((b & 0x0F) << 12 | (read() & 0x3F) << 6 | read() & 0x3F);
                    break;
                default:
            }
            if (++charIndex >= charCount) {
                break;
            }
            b = read() & 0xFF;
        }
    }
}
