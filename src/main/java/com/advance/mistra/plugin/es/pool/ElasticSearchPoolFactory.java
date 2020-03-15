package com.advance.mistra.plugin.es.pool;

import com.advance.mistra.plugin.es.autowired.ElasticSearchPoolConfig;
import com.advance.mistra.plugin.es.util.RestfulResponseUtil;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.advance.mistra.common.SystemConstans.GET;
import static com.advance.mistra.common.SystemConstans.SLASH;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:41
 * @ Description: Es连接池工厂，管理池对象，支持多个集群同时连接
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
public class ElasticSearchPoolFactory implements KeyedPooledObjectFactory<String, ElasticSearchClient> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchPoolFactory.class);

    @Autowired
    private ElasticSearchPoolConfig elasticSearchPoolConfig;

    /**
     * 获取连接
     *
     * @param s 集群名称
     * @return
     * @throws Exception
     */
    @Override
    public PooledObject<ElasticSearchClient> makeObject(String s) throws Exception {
        List<HttpHost> httpHosts = new ArrayList<>();
        for (ElasticSearchPoolConfig.ElasticSearchClusterConfig elasticSearchClusterConfig : elasticSearchPoolConfig.getPool()) {
            if (s.equals(elasticSearchClusterConfig.getClusterName())) {
                for (ElasticSearchPoolConfig.ElasticSearchClusterConfig.ElasticSearchNodeConfig elasticSearchNodeConfig : elasticSearchClusterConfig.getNodes()) {
                    httpHosts.add(new HttpHost(elasticSearchNodeConfig.getIp(), elasticSearchNodeConfig.getPort(), elasticSearchNodeConfig.getSchema()));
                }
            }
        }
        HttpHost[] httpHosts1 = new HttpHost[httpHosts.size()];
        httpHosts1 = httpHosts.toArray(httpHosts1);
        RestClient restClient = RestClient.builder(httpHosts1).build();
        ElasticSearchClient elasticsearchClient = new ElasticSearchClient(restClient, s);
        return new DefaultPooledObject<>(elasticsearchClient);
    }

    /**
     * 销毁客户端
     *
     * @param s
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void destroyObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {
        ElasticSearchClient elasticSearchClient = pooledObject.getObject();
        if (elasticSearchClient == null) {
            pooledObject = null;
            return;
        }
        if (elasticSearchClient.getRestClient() == null) {
            pooledObject = null;
            return;
        }
        RestClient restClient = elasticSearchClient.getRestClient();
        // 关闭连接
        if (restClient != null) {
            restClient.close();
        }
    }

    /**
     * 验证客户端有效性
     *
     * @param s
     * @param pooledObject
     * @return
     */
    @Override
    public boolean validateObject(String s, PooledObject<ElasticSearchClient> pooledObject) {
        ElasticSearchClient elasticSearchClient = pooledObject.getObject();
        if (elasticSearchClient == null) {
            return false;
        }
        if (elasticSearchClient.getRestClient() == null) {
            return false;
        }
        RestClient restClient = elasticSearchClient.getRestClient();
        try {
            Response response = restClient.performRequest(new Request(GET, SLASH));
            int statusCode = response.getStatusLine().getStatusCode();
            return RestfulResponseUtil.isSuccessfulResponse(statusCode);
        } catch (Exception e) {
            logger.error("Cluster " + s + "validate clent failure!", e);
        }
        return false;
    }

    /**
     * 唤醒客户端
     *
     * @param s
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {

    }

    /**
     * 挂起客户端
     *
     * @param s
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(String s, PooledObject<ElasticSearchClient> pooledObject) throws Exception {

    }
}
