package com.advance.mistra.controller;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.common.response.annotation.MistraResponse;
import com.advance.mistra.plugin.esfreemakerversion.EsBaseService;
import com.advance.mistra.plugin.esfreemakerversion.EsRestClient;
import com.advance.mistra.plugin.esfreemakerversion.util.FreemakerUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private static String esType = "group";

    @ApiOperation(value = "Es查询", notes = "Es查询", response = ResponseResult.class)
    @GetMapping(value = "/get/{id}")
    @MistraResponse
    public JSONObject get(@PathVariable String id) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        String param = FreemakerUtil.generateEsJson(params, esTemplatePath);
        return super.getEsDataListAndAggs(esRestClient, param, esIndex, esType);
    }
}
