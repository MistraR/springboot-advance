package com.advance.mistra.common.threadpool;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/5/13 15:57
 * @ Description:
 */
@Slf4j
public class YMCPUThreadPool extends YMThreadPool {

    public YMCPUThreadPool(Integer poolSize, Integer poolQueueSize, EnableDiversityThreadPool annotation) {
        init(poolSize, poolQueueSize, annotation);
    }

    @Override
    boolean expansion() {
        return threadPoolExecutor.getQueue().size() > threadPoolExecutor.getCorePoolSize() * 300;
    }

    @Override
    void init(Integer poolSize, Integer poolQueueSize, EnableDiversityThreadPool annotation) {
        threadPoolExecutor = new ThreadPoolExecutor(
                Math.min(availableProcessors, poolSize) + 1,
                Math.min(availableProcessors, poolSize) + 1,
                annotation.keepAliveTime(), TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(poolQueueSize),
                new NamedThreadFactory("CPU-", false));
        threadPoolExecutor.allowCoreThreadTimeOut(annotation.allowCoreThreadTimeOut());
    }

    @Override
    void executeTask(Runnable task) {
        check();
        threadPoolExecutor.execute(task);
    }

    @Override
    ThreadPoolTaskType poolType() {
        return ThreadPoolTaskType.CPU;
    }
}
