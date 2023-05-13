package com.advance.mistra.common.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/5/13 15:51
 * @ Description:
 */
@Slf4j
public abstract class YMThreadPool {

    public static final int availableProcessors = Runtime.getRuntime().availableProcessors();

    protected ThreadPoolExecutor threadPoolExecutor;

    /**
     * 扩容核心线程数
     */
    private void flexible() {
        if (threadPoolExecutor.getCorePoolSize() < availableProcessors * 1.5
                && getMemoryUsage() < 0.8) {
            threadPoolExecutor.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize() + 1);
            threadPoolExecutor.setCorePoolSize(threadPoolExecutor.getCorePoolSize() + 1);
        }
    }

    /**
     * 提供给外部自定义修改线程池核心参数
     */
    protected void fixPoolSize(int corePoolSize, int maxPoolSize) {
        if (maxPoolSize < corePoolSize) {
            throw new RuntimeException("thread pool maxPoolSize must gt corePoolSize");
        }
        check();
        threadPoolExecutor.setCorePoolSize(corePoolSize);
        threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
    }

    /**
     * 检查线程池是否需要扩容
     */
    protected void check() {
        if (threadPoolExecutor == null) {
            throw new RuntimeException(this.getClass() + " not yet initialized,Please add annotation @EnableDiversityThreadPool on Application class");
        }
        if (expansion()) {
            log.info("{} YMThreadPool overstock,thread quantity:{},queue quantity:{}", poolType(),
                    threadPoolExecutor.getActiveCount(), threadPoolExecutor.getQueue().size());
            flexible();
        }
    }

    /**
     * 是否扩容触发条件
     */
    abstract boolean expansion();

    /**
     * 初始化线程池
     */
    abstract void init(Integer poolSize, Integer poolQueueSize, EnableDiversityThreadPool annotation);

    /**
     * 提交任务
     */
    abstract void executeTask(Runnable task);

    /**
     * 线程池类型
     */
    abstract ThreadPoolTaskType poolType();

    /**
     * JVM内存使用率
     */
    private static double getMemoryUsage() {
        return (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Runtime.getRuntime().maxMemory();
    }
}
