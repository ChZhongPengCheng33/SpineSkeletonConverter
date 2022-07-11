package com.zhongpengcheng.spine.io.v35.handler.input;

import com.zhongpengcheng.spine.io.pojo.timeline.*;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.pojo.Animation;
import com.zhongpengcheng.spine.io.pojo.Event;
import com.zhongpengcheng.spine.io.pojo.Path;
import com.zhongpengcheng.spine.io.pojo.Skin;
import com.zhongpengcheng.spine.io.pojo.attachment.IAttachment;
import com.zhongpengcheng.spine.io.stream.SpineDataInputStream;
import com.zhongpengcheng.spine.util.ColorUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动作读取器
 *
 * @author ZhongPengCheng
 * @since 2022-06-06 22:38:00
 */
@Slf4j
public class BinaryAnimationsParser extends AbstractBinaryParser {
    @Override
    public String getName() {
        return super.getName() + ":动作读取器";
    }

    @Override
    public boolean handle(BinaryReaderContext ctx) throws IOException {
        SpineDataInputStream input = ctx.getInput();
        for (int i = 0, animationCount = input.readInt(true); i < animationCount; i++) {
            String animationName = input.readString();
            log.info("读取动画{}", animationName);
            TimelinesReader timelinesReader = new TimelinesReader(ctx);
            Map<String, List<ITimeline>> timelineMap = timelinesReader.read();

            ctx.getAnimations().add(
                    Animation.builder()
                            .name(animationName)
                            .timelineMap(timelineMap)
                            .build()
            );
        }
        return true;
    }

    /**
     * 时间线读取器
     */
    @Slf4j
    public static class TimelinesReader {
        public static final int SLOT_ATTACHMENT = 0;
        public static final int SLOT_COLOR = 1;
        public static final int SLOT_TWO_COLOR = 2;

        static public final int BONE_ROTATE = 0;
        static public final int BONE_TRANSLATE = 1;
        static public final int BONE_SCALE = 2;
        static public final int BONE_SHEAR = 3;

        public static final int PATH_POSITION = 0;
        public static final int PATH_SPACING = 1;
        public static final int PATH_MIX = 2;

        /**
         * 线性曲线
         */
        public static final int CURVE_NONE = 0;
        /**
         * 分段曲线
         */
        public static final int CURVE_STEPPED = 1;
        /**
         * 贝塞尔曲线
         */
        public static final int CURVE_BEZIER = 2;

        public static final String SLOT_KEY = "slots";
        public static final String BONE_KEY = "bones";
        public static final String IK_KEY = "ik";
        public static final String TRANSFORM_KEY = "transform";
        public static final String PATH_KEY = "path";
        public static final String DEFORM_KEY = "deform";
        public static final String DRAW_ORDER_KEY = "drawOrder";
        public static final String EVENT_KEY = "events";

        private final Map<String, List<ITimeline>> timelines = new LinkedHashMap<>();
        private final SpineDataInputStream input;
        private final BinaryReaderContext ctx;


        public TimelinesReader(BinaryReaderContext ctx) {
            this.input = ctx.getInput();
            this.ctx = ctx;
        }

        /**
         * 从输入流中读取指定类型的对象
         *
         * @return 对应对象
         */
        public Map<String, List<ITimeline>> read() throws IOException {
            timelines.put(SLOT_KEY, new ArrayList<>());
            this.readSlotTimelines();

            timelines.put(BONE_KEY, new ArrayList<>());
            this.readBoneTimelines();

            timelines.put(IK_KEY, new ArrayList<>());
            this.readIkTimelines();

            timelines.put(TRANSFORM_KEY, new ArrayList<>());
            this.readTransformTimelines();

            timelines.put(PATH_KEY, new ArrayList<>());
            this.readPathTimelines();

            timelines.put(DEFORM_KEY, new ArrayList<>());
            this.readDeformTimelines();

            timelines.put(DRAW_ORDER_KEY, new ArrayList<>());
            this.readDrawOrderTimelines();

            timelines.put(EVENT_KEY, new ArrayList<>());
            this.readEventTimelines();

            return this.timelines;
        }

