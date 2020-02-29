package com.advance.mistra.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 21:09
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Component
public class KafkaMessageHandler {

    @KafkaListener(topics = {"test-topic"})
    public void handle(String message) {
        log.info("Consumer consume message:(String){}", message);
    }

    @KafkaListener(topics = {"test-topic"})
    public void handle(ConsumerRecord<String, String> record) {
        log.info("Consumer consume message:(ConsumerRecord){}", record);

    }
}
