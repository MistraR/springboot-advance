package com.advance.mistra.utils.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 15:13
 * @ Description: Http request util
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class RestfulUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestfulUtil.class);

    private static final String JSON_HEADER = "application/json;charset=UTF-8";
    private static final String FORM_HEADER = "application/x-www-form-urlencoded;charset=UTF-8";

    /**
     * doGet Http Request
     *
     * @param url   Request url
     * @param param Request param : "{ \"id\":\"123456\"}"
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doGetByJson(String url, String param) throws IOException {
        return doHttpByJson(HttpMethod.GET, url, param);
    }

    /**
     * doGet Http Request
     *
     * @param url   Request url
     * @param param Request param : "{name1=value1}"
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doGet(String url, String param) throws IOException {
        String urlNameString = url + (param == null ? "" : "?" + param);
        return doGet(urlNameString);
    }

    /**
     * doGet Http Request
     *
     * @param url Request url
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doGet(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        return getResponse(httpResponse);
    }

    /**
     * doHttpByForm Http Request
     *
     * @param method HttpMethod
     * @param url    Request url
     * @param param  Request param
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doHttpByForm(HttpMethod method, String url, String param) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.create(method.name());
        requestBuilder.setUri(url);
        requestBuilder.setHeader("Content-Type", FORM_HEADER);
        requestBuilder.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(requestBuilder.build());
        return getResponse(httpResponse);
    }

    /**
     * doHttpByJson Http Request
     *
     * @param method HttpMethod
     * @param url    Request url
     * @param param  Request param
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doHttpByJson(HttpMethod method, String url, String param) throws IOException {
        RequestBuilder requestBuilder = RequestBuilder.create(method.name());
        requestBuilder.setUri(url);
        requestBuilder.setHeader("Content-Type", JSON_HEADER);
        requestBuilder.setEntity(new StringEntity(param, Charset.forName("UTF-8")));
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(requestBuilder.build());
        return getResponse(httpResponse);
    }

    /**
     * doPost Http Request
     *
     * @param url Request url
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doPost(String url) throws IOException {
        HttpPost request = new HttpPost(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        return getResponse(httpResponse);
    }

    /**
     * doPostByJson Http Request
     *
     * @param url   Request url
     * @param param Request param
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doPostByJson(String url, String param) throws IOException {
        return doHttpByJson(HttpMethod.POST, url, param);
    }

    /**
     * doDelete Http Request
     *
     * @param url Request url
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doDelete(String url) throws IOException {
        HttpDelete request = new HttpDelete(url);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        return getResponse(httpResponse);
    }

    /**
     * doDeleteByJson Http Request
     *
     * @param url   Request url
     * @param param Request param
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doDeleteByJson(String url, String param) throws IOException {
        return doHttpByJson(HttpMethod.DELETE, url, param);
    }

    /**
     * doPutByJson Http Request
     *
     * @param url   Request url
     * @param param Request param
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject doPutByJson(String url, String param) throws IOException {
        return doHttpByJson(HttpMethod.PUT, url, param);
    }

    /**
     * get result from httpResponse
     *
     * @param httpResponse HttpResponse
     * @return result
     * @throws IOException IOException
     */
    public static JSONObject getResponse(HttpResponse httpResponse) throws IOException {
        JSONObject result = new JSONObject();
        result.put("code", httpResponse.getStatusLine().getStatusCode());
        result.put("message", httpResponse.getStatusLine().getReasonPhrase());
        result.put("data", EntityUtils.toString(httpResponse.getEntity()));
        return result;
    }
}
