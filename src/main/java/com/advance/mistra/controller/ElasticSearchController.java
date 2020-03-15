package com.advance.mistra.controller;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.common.response.annotation.MistraResponse;
import com.advance.mistra.plugin.es.EsBaseService;
import com.advance.mistra.plugin.es.EsRestClient;
import com.advance.mistra.plugin.es.util.FreemakerUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 17:07
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@RestController
@RequestMapping("/es")
@Api(value = "ElasticSearch相关接口")
public class ElasticSearchController extends EsBaseService {

    @Autowired
    private EsRestClient esRestClient;

    private static String esTemplatePath = "/esInterface/selectbyIdParam.ftl";
    private static String esIndex = "get-together";

    @ApiOperation(value = "get", notes = "get", response = ResponseResult.class)
    @GetMapping(value = "/get")
    @MistraResponse
    public JSONObject get() throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", "2");
        String param = FreemakerUtil.generateEsJson(params, esTemplatePath);
        return super.getEsDataListAndAggs(esRestClient, param, esIndex, "group");
    }
}
