package com.advance.mistra.controller;

import com.advance.mistra.common.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/swagger")
@Api(value = "Swagger相关接口")
public class SwaggerController {

    @ApiOperation(value = "欢迎接口", notes = "欢迎接口", response = ResponseResult.class)
    @GetMapping(value = "/hello")
    public ResponseResult hello(@ApiParam(value = "hello", required = true) @RequestParam String index,
                                @ApiParam(value = "hello", required = true) @RequestParam String index2) {
        return new ResponseResult();
    }

}
