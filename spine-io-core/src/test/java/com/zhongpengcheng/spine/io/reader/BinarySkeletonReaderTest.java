package com.zhongpengcheng.spine.io.reader;

import com.alibaba.fastjson.JSON;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import com.zhongpengcheng.spine.io.executor.PipelineExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

@Slf4j
class BinarySkeletonReaderTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "v35/spineboy/spineboy.skel",
            "v35/spineboy/spineboy-hover.skel",
            "v35/spineboy/spineboy-mesh.skel",
            "v35/stretchyman/stretchyman.skel",
            "v35/tank/tank.skel",
    })
    void read(String skelPath) {
        BinaryReaderContext ctx = BinaryReaderContext.of(skelPath);
        boolean success = PipelineExecutor.acceptSync(ctx);
        log.debug("执行结果: " + success);
        log.debug(JSON.toJSONString(ctx));
    }
}