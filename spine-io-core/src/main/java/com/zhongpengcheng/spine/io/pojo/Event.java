package com.zhongpengcheng.spine.io.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * 事件
 *
 * @author ZhongPengCheng
 * @since 2021-03-03 14:28:00
 */
@Data
@Builder
public class Event {
    /**
     * 事件的名称. 该参数在一个skeleton中是唯一的.
     */
    String name;
    /**
     * 事件的int值.
     */
    @Builder.Default
    Integer aInt = 0;
    /**
     * 事件的float值.
     */
    @Builder.Default
    Float aFloat = 0F;
    /**
     * 事件的string值.
     */
    @Builder.Default
    String aString = "";
}
