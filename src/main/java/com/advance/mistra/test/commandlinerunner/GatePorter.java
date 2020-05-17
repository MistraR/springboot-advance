package com.advance.mistra.test.commandlinerunner;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/5/17 21:28
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
public class GatePorter {

    public static final List<String> task = new ArrayList<>();

    public void execute() {
        task.forEach(s -> System.out.println(s));
    }
}
