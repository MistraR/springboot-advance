package com.advance.mistra.test.string;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/27 15:57
 * @ Description: String测试类
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
public class StringTest {

    public static void main(String[] args) {
        String string = "1|3,2,1";
        String str = string.substring(string.indexOf("|") + 1);
        log.info(str);
        log.info("{}", Arrays.asList(str.split(",")));
    }
}
