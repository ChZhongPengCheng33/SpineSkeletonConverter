package com.zhongpengcheng.spine.io.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author ZhongPengCheng
 * @since 2021-03-01 23:18:00
 */
@Data
@Accessors(chain = true)
@Slf4j
@Builder
public class Skeleton {
    private Head head;
    private List<Bone> bones;
    private List<Slot> slots;
    private List<Ik> iks;
    private List<Transform> transforms;
    private List<Path> paths;
    private List<Skin> skins;
    private List<Event> events;
    private List<Animation> animations;

    public boolean nonessential() {
        if (head == null) return false;
        return Boolean.TRUE.equals(head.getNonessential());
    }
}
