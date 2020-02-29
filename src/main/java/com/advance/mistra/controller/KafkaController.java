package com.advance.mistra.controller;

import com.advance.mistra.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 20:12
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@RestController
@RequestMapping("/kafka")
@Api(value = "Kafka相关接口")
public class KafkaController {

    @ApiOperation(value = "生产者接口", notes = "生产者接口", response = ResponseResult.class)
    @GetMapping(value = "/produce")
    public ResponseResult produce() {
        return new ResponseResult();
    }

    @ApiOperation(value = "消费者接口", notes = "消费者接口", response = ResponseResult.class)
    @GetMapping(value = "/consumer")
    public ResponseResult consumer() {
        return new ResponseResult();
    }
}
