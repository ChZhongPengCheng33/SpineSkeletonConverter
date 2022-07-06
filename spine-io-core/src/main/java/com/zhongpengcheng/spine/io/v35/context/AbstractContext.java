package com.zhongpengcheng.spine.io.v35.context;

import cn.hutool.core.collection.CollectionUtil;
import com.zhongpengcheng.spine.io.context.PipelineContext;
import com.zhongpengcheng.spine.io.v35.pojo.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ZhongPengCheng
 * @since 2022-06-07 20:45:00
 */
@Slf4j
@Getter
@Setter
public abstract class AbstractContext extends PipelineContext {
    /**
     * 当slot没有颜色时的默认值
     */
    public static final int NO_SLOT_COLOR = -1;
    /**
     * 默认骨骼数量，这是一个由经验值，用于优化ArrayList性能，减少扩容的发生
     */
    public static final int DEFAULT_BONE_SIZE = 1 << 5;
    /**
     * ik的bendPositive属性的默认值
     */
    public static final Byte DEFAULT_BEND_POSITIVE = 1;
    /**
     * 是否需读取非必须数据
     */
    private boolean nonessential = false;
    /**
     * 文件头信息
     */
    private Head head;
    /**
     * 节点列表
     */
    private List<Bone> bones = new ArrayList<>(DEFAULT_BONE_SIZE);
    /**
     * 插槽列表
     */
    private List<Slot> slots = new ArrayList<>();
    /**
     * ik约束列表
     */
    private List<Ik> iks = new ArrayList<>();
    /**
     * 旋转列表
     */
    private List<Transform> transforms = new ArrayList<>();
    /**
     * 路径列表
     */
    private List<Path> paths = new ArrayList<>();
    /**
     * 皮肤列表
     */
    private List<Skin> skins = new ArrayList<>();
    /**
     * 事件列表
     */
    private List<Event> events = new ArrayList<>();
    /**
     * 动作列表
     */
    private List<Animation> animations = new ArrayList<>();

    /**
     * 获取指定index的bone的名称
     */
    public String getBoneName(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.bones, index)).orElseGet(Bone::new).getName();
    }

    /**
     * 查找第一个匹配到的名称的骨骼的下标
     * @param boneName 骨骼名称
     * @return 骨骼下标
     */
    public int getBoneIndex(final String boneName) {
        int index = 0;
        if (CollectionUtil.isEmpty(bones)) return index;

        for (int i = 0; i < bones.size(); i++) {
            if (boneName.equals(bones.get(i).getName())) {
                index = i;
                break;
            }
        }

        return index;
    }

    /**
     * 获取指定index的slot的名称
     */
    public String getSlotName(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.slots, index)).orElseGet(Slot::new).getName();
    }

    /**
     * 获取指定index的ik的名称
     */
    public String getIkName(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.iks, index)).orElseGet(Ik::new).getName();
    }

    /**
     * 获取指定index的transform的名称
     */
    public String getTransformName(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.transforms, index)).orElseGet(Transform::new).getName();
    }

    /**
     * 获取指定index的path的名称
     */
    public String getPathName(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.paths, index)).orElseGet(Path::new).getName();
    }

    /**
     * 获取指定index的path
     */
    public Path getPath(int index) {
        return Optional.ofNullable(CollectionUtil.get(this.paths, index)).orElseGet(Path::new);
    }

    @Override
    public String toString() {
        return "Spine 3.5.*";
    }
}
