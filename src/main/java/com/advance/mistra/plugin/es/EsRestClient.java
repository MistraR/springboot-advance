package com.advance.mistra.plugin.es;

import com.advance.mistra.plugin.es.pool.ElasticSearchClient;
import com.advance.mistra.plugin.es.pool.ElasticSearchPool;
import com.advance.mistra.plugin.es.pool.ElasticSearchPoolFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 16:40
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
public class EsRestClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchPoolFactory.class);

    public JSONObject doPostByJson(String url, String param) throws IOException {
        return sendRequest(HttpMethod.POST.name(), url, param);
    }

    public JSONObject doPutByJson(String url, String param) throws IOException {
        return sendRequest(HttpMethod.PUT.name(), url, param);
    }

    public JSONObject doGetByJson(String url, String param) throws IOException {
        return sendRequest(HttpMethod.GET.name(), url, param);
    }

    public JSONObject doGet(String url) throws IOException {
        return doGetByJson(url, null);
    }

    public JSONObject doPost(String url) throws IOException {
        return doPostByJson(url, null);
    }

    public JSONObject doDeleteByJson(String url, String param) throws IOException {
        return sendRequest(HttpMethod.DELETE.name(), url, param);
    }

    public JSONObject doDelete(String url) throws IOException {
        return doDeleteByJson(url, null);
    }

    private JSONObject sendRequest(String methodName, String url, String param) throws IOException {
        if (StringUtils.isEmpty(methodName) || StringUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Illegal argument!");
        }
        ElasticSearchClient elasticSearchClient = null;
        try {
            // 获取连接
            elasticSearchClient = ElasticSearchPool.getClient();
            // 构造请求对象
            HttpEntity entity = null;
            if (!StringUtils.isEmpty(param)) {
                entity = new NStringEntity(param, ContentType.APPLICATION_JSON);
            }
            // 执行请求
            Request request = new Request(methodName, url);
            request.setEntity(entity);
            Response response = elasticSearchClient.getRestClient().performRequest(request);
            return RestfulResponseUtil.createResponseResult(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (elasticSearchClient != null) {
                ElasticSearchPool.returnClient(elasticSearchClient);
            }
        }

    }

}
