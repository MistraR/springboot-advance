package com.advance.mistra.tuning;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2023/2/26
 */
public class MyPoolSizeCalculator extends PoolSizeCalculator {

    public static void main(String[] args) {
        MyPoolSizeCalculator calculator = new MyPoolSizeCalculator();
        // 第一个参数：CPU目标利用率 第二个参数 blockingQueue占用的内存大小 byte
        calculator.calculateBoundaries(new BigDecimal(1.0), new BigDecimal(100000));
    }

    protected long getCurrentThreadCPUTime() {
        // 当前线程占用的总时间
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    /**
     * 用这个方法来计算合理参数
     */
    protected Runnable creatTask() {
        return new AsynchronousTask();
    }

    protected BlockingQueue createWorkQueue() {
        return new LinkedBlockingQueue<>();
    }

    /**
     * 加入这是你的线程池的任务
     */
    class AsynchronousTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
