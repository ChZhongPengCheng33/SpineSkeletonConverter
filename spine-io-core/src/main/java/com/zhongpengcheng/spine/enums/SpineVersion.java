package com.zhongpengcheng.spine.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * spine版本
 *
 * @author zhongpengcheng
 * @since 2022-07-11 14:58:28
 **/
@Getter
@Slf4j
public enum SpineVersion {
    /**
     * spine 3.5.51
     */
    V_3_5_51(3, 5, 51),
    /**
     * spine 3.8.89
     */
    V_3_8_89(3, 8, 89),
    /**
     * 通用版本
     */
    GENERAL(0, 0, 0),
    ;

    /**
     * 主版本号
     */
    private final Integer major;

    /**
     * 次版本号
     */
    private final Integer minor;

    /**
     * 构建版本号
     */
    private final Integer build;

    SpineVersion(Integer major, Integer minor, Integer build) {
        this.major = major;
        this.minor = minor;
        this.build = build;
    }

    /**
     * 判断传入版本号是否匹配枚举的版本，仅比较major及minor版本，一般来说major及minor匹配时，文件格式都是兼容的
     *
     * @param versionStr 版本字符串，如3.5.51
     */
    public boolean matchMajorAndMinor(final String versionStr) {
        if (StrUtil.isBlank(versionStr)) {
            log.debug("字符串为空: [{}]", versionStr);
            return false;
        }
        String[] strings = versionStr.trim().split("\\.");
        if (strings.length < 2) {
            log.debug("字符串格式错误: [{}]", versionStr);
            return false;
        }
        if (!String.valueOf(major).equals(Optional.ofNullable(strings[0]).orElse("").trim())) {
            log.debug("主版本号不匹配: [{}]-[{}]", major, strings[0]);
            return false;
        }
        if (!String.valueOf(minor).equals(Optional.ofNullable(strings[1]).orElse("").trim())) {
            log.debug("次版本号不匹配: [{}]-[{}]", minor, strings[1]);
            return false;
        }

        return true;
    }
}
