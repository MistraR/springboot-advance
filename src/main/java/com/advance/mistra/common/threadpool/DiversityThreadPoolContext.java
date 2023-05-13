package com.advance.mistra.common.threadpool;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/5/12 19:03
 * @ Description:
 */
public class DiversityThreadPoolContext {

    /**
     * IO密集型线程池
     */
    protected static YMIOThreadPool IO_POOL = null;

    /**
     * CPU密集型线程池
     */
    protected static YMCPUThreadPool CPU_POOL = null;

    /**
     * 优先级线程池
     */
    protected static YMPriorityThreadPool PRIORITY_POOL = null;

    public static void executeIOTask(Runnable task) {
        IO_POOL.executeTask(task);
    }

    public static void executeCPUTask(Runnable task) {
        CPU_POOL.executeTask(task);
    }

    /**
     * 优先级任务 立即执行
     */
    public static void executePriorityTaskImmediately(Runnable task) {
        PRIORITY_POOL.executeTask(new YMPriorityThreadPool.PriorityTask(task, YMPriorityThreadPool.TaskPriority.HIGH));
    }

    /**
     * 优先级任务 普通顺序提交
     */
    public static void executePriorityTask(Runnable task) {
        PRIORITY_POOL.executeTask(new YMPriorityThreadPool.PriorityTask(task, YMPriorityThreadPool.TaskPriority.LOW));
    }

    /**
     * 修改ThreadPoolTaskType.IO类型线程池核心参数
     */
    public static void fixIOPoolSize(int corePoolSize, int maxPoolSize) {
        IO_POOL.fixPoolSize(corePoolSize, maxPoolSize);
    }

    /**
     * 修改ThreadPoolTaskType.CPU类型线程池核心参数
     */
    public static void fixCPUPoolSize(int corePoolSize, int maxPoolSize) {
        CPU_POOL.fixPoolSize(corePoolSize, maxPoolSize);
    }

    /**
     * 修改ThreadPoolTaskType.PRIORITY类型线程池核心参数
     */
    public static void fixPriorityPoolSize(int corePoolSize, int maxPoolSize) {
        PRIORITY_POOL.fixPoolSize(corePoolSize, maxPoolSize);
    }

}
