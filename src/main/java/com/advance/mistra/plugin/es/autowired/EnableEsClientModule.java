package com.advance.mistra.plugin.es.autowired;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:27
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {java.lang.annotation.ElementType.TYPE})
@Documented
@Import((EsClientAutoConfiguration.class))
public @interface EnableEsClientModule {
}
