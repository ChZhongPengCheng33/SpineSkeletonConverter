package com.zhongpengcheng.spine.io.pojo;

import com.zhongpengcheng.spine.annotation.Ignore;
import com.zhongpengcheng.spine.annotation.Order;
import com.zhongpengcheng.spine.annotation.SpineIOField;
import com.zhongpengcheng.spine.enums.SpineVersion;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 骨骼文件头信息
 * @author ZhongPengCheng
 * @since 2021-03-01 23:20:00
 */
@Data
@Builder
@EqualsAndHashCode(of = "hash")
public class Head {
    /**
     * 所有skeleton数据的哈希值. 该值可被工具用来检测数据在上次加载后是否有变化.
     */
    private String hash;
    /**
     * 导出这份数据的Spine版本. 该值可以被工具用来在读取时保持特定Spine版本.
     */
    private String version;
    /**
     * skeleton附件的AABB宽度, 与Spine中的setup pose相同. 虽然骨架的AABB取决于其摆放方式, 但可将此参数作为skeleton的大概尺寸.
     */
    private Float width;
    /**
     * skeleton附件的AABB高度, 与Spine中的setup pose相同.
     */
    private Float height;
    /**
     * 非必要的参数: 如果该参数为false, 则标记为非必要的参数将被忽略.
     */
    @Builder.Default
    private Boolean nonessential = Boolean.FALSE;
    /**
     * Dopesheet的帧率, 单位为帧数/每秒, 与Spine中一致. 非必要的参数.
     */
    @Order(3)
    @SpineIOField(nonessential = true)
    private Float fps;
    /**
     * 图像路径, 与Spine中一致. 非必要的参数.
     */
    @Order(1)
    @SpineIOField(nonessential = true)
    private String images;
    /**
     * 音频路径, 与Spine中一致. 非必要的参数.
     */
    @Order(2)
    @Ignore
    @SpineIOField(version = SpineVersion.V_3_8_89, nonessential = true)
    private String audio;
}
