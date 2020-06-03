package com.advance.mistra.test.commandlinerunner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/5/17 22:20
 * @ Description: CommandLineRunner
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Component
public class Test2CommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("Test2CommandLineRunner run success!");
        List<String> list = new ArrayList<>();
        list.add("Test2 - A");
        list.add("Test2 - B");
        list.add("Test2 - C");
        GatePorter.task.addAll(list);
    }
}
