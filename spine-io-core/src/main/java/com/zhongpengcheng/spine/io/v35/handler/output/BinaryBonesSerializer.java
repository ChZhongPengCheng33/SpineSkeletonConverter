package com.zhongpengcheng.spine.io.v35.handler.output;

import com.zhongpengcheng.spine.io.stream.SpineDataOutputStream;
import com.zhongpengcheng.spine.io.v35.context.BinaryWriterContext;
import com.zhongpengcheng.spine.io.v35.enums.TransformMode;
import com.zhongpengcheng.spine.io.pojo.Bone;
import com.zhongpengcheng.spine.util.ColorUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * 二进制bones写入器
 * @author zhongpengcheng
 * @since 2022-07-06 15:08:17
 **/
@Slf4j
public class BinaryBonesSerializer extends AbstractBinarySerializer {
    @Override
    public String getName() {
        return super.getName() + ":二进制骨骼文件bones写入";
    }

    @Override
    public boolean handle(BinaryWriterContext ctx) throws IOException {
        SpineDataOutputStream output = ctx.getOutput();
        List<Bone> bones = ctx.getBones();

        output.writeInteger(bones.size());
        for (Bone bone : bones) {
            output.writeString(bone.getName());

            if (bone.getParent() == null) {
                output.writeInteger(0);
            } else {
                output.writeInteger(ctx.getBoneIndex(bone.getParent()));
            }

            output.writeFloat(bone.getRotation());
            output.writeFloat(bone.getX());
            output.writeFloat(bone.getY());
            output.writeFloat(bone.getScaleX());
            output.writeFloat(bone.getScaleY());
            output.writeFloat(bone.getShearX());
            output.writeFloat(bone.getShearY());
            output.writeFloat(bone.getLength());

            if (bone.getTransformMode() == null) {
                output.writeInteger(TransformMode.NORMAL.getIndex());
            } else {
                output.writeInteger(TransformMode.ofCode(bone.getTransformMode()).getIndex());
            }

            if (ctx.isNonessential()) {
                output.writeInt(ColorUtils.hexColorToRgba(bone.getColor()));
            }
        }

        return true;
    }
}
