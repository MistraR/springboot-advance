package com.advance.mistra.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 10:40
 * @ Description: Swagger增强版API文档
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    private static final String BASE_PACKAGE = "com.advance.mistra.controller";

    /**
     * 是否开启swagger
     */
    @Value("${swagger.enable}")
    private boolean enableSwagger;

}
