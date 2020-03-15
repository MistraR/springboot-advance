package com.advance.mistra.plugin.es.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;

import java.io.IOException;

import static com.advance.mistra.common.SystemConstans.*;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 16:37
 * @ Description: 测试 http 请求是否成功响应
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class RestfulResponseUtil {

    public static boolean isSuccessfulResponse(int statusCode) {
        return statusCode < HttpStatus.SC_MULTIPLE_CHOICES;
    }

    /**
     * 构造es请求响应体
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static JSONObject createResponseResult(Response response) throws IOException {
        if (response == null) {
            throw new IllegalArgumentException("非法的响应请求体!");
        }
        JSONObject result = new JSONObject();
        result.put(CODE, response.getStatusLine().getStatusCode());
        result.put(MSG, response.getStatusLine().getReasonPhrase());
        result.put(DATA, EntityUtils.toString(response.getEntity()));
        return result;
    }
}
