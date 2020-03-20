package com.advance.mistra.config.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/20 23:58
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "mistra.filter")
public class FilterProperties {

    /**
     * 不需要经过过滤器的请求路径
     */
    private List<String> exclusions;
}
