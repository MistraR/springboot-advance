package com.advance.mistra.common.response;

import com.advance.mistra.common.response.annotation.MistraResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:51
 * @Description: 自定义注解，封装返回值处理类。封装controller层的返回值给前端。
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@ControllerAdvice
public class MistraResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(MistraResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (selectedConverterType == StringHttpMessageConverter.class) {
            return JSON.toJSONString(ResponseResult.buildSuccess(body));
        }
        return ResponseResult.buildSuccess(body);
    }
}
