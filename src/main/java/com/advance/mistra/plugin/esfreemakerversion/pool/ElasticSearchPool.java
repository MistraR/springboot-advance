package com.advance.mistra.plugin.esfreemakerversion.pool;

import com.advance.mistra.plugin.esfreemakerversion.autowired.ElasticSearchPoolConfig;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:38
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Component
public class ElasticSearchPool {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchPool.class);

    private volatile static GenericKeyedObjectPool<String, ElasticSearchClient> elasticSearchPool;

    private static String DEFAULT_CLUSTER = "";

    @Autowired
    private ElasticSearchPool(ElasticSearchPoolFactory elasticSearchPoolFactory, ElasticSearchPoolConfig elasticSearchPoolConfig) {
        logger.info("load es cluster success: " + elasticSearchPoolConfig.toString() + ", current cluster: " + elasticSearchPoolConfig.getDefaultCluster());
        synchronized (this) {
            if (elasticSearchPool == null) {
                synchronized (this) {
                    elasticSearchPool = new GenericKeyedObjectPool<String, ElasticSearchClient>(elasticSearchPoolFactory, elasticSearchPoolConfig);
                }
            }
            DEFAULT_CLUSTER = elasticSearchPoolConfig.getDefaultCluster();
            for (ElasticSearchPoolConfig.ElasticSearchClusterConfig elasticSearchClusterConfig : elasticSearchPoolConfig.getPool()) {
                try {
                    String clusterName = elasticSearchClusterConfig.getClusterName();
                    elasticSearchPool.preparePool(clusterName);
                    if (logger.isDebugEnabled()) {
                        logger.debug("PreparePool es pool " + clusterName + " success.");
                    }
                } catch (Exception e) {
                    logger.error("PreparePool es pool failure,clustername: " + elasticSearchClusterConfig.getClusterName(), e);
                }
            }
        }
    }

    public static ElasticSearchClient getClient() {
        int key = elasticSearchPool.getNumActivePerKey().get(DEFAULT_CLUSTER);
        if (key > elasticSearchPool.getMaxTotalPerKey() * 0.5) {
            logger.info("GetClient,current active client:" + key);
        } else {
            logger.debug("GetClient,current active client:" + key);
        }
        return getClient(DEFAULT_CLUSTER);
    }

    private static ElasticSearchClient getClient(String clusterName) {
        try {
            ElasticSearchClient elasticSearchClient = elasticSearchPool.borrowObject(clusterName);
            return elasticSearchClient;
        } catch (Exception e) {
            logger.error("Get client failure,msg:{}", e.getMessage(), e);
        }
        return null;
    }

    public static void returnClient(ElasticSearchClient elasticSearchClient) {
        try {
            elasticSearchPool.returnObject(elasticSearchClient.getCluster(), elasticSearchClient);
            logger.debug("Return client,current active client:" + elasticSearchPool.getNumActivePerKey().get(DEFAULT_CLUSTER));
        } catch (Exception e) {
            logger.error("Return client failure,msg:{}", e.getMessage(), e);
        }
    }
}
