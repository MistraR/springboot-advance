package com.advance.mistra.plugin.esannotationversion.config;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * IndexConfiguration
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@Data
@Component
@ConfigurationProperties("index-entity")
public class IndexConfiguration {

    /**
     * 会员标签 索引名称
     */
    public static final String MEMBER_INDEX_NAME = "member";

    /**
     * 创建索引的文档配置
     */
    private List<IndexConfigEntity> entities;

    /**
     * 根据文档编码获取配置信息
     *
     * @param indexName 文档编码
     * @return 配置
     */
    public IndexConfigEntity getIndexConfigByIndexName(String indexName) {
        for (IndexConfigEntity config : entities) {
            if (config.getIndexName().equals(indexName)) {
                return config;
            }
        }
        return null;
    }

}
