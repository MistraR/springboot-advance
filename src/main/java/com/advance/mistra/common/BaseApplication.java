package com.advance.mistra.common;

import com.advance.mistra.common.web.MistraDispatcherServlet;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 20:03
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class BaseApplication {

    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        //注解扫描上下文
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        //通过构造函数指定dispatcherServlet的上下文
        MistraDispatcherServlet servlet = new MistraDispatcherServlet(applicationContext);
        //用ServletRegistrationBean包装servlet
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/*");
        registrationBean.setLoadOnStartup(1);
        //指定name，如果不指定默认为dispatcherServlet
        //registrationBean.setName("rest");
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(80));
        factory.setMaxRequestSize(DataSize.ofMegabytes(80));
        registrationBean.setMultipartConfig(factory.createMultipartConfig());
        return registrationBean;
    }
}
