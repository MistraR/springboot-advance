package com.advance.mistra.common.threadpool;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/4/15 16:43
 * @ Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DiversityThreadPoolFactory.class)
public @interface EnableDiversityThreadPool {

    /**
     * 初始化IO密集型线程池
     */
    boolean initIOPool() default true;

    /**
     * 初始化CPU密集型线程池
     */
    boolean initCPUPool() default true;

    /**
     * 初始化优先级调度线程池
     */
    boolean initPriorityPool() default false;

    /**
     * 是否回收核心线程
     */
    boolean allowCoreThreadTimeOut() default true;

    /**
     * 线程空闲存活时间，超过则回收 单位:TimeUnit.SECONDS
     */
    long keepAliveTime() default 5;
}
