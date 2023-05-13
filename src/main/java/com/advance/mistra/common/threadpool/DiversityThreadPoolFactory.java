package com.advance.mistra.common.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author rui.wang
 * @ Version: 1.0
 * @ Time: 2023/5/12 19:34
 * @ Description:
 */
@Slf4j
@Configuration
public class DiversityThreadPoolFactory {

    @Value("${executor.poolSize:1}")
    private Integer poolSize;

    @Value("${executor.poolQueueSize:1000}")
    private Integer poolQueueSize;

    @EventListener
    public void listener(ApplicationStartedEvent event) {
        if (poolSize < 1 || poolQueueSize < 1) {
            throw new RuntimeException("poolSize or poolQueueSize must ge 1");
        }
        EnableDiversityThreadPool annotation = AnnotationUtils.getAnnotation(event.getSpringApplication().getMainApplicationClass(),
                EnableDiversityThreadPool.class);
        if (annotation != null) {
            if (annotation.initCPUPool()) {
                DiversityThreadPoolContext.CPU_POOL = new YMCPUThreadPool(poolSize, poolQueueSize, annotation);
            }
            if (annotation.initIOPool()) {
                DiversityThreadPoolContext.IO_POOL = new YMIOThreadPool(poolSize, poolQueueSize, annotation);
            }
            if (annotation.initPriorityPool()) {
                DiversityThreadPoolContext.PRIORITY_POOL = new YMPriorityThreadPool(poolSize, poolQueueSize, annotation);
            }
        }
    }
}
