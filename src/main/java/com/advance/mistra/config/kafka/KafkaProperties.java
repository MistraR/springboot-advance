package com.advance.mistra.config.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 19:58
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaProperties {

    private String servers;

    private String clientId;

    public static final String TOPIC = "test-topic";

    public static final String GROUP_ID_1 = "test-group1";

    public static final String GROUP_ID_2 = "test-group2";

}
