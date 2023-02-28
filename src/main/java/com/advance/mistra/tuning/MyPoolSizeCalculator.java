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

    /**
     * 运行结果
     * Target queue memory usage (bytes): 100000 期望blockingQueue最大占用的内存大小
     * createTask() produced com.advance.mistra.tuning.MyPoolSizeCalculator$AsynchronousTask which took 40 bytes in a queue
     * Formula: 100000 / 40
     * * Recommended queue capacity (bytes): 2500 // 推荐队列容量大小
     * Number of CPU: 16 // 当前机器CPU核心数
     * Target utilization: 1 // 期望CPU利用率
     * Elapsed time (nanos): 3000000000
     * Compute time (nanos): 3015625000
     * Wait time (nanos): -15625000
     * Formula: 16 * 1 * (1 + -15625000 / 3015625000) // 计算公式
     * * Optimal thread count: 16 // 推荐最佳线程数
     *
     * @param args
     */
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
//            System.out.println(Thread.currentThread().getName());
        }
    }
}
