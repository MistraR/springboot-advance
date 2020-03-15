package com.advance.mistra.plugin.es.autowired;

import com.advance.mistra.utils.http.RestfulUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.advance.mistra.common.SystemConstans.STRING_SEPARTOR;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 14:32
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Component
@ConfigurationProperties(prefix = "mistra.elasticsearch")
public class ElasticSearchPoolConfig extends GenericKeyedObjectPoolConfig {

    private ElasticSearchClusterConfig[] pool;
    private String esUrl;
    private String defaultCluster;
    private int minIdlePerKey = 10;
    private int maxIdlePerKey = 100;
    private int maxTotalPerKey = 200;
    private int maxTotal = 500;
    private boolean testOnBorrow = false;
    private boolean testwhileIdle = true;
    private boolean testOnReturn = false;
    private boolean testOnCreate = true;
    private long maxWaitMillis = 10000;

    public String getDefaultCluster() {
        return defaultCluster;
    }

    public void setDefaultCluster(String defaultCluster) {
        this.defaultCluster = defaultCluster;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    @Override
    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestwhileIdle() {
        return testwhileIdle;
    }

    public void setTestwhileIdle(boolean testwhileIdle) {
        this.testwhileIdle = testwhileIdle;
    }

    @Override
    public int getMinIdlePerKey() {
        return minIdlePerKey;
    }

    @Override
    public void setMinIdlePerKey(int minIdlePerKey) {
        this.minIdlePerKey = minIdlePerKey;
    }

    @Override
    public int getMaxIdlePerKey() {
        return maxIdlePerKey;
    }

    @Override
    public void setMaxIdlePerKey(int maxIdlePerKey) {
        this.maxIdlePerKey = maxIdlePerKey;
    }

    @Override
    public int getMaxTotalPerKey() {
        return maxTotalPerKey;
    }

    @Override
    public void setMaxTotalPerKey(int maxTotalPerKey) {
        this.maxTotalPerKey = maxTotalPerKey;
    }

    @Override
    public int getMaxTotal() {
        return maxTotal;
    }

    @Override
    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    @Override
    public long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    @Override
    public void setMaxWaitMillis(long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    @Override
    public boolean getTestOnReturn() {
        return testOnReturn;
    }

    @Override
    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public boolean isTestOnCreate() {
        return testOnCreate;
    }

    @Override
    public boolean getTestOnCreate() {
        return testOnCreate;
    }

    @Override
    public void setTestOnCreate(boolean testOnCreate) {
        this.testOnCreate = testOnCreate;
    }

    public String getEsUrl() {
        return esUrl;
    }

    public void setEsUrl(String esUrl) {
        this.esUrl = esUrl;
        if (!StringUtils.isEmpty(this.esUrl)) {
            String[] esUrlArr = esUrl.split(STRING_SEPARTOR);
            validateEsCluster(esUrlArr);
        }
    }

    public ElasticSearchClusterConfig[] getPool() {
        return pool;
    }

    public void setPool(ElasticSearchClusterConfig[] pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        return "ElasticSearchPoolConfig{" +
                "pool=" + Arrays.toString(pool) +
                ", esUrl='" + esUrl + '\'' +
                ", defaultCluster='" + defaultCluster + '\'' +
                ", minIdlePerKey=" + minIdlePerKey +
                ", maxIdlePerKey=" + maxIdlePerKey +
                ", maxTotalPerKey=" + maxTotalPerKey +
                ", maxTotal=" + maxTotal +
                ", testOnBorrow=" + testOnBorrow +
                ", testwhileIdle=" + testwhileIdle +
                ", testOnReturn=" + testOnReturn +
                ", testOnCreate=" + testOnCreate +
                ", maxWaitMillis=" + maxWaitMillis +
                '}';
    }

    private void validateEsCluster(String[] esUrlArr) {
        String cluserName = null;
        List<ElasticSearchClusterConfig.ElasticSearchNodeConfig> nodes = new ArrayList<>();
        for (String url : esUrlArr) {
            try {
                // 解析ip和端口
                String[] arr = url.split("//");
                if (arr.length != 2) {
                    log.error("ElasticSearch url config error.[url={}]", url);
                    continue;
                }
                String ipPortStr = arr[1];
                arr = ipPortStr.split(":");
                if (arr.length != 2) {
                    log.error("ElasticSearch url config error.[url={}]", url);
                    continue;
                }
                String ip = arr[0];
                String port = arr[1];
                // 获取集群信息
                JSONObject node = RestfulUtil.doGet(url).getJSONObject("data");
                cluserName = node.getString("cluster_name");
                String nodeName = node.getString("name");
                String nodeVersion = node.getJSONObject("version").getString("number");
                // 校验版本
                if (StringUtils.isEmpty(this.defaultCluster)) {
                    this.defaultCluster = cluserName;
                } else if (!cluserName.equalsIgnoreCase(this.defaultCluster)) {
                    throw new RuntimeException("ElasticSearch Cluster[" + this.defaultCluster + "] add Node: [" + nodeName + "] failure! The cluster name [" + cluserName + "] of node is not correct!");
                }
                validateNodeVersion(nodes, nodeVersion, nodeName);
                // 封装对象
                ElasticSearchClusterConfig.ElasticSearchNodeConfig nodeConfig = new ElasticSearchClusterConfig.ElasticSearchNodeConfig();
                nodeConfig.setIp(ip);
                nodeConfig.setPort(Integer.parseInt(port));
                nodeConfig.setName(nodeName);
                nodeConfig.setVersion(nodeVersion);
                nodes.add(nodeConfig);
            } catch (Exception e) {
                log.error("ElasticSearch Cluster Connect url: " + url + " failure!");
            }
            if (nodes.size() == 0) {
                throw new RuntimeException("ElasticSearch Cluster[" + this.defaultCluster + "] has not found Node!");
            }
            if (pool == null) {
                pool = new ElasticSearchClusterConfig[1];
            }
            ElasticSearchClusterConfig.ElasticSearchNodeConfig[] nodeArr = new ElasticSearchClusterConfig.ElasticSearchNodeConfig[nodes.size()];
            ElasticSearchClusterConfig tmp = new ElasticSearchClusterConfig();
            tmp.setClusterName(cluserName);
            tmp.setNodes(nodes.toArray(nodeArr));
            pool[0] = tmp;
        }
    }

    private void validateNodeVersion(List<ElasticSearchClusterConfig.ElasticSearchNodeConfig> nodes, String nodeVersion, String nodeName) {
        if (nodes == null || nodes.isEmpty()) {
            return;
        }
        for (ElasticSearchClusterConfig.ElasticSearchNodeConfig node : nodes) {
            if (!node.getVersion().equalsIgnoreCase(nodeVersion)) {
                throw new RuntimeException("ElasticSearch Cluster -v" + node.getVersion() + " add Node: [" + nodeName + "] failure! The version [" + nodeVersion + "] of node is not correct!");
            }
        }
    }

    public static class ElasticSearchClusterConfig {

        private String clusterName;
        private ElasticSearchNodeConfig[] nodes;

        public String getClusterName() {
            return clusterName;
        }

        public void setClusterName(String clusterName) {
            this.clusterName = clusterName;
        }

        public ElasticSearchNodeConfig[] getNodes() {
            return nodes;
        }

        public void setNodes(ElasticSearchNodeConfig[] nodes) {
            this.nodes = nodes;
        }

        public static class ElasticSearchNodeConfig {
            private String name;
            private String ip;
            private Integer port;
            private String schema;
            private String version;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public Integer getPort() {
                return port;
            }

            public void setPort(Integer port) {
                this.port = port;
            }

            public String getSchema() {
                return schema;
            }

            public void setSchema(String schema) {
                this.schema = schema;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }


        }
    }
}
