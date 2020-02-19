package com.advance.mistra.solution.producerconsumer;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/2 16:54
 * @Description: 生产者消费者模型，采用1个生产者，多个消费者的形式
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Component
public class ProducerConsumerTest {

    /**
     * 待处理任务队列
     */
    public static ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<String>();
    /**
     * 消费者线程个数
     */
    private static int threadCount = 5;
    /**
     * 线程池
     * LinkedBlockingQueue阻塞队列，默认为Integer.MAX_VALUE  -  无界队列
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(threadCount + 1, threadCount + 1,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1000), new NameTreadFactory());

    private static volatile AtomicInteger stopProduce = new AtomicInteger(0);

    @PostConstruct
    private void init() {
        executorService.submit(new Producer());
        for (int i = 1; i <= threadCount; i++) {
//            executorService.submit(new Consumer());
        }
    }

    /**
     * 生产者
     */
    static class Producer implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                try {
                    // 只生产15个
                    if (stopProduce.intValue() < 15) {
                        queue.add("队列元素" + System.currentTimeMillis());
                        Thread.sleep(1000);
                        stopProduce.incrementAndGet();
                    }
                } catch (Exception e) {
                    log.info("线程发生异常！---------------------------------------------------------------------");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 消费者
     */
    static class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    if (!queue.isEmpty()) {
                        String str = queue.poll();
                        if (str != null && !str.isEmpty()) {
                            log.info("线程:{},消费:{}", Thread.currentThread().getName(), str);
                        }
                    } else {
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                    log.info("线程发生异常！---------------------------------------------------------------------");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 自定义的线程名称类
     * 定义线程池时使用
     */
    static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger threadNum = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "my-thread-" + threadNum.getAndIncrement());
            log.info("{} has been created", thread.getName());
            return thread;
        }
    }

}