        /**
         * 读slot timeline
         */
        private void readSlotTimelines() throws IOException {
            int slotCount = input.readInt(true);
            log.info("读取到{}个带时间线的插槽", slotCount);

            for (int i = 0; i < slotCount; i++) {
                // 读取到当前slot的下标，对应skeleton的slots下标
                int slotIndex = input.readInt(true);

                // 循环读取slot的时间线
                for (int j = 0, timelineCount = input.readInt(true); j < timelineCount; j++) {
                    // 读取时间线类型：attachment、color（rgba）、two_color（rgb、rgba）
                    int timelineType = input.readByte();
                    // 读取时间线帧数量
                    int frameCount = input.readInt(true);
                    // 拿到插槽名称
                    String slotName = ctx.getSlotName(slotIndex);
                    log.info("读取插槽[{}]的时间线数据，时间线个数={}", slotName, timelineCount);

                    switch (timelineType) {
                        // 读取attachment类型的时间线
                        case SLOT_ATTACHMENT: {
                            AttachmentTimeline timeline = new AttachmentTimeline(frameCount);
                            timeline.setSlotIndex(slotIndex);
                            timeline.setSlotName(slotName);
                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                timeline.addFrame(input.readFloat(), input.readString());
                            }
                            timelines.get(SLOT_KEY).add(timeline);
                            break;
                        }
                        // 读取color类型的时间线
                        case SLOT_COLOR: {
                            ColorTimeline timeline = new ColorTimeline(frameCount);
                            timeline.setSlotIndex(slotIndex);
                            timeline.setSlotName(slotName);
                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                float time = input.readFloat();
                                ColorUtils.Color color = ColorUtils.rgba8888ToColor(input.readInt());
                                timeline.addFrame(time, color);
                                if (frameIndex < frameCount - 1) {
                                    this.readCurve(timeline, frameIndex);
                                }
                            }
                            timelines.get(SLOT_KEY).add(timeline);
                            break;
                        }
                        // 读取two_color类型的时间线
                        case SLOT_TWO_COLOR:
                            TwoColorTimeline timeline = new TwoColorTimeline(frameCount);
                            timeline.setSlotIndex(slotIndex);
                            timeline.setSlotName(slotName);
                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                float time = input.readFloat();
                                ColorUtils.Color light = ColorUtils.rgba8888ToColor(input.readInt());
                                ColorUtils.Color dark = ColorUtils.rgb8888ToColor(input.readInt());
                                timeline.addFrame(time, light, dark);
                                if (frameIndex < frameCount - 1) readCurve(timeline, frameIndex);
                            }
                            timelines.get(SLOT_KEY).add(timeline);
                            break;
                        default:
                            log.error("未知的插槽时间（timeline）类型：{}", timelineType);
                    }
                }
            }
        }

        /**
         * 读bone timeline
         */
        private void readBoneTimelines() throws IOException {
            int boneCount = input.readInt(true);
            log.info("读取到{}个带时间线的骨骼", boneCount);

            for (int i = 0; i < boneCount; i++) {
                // 读取到当前bone的下标，对应skeleton的bones下标
                int boneIndex = input.readInt(true);
                // 读取时间线数量
                int timelineCount = input.readInt(true);
                // 拿到骨骼名称
                String boneName = ctx.getBoneName(boneIndex);
                log.info("读取骨骼[{}]的时间线数据，时间线个数={}", boneName, timelineCount);

                for (int j = 0; j < timelineCount; j++) {
                    // 拿到时间线类型：rotate、translate|scale|shear
                    int timelineType = input.readByte();
                    // 读取帧数量
                    int frameCount = input.readInt(true);

                    switch (timelineType) {
                        // 读rotate类型的时间线
                        case BONE_ROTATE: {
                            RotateTimeline timeline = new RotateTimeline(frameCount);
                            timeline.setBoneIndex(boneIndex);
                            timeline.setBoneName(boneName);
                            timeline.setTimelineType("rotate");

                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                timeline.addFrame(input.readFloat(), input.readFloat());
                                if (frameIndex < frameCount - 1) readCurve(timeline, frameIndex);
                            }
                            timelines.get(BONE_KEY).add(timeline);
                            break;
                        }
                        // 读translate|scale|shear类型的时间线
                        case BONE_TRANSLATE:
                        case BONE_SCALE:
                        case BONE_SHEAR: {
                            TranslateTimeline timeline = new TranslateTimeline(frameCount);
                            timeline.setBoneIndex(boneIndex);
                            timeline.setBoneName(boneName);

                            String timelineTypeName = "translate";
                            if (timelineType == BONE_SCALE)
                                timelineTypeName = "scale";
                            else if (timelineType == BONE_SHEAR)
                                timelineTypeName = "shear";
                            timeline.setTimelineType(timelineTypeName);

                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                timeline.addFrame(input.readFloat(), input.readFloat(), input.readFloat());
                                if (frameIndex < frameCount - 1) readCurve(timeline, frameIndex);
                            }
                            timelines.get(BONE_KEY).add(timeline);
                            break;
                        }
                        default:
                            log.error("未知的骨骼时间线（timeline）类型：{}", timelineType);
                    }
                }
            }
        }

        /**
         * 读ik timeline
         */
        private void readIkTimelines() throws IOException {
            // 读ik约束数量
            int ikCount = input.readInt(true);
            log.info("读取到{}个带时间线的IK", ikCount);

            for (int i = 0; i < ikCount; i++) {
                // 获取当前ik约束的下标
                int ikIndex = input.readInt(true);
                // 获取时间线帧数量
                int frameCount = input.readInt(true);

                // 拿到对应ik约束的名称
                String ikName = ctx.getIkName(ikIndex);
                log.info("读取ik约束{}的时间线数据，时间线个数={}", ikName, 1);

                IkTimeline timeline = new IkTimeline(frameCount);
                timeline.setIkIndex(ikIndex);
                timeline.setIkName(ikName);

                for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                    timeline.addFrame(input.readFloat(), input.readFloat(), input.readByte());
                    if (frameIndex < frameCount - 1) readCurve(timeline, frameIndex);
                }

                timelines.get(IK_KEY).add(timeline);
            }
        }

        /**
         * 读取transform timeline
         */
        private void readTransformTimelines() throws IOException {
            int transformCount = input.readInt(true);
            log.info("读取到{}个带时间线的Transform", transformCount);

            for (int i = 0; i < transformCount; i++) {
                int transformIndex = input.readInt(true);
                int frameCount = input.readInt(true);
                String transformName = ctx.getTransformName(transformIndex);
                log.info("读取transform-{}的时间线数据，时间线个数={}", transformName, transformCount);

                TransformTimeline timeline = new TransformTimeline(frameCount);
                timeline.setTransformIndex(transformIndex);
                timeline.setTransformName(transformName);

                for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                    timeline.addFrame(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
                            input.readFloat());
                    if (frameIndex < frameCount - 1) readCurve(timeline, frameIndex);
                }

                timelines.get(TRANSFORM_KEY).add(timeline);
            }
        }

        /**
         * 读取path timelines
         */
        private void readPathTimelines() throws IOException {
            int pathCount = input.readInt(true);
            log.info("读取到{}个带时间线的path", pathCount);

            for (int i = 0; i < pathCount; i++) {
                // 拿到路径下标
                int pathIndex = input.readInt(true);
                int timelineCount = input.readInt(true);
                Path path = ctx.getPath(pathIndex);
                log.info("读取路径[{}]的时间线数据，时间线个数={}", path.getName(), timelineCount);

                // 循环读取路径的时间线
                for (int j = 0; j < timelineCount; j++) {
                    int timelineType = input.readByte();
                    int frameCount = input.readInt(true);

                    switch (timelineType) {
                        case PATH_POSITION:
                        case PATH_SPACING: {
                            PathPositionTimeline timeline = new PathPositionTimeline(frameCount);
                            if (timelineType == PATH_SPACING) timeline.setTimelineType("spacing");
                            timeline.setPathIndex(pathIndex);
                            timeline.setPathName(path.getName());

                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                timeline.addFrame(input.readFloat(), input.readFloat());
                                if (frameIndex < frameCount - 1) this.readCurve(timeline, frameIndex);
                            }

                            timelines.get(PATH_KEY).add(timeline);

                            break;
                        }
                        case PATH_MIX: {
                            PathMixTimeline timeline = new PathMixTimeline(frameCount);
                            timeline.setPathIndex(pathIndex);
                            timeline.setPathName(path.getName());

                            for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                                timeline.addFrame(input.readFloat(), input.readFloat(), input.readFloat());
                                if (frameIndex < frameCount - 1) this.readCurve(timeline, frameIndex);
                            }

                            timelines.get(PATH_KEY).add(timeline);

                            break;
                        }
                        default:
                            log.error("未知的路径时间线类型：{}", timelineType);
                    }
                }
            }
        }

        /**
         * 读取deform timelines
         */
        private void readDeformTimelines() throws IOException {
            int deformCount = input.readInt(true);
            log.info("读取到[{}]个带时间线的deform", deformCount);

            for (int i = 0; i < deformCount; i++) {
                Skin skin = ctx.getSkins().get(input.readInt(true));
                int slotCount = input.readInt(true);

                for (int j = 0; j < slotCount; j++) {
                    int slotIndex = input.readInt(true);
                    int timelineCount = input.readInt(true);

                    for (int k = 0; k < timelineCount; k++) {
                        String attachmentName = input.readString();
                        IAttachment attachment = skin.attachmentOf(attachmentName, slotIndex);

                        boolean weighted = attachment.weighted();
                        float[] vertices = attachment.vertices();
                        int deformLength = weighted ? vertices.length / 3 * 2 : vertices.length;
                        int frameCount = input.readInt(true);

                        DeformTimeline timeline = new DeformTimeline(frameCount);
                        timeline.setSkinIndex(slotIndex);
                        timeline.setAttachmentName(attachmentName);

                        for (int frameIndex = 0; frameIndex < frameCount; frameIndex++) {
                            float time = input.readFloat();
                            float[] deform;
                            int end = input.readInt(true);
                            if (end == 0) {
                                deform = weighted ? new float[deformLength] : vertices;
                            } else {
                                deform = new float[deformLength];
                                int start = input.readInt(true);
                                end += start;
                                for (int v = start; v < end; v++) {
                                    deform[v] = input.readFloat();
                                }
                                if (!weighted) {
                                    for (int v = 0, vn = deform.length; v < vn; v++)
                                        deform[v] += vertices[v];
                                }
                            }
                            timeline.addFrame(time, deform);
                            if (frameIndex < frameCount - 1) this.readCurve(timeline, frameIndex);
                        }

                        timelines.get(DEFORM_KEY).add(timeline);
                    }
                }
            }
        }

        /**
         * 读取draw order timelines
         */
        private void readDrawOrderTimelines() throws IOException {
            int frameCount = input.readInt(true);
            if (frameCount < 1) return;

            DrawOrderTimeline timeline = new DrawOrderTimeline(frameCount);

            for (int i = 0; i < frameCount; i++) {
                float time = input.readFloat();
                int offsetCount = input.readInt(true);

                ArrayList<DrawOrderTimeline.Offset> offsets = new ArrayList<>();
                for (int k = 0; k < offsetCount; k++) {
                    int slotIndex = input.readInt(true);
                    int offset = input.readInt(true);
                    String slotName = ctx.getSlotName(slotIndex);

                    offsets.add(new DrawOrderTimeline.Offset(slotIndex, offset, slotName));
                }

                timeline.addFrame(time, offsets);
            }
            timelines.get(DRAW_ORDER_KEY).add(timeline);
        }

        /**
         * 读取event timelines
         */
        private void readEventTimelines() throws IOException {
            int frameCount = input.readInt(true);
            if (frameCount < 1) return;

            EventTimeline timeline = new EventTimeline(frameCount);

            for (int i = 0; i < frameCount; i++) {
                float time = input.readFloat();
                int eventIndex = input.readInt(true);
                Event rawEvent = ctx.getEvents().get(eventIndex);

                timeline.setEventName(rawEvent.getName());

                timeline.addFrame(
                        time,
                        rawEvent.getName(),
                        input.readInt(true),
                        input.readFloat(),
                        input.readBoolean() ? input.readString() : rawEvent.getAString());
            }

            timelines.get(EVENT_KEY).add(timeline);
        }

        /**
         * 读取关键帧切换曲线
         *
         * @param timeline 曲线时间线，继承自{@link CurveTimeline}
         */
        private void readCurve(CurveTimeline timeline, Integer frameIndex) throws IOException {
            byte curveType = input.readByte();
            switch (curveType) {
                case CURVE_NONE:
                    break;
                case CURVE_STEPPED:
                    timeline.setStepped(true);
                    timeline.addFrameIndex(frameIndex);
                    break;
                case CURVE_BEZIER:
                    timeline.setCurve(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat());
                    timeline.addFrameIndex(frameIndex);
                    break;
                default:
                    // 如果时间线没有曲线，会读取到curveType为0
                    log.error("未知的曲线（curve）类型：{}", curveType);
            }
        }
    }
}
