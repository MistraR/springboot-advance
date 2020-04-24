package com.advance.mistra.test.juc.flowcontroll.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/6 16:30
 * @ Description: 多等一：多个线程等待某一个线程完成工作之后继续执行。譬如开车的时候，路上等红灯。有4个车道，大家都在等待绿灯亮起，大家一起出发。
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class CountDownLatchDemo2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch begin = new CountDownLatch(1);
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            final int no = i + 1;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    System.out.println("No." + no + "在等红灯，等待绿灯亮起");
                    try {
                        begin.await();
                        System.out.println("No." + no + "开始踩油门加速了");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            service.submit(runnable);
        }
        Thread.sleep(5000);
        System.out.println("红灯亮起，大家可以开车了！");
        begin.countDown();
    }

}
