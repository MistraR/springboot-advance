package com.advance.mistra.config.api;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.common.response.annotation.MistraResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/2/29 10:46
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@RestController
@RequestMapping("/knife4j")
@Api(value = "Knife4j测试接口分组")
public class Knife4jTestGroupController {

    @ApiOperation(value = "欢迎接口", notes = "欢迎接口", response = ResponseResult.class)
    @GetMapping(value = "/hello")
    @MistraResponse
    public void hello(@ApiParam(value = "hello", required = true) @RequestParam String index,
                      @ApiParam(value = "hello", required = true) @RequestParam String index2) {
    }

}
