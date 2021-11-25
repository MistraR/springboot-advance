package com.advance.mistra.plugin.esfreemakerversion.autowired;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:28
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Configuration
@ComponentScan(basePackages = {"com.advance.mistra.plugin.esfreemakerversion"})
@EnableConfigurationProperties(ElasticSearchPoolConfig.class)
public class EsClientAutoConfiguration {
}
