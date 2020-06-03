package com.advance.mistra.test.commandlinerunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/5/17 21:40
 * @ Description: 定时任务
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
public class ScheduledTask {

    @Autowired
    private GatePorter gatePorter;

    @Scheduled(fixedDelay = 5000)
    public void doSomething() {
        gatePorter.execute();
    }
}
