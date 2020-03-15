package com.advance.mistra.controller;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.plugin.kafka.KafkaProducerCreator;
import com.advance.mistra.plugin.kafka.KafkaProperties;
import com.advance.mistra.plugin.kafka.KafkaTemplateProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 20:12
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
@Api(value = "Kafka相关接口")
public class KafkaController {

    @Autowired
    private KafkaProducerCreator kafkaProducerCreator;

    @Autowired
    private KafkaTemplateProducer kafkaTemplateProducer;

    private Producer<String, String> kafkaProducer;

    @PostConstruct
    private void init() {
        kafkaProducer = kafkaProducerCreator.createProducer();
    }

    @ApiOperation(value = "消息生产者接口", notes = "消息生产者接口", response = ResponseResult.class)
    @GetMapping(value = "/produce")
    public ResponseResult produce(@ApiParam(value = "hello", required = true) @RequestParam String index) {
        ProducerRecord<String, String> record = new ProducerRecord<>(KafkaProperties.TOPIC, "hello, Kafka! " + index);
        try {
            // 发送消息
            RecordMetadata metadata = kafkaProducer.send(record).get();
            log.info("Record sent to partition {} with offset {}", metadata.partition(), metadata.offset());
        } catch (ExecutionException | InterruptedException e) {
            log.debug("Error in sending record,{}", e.getMessage());
        }
        // 需要时，关闭kafkaProducer
        //kafkaProducer.close();
        return new ResponseResult(true);
    }

    @ApiOperation(value = "KafkaTemplate消息生产者接口", notes = "KafkaTemplate消息生产者接口", response = ResponseResult.class)
    @GetMapping(value = "/KafkaTemplateProduce")
    public ResponseResult kafkaTemplateProduce(@ApiParam(value = "hello", required = true) @RequestParam String index) {
        // 发送消息
        kafkaTemplateProducer.send("KafkaTemplat hello, Kafka! " + index);
        return new ResponseResult(true);
    }

}
