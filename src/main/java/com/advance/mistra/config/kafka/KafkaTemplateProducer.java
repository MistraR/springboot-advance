package com.advance.mistra.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 21:33
 * @ Description: KafkaTemplate方式发送消息，对比于KafkaProducer
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
@Slf4j
public class KafkaTemplateProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(Object obj) {
        // 发送消息
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaProperties.TOPIC, obj);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                // 发送失败的处理
                log.info(KafkaProperties.TOPIC + " - 生产者 发送消息失败：" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                // 成功的处理
                log.info(KafkaProperties.TOPIC + " - 生产者 发送消息成功：" + stringObjectSendResult.toString());
            }
        });
    }
}
