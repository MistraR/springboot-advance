package com.advance.mistra.controller;

import com.advance.mistra.common.response.annotation.MistraResponse;
import com.advance.mistra.model.ParameterTestModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/12 16:52
 * @Description: 参数映射测试
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@RestController
@RequestMapping("/parameter")
public class ParameterTestController {

    @ApiOperation("时间戳")
    @GetMapping("/timeStamp")
    @MistraResponse
    public ParameterTestModel timeStamp(ParameterTestModel parameterTestModel) {
        log.info("{}", parameterTestModel);
        return parameterTestModel;
    }
}
