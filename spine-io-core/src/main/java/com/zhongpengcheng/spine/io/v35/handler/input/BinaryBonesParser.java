package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.v35.enums.TransformMode;
import com.zhongpengcheng.spine.io.pojo.Bone;
import com.zhongpengcheng.spine.io.stream.SpineDataInputStream;
import com.zhongpengcheng.spine.util.ColorUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 骨骼节点读取器
 *
 * @author ZhongPengCheng
 * @since 2022-06-06 22:31:00
 */
@Slf4j
public class BinaryBonesParser extends AbstractBinaryParser {
    @Override
    public String getName() {
        return super.getName() + ":骨骼节点读取器";
    }

    @Override
    public boolean handle(BinaryReaderContext ctx) throws IOException {
        SpineDataInputStream input = ctx.getInput();
        for (int i = 0, boneCount = input.readInt(true); i < boneCount; i++) {
            // 读名称
            String name = input.readString();
            // 拿到父骨骼
            Bone parent = i == 0 ? null : ctx.getBones().get(input.readInt(true));
            // 读基础属性
            Bone.BoneBuilder builder = Bone.builder()
                    .name(name)
                    .id(i)
                    .parent(parent == null ? null : parent.getName())
                    .rotation(input.readFloat())
                    .x(input.readFloat())
                    .y(input.readFloat())
                    .scaleX(input.readFloat())
                    .scaleY(input.readFloat())
                    .shearX(input.readFloat())
                    .shearY(input.readFloat())
                    .length(input.readFloat());
            // 读变换模式
            TransformMode transformMode = TransformMode.values()[input.readInt(true)];
            if (transformMode != TransformMode.NORMAL) {
                builder.transformMode(transformMode.getCode());
            }
            // 读非必须属性
            if (ctx.isNonessential()) {
                builder.color(ColorUtils.rgba8888ToHexColor(input.readInt()));
            }
            ctx.getBones().add(builder.build());
        }
        return true;
    }
}
