package com.advance.mistra.common.response.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:50
 * @Description: 自定义注解，封装返回值
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Retention(RUNTIME)
@Target({ METHOD })
@Documented
public @interface MistraResponse {
}
