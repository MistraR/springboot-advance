package com.advance.mistra.config.filter;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/20 23:21
 * @ Description: 注册过滤器
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Data
@Configuration
public class FilterConfig {

    @Autowired
    private FilterProperties filterProperties;

    @Bean
    public FilterRegistrationBean mistraFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MistraFilter());
        filterRegistrationBean.setOrder(70);
        // 需要过滤的url
        filterRegistrationBean.setUrlPatterns(filterProperties.getExclusions());
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistrationBean;
    }
}
