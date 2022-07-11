package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.handler.ContextHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhongPengCheng
 * @since 2022-06-06 22:08:00
 */
@Slf4j
public abstract class AbstractBinaryParser implements ContextHandler<BinaryReaderContext> {
    @Override
    public String getName() {
        return "Spine v3.5二进制骨骼";
    }

    /**
     * 当slot没有颜色时的默认值
     */
    protected static final int NO_SLOT_COLOR = -1;
    /**
     * 默认骨骼数量，这是一个由经验值，用于优化ArrayList性能，减少扩容的发生
     */
    protected static final int DEFAULT_BONE_SIZE = 1 << 5;
    /**
     * ik的bendPositive属性的默认值
     */
    protected static final Byte DEFAULT_BEND_POSITIVE = 1;

    protected List<String> readDependBones(BinaryReaderContext ctx) throws IOException {
        List<String> dependBones = new ArrayList<>();
        for (int i = 0, boneCount = ctx.getInput().readInt(true); i < boneCount; i++) {
            dependBones.add(ctx.getBoneName(ctx.getInput().readInt(true)));
        }

        return dependBones;
    }
}
