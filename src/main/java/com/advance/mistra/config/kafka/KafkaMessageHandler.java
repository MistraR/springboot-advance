package com.advance.mistra.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 21:09
 * @ Description: 消息监听消费
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Component
public class KafkaMessageHandler {

    @KafkaListener(topics = KafkaProperties.TOPIC, groupId = KafkaProperties.GROUP_ID_1)
    public void topicTest1(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("》》》》》》》》》》》》》》》》》》》》》topicTest1消费了Topic:{},Message:{}", topic, msg);


            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaProperties.TOPIC, groupId = KafkaProperties.GROUP_ID_2)
    public void topicTest2(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("》》》》》》》》》》》》》》》》》》》》》topicTest2消费了Topic:{},Message:{}", topic, msg);
            ack.acknowledge();
        }
    }

    /**
     * 消费多个topic
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListeners({@KafkaListener(topics = "topic1"), @KafkaListener(topics = "topic2")})
    public void batchListener(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("》》》》》》》》》》》》》》》》》》》》》topicTest1消费了Topic:{},Message:{}", topic, msg);
            ack.acknowledge();
        }
    }
}
