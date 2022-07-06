package com.zhongpengcheng.spine.io.pipeline.executor;

import com.zhongpengcheng.spine.io.executor.PipelineExecutor;
import com.zhongpengcheng.spine.io.v35.context.BinaryReaderContext;
import org.junit.jupiter.api.Test;

class PipelineExecutorTest {
    @Test
    void testRun() {
        boolean b = PipelineExecutor.acceptSync(new BinaryReaderContext());
        System.out.println(b);
    }
}