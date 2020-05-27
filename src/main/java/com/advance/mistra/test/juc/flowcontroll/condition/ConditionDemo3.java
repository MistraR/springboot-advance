package com.advance.mistra.test.juc.flowcontroll.condition;


import cn.hutool.core.thread.NamedThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/15 20:58
 * @ Description: ConcurrentLinkedQueue 加锁的情况下，比ConditionDemo4不加锁慢的多
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class ConditionDemo3 implements Runnable {

    private static int queueSize = 5;
    private static ConcurrentLinkedQueue<String> QUEUE = new ConcurrentLinkedQueue<String>();
    private static Lock lock = new ReentrantLock();
    // 没有满的Condition
    private static Condition notFull = lock.newCondition();
    // 没有空的Condition
    private static Condition notEmpty = lock.newCondition();

    private long startTime;

    public ConditionDemo3(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (QUEUE.size() == 0) {
                    System.out.println("队列空，等待数据");
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String s = QUEUE.poll();
                notFull.signalAll();
                System.out.println("从队列里取走了一个数据 " + s + "，队列剩余" + QUEUE.size() + "个元素");
                Thread.sleep(10);
                if (s.equalsIgnoreCase("字符串999")) {
                    long endTime = System.currentTimeMillis();
                    System.out.println("花费时间：" + (endTime - startTime));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    static void addCache(String string) {
        lock.lock();
        try {
            while (QUEUE.size() == queueSize) {
                System.out.println("队列满，等待有空余");
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            QUEUE.add(string);
            notEmpty.signalAll();
            System.out.println("向队列插入了一个元素，队列剩余空间" + (queueSize - QUEUE.size()));
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int threadCount = 3;
        ExecutorService executorService = new ThreadPoolExecutor(threadCount + 1, threadCount + 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1000), new NamedThreadFactory("测试线程-", false));
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(new ConditionDemo3(start));
        }
        for (int i = 0; i < 1000; i++) {
            ConditionDemo3.addCache("字符串" + i);
        }
    }
}
