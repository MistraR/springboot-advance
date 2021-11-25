package com.advance.mistra.plugin.esfreemakerversion.pool;

import org.elasticsearch.client.RestClient;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:37
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class ElasticSearchClient implements Closeable {

    private RestClient restClient;

    private String cluster;

    public RestClient getRestClient() {
        return restClient;
    }

    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public ElasticSearchClient(RestClient restClient, String cluster) {
        this.restClient = restClient;
        this.cluster = cluster;
    }

    @Override
    public void close() throws IOException {
        ElasticSearchPool.returnClient(this);
    }
}
