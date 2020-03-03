package com.advance.mistra.config.api;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:30
 * @Description: Swagger文档基础版地址http://localhost:8080/swagger-ui.html，Swagger文档增强版Knife4j地址:http://localhost:8080/doc.html
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    private static final String BASE_PACKAGE = "com.advance.mistra.controller";

    private static final String BASE_PACKAGE_2 = "com.advance.mistra.config.api";

    /**
     * 是否开启swagger
     */
    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("默认接口")
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_2))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean(value = "groupRestApi")
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("分组接口")
                .enable(enableSwagger)
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("RoronoaZoro丶")
                .description("定制化接口文档")
                .termsOfServiceUrl("www.mistra.wang")
                .contact(new Contact("WangRui", "www.mistra.wang", "wrmistra@gmail.com"))
                .version("1.0")
                .build();
    }
}