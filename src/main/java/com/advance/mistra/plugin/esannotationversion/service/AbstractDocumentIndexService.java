package com.advance.mistra.plugin.esannotationversion.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import com.advance.mistra.common.exception.BusinessErrorCode;
import com.advance.mistra.common.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@Slf4j
@Component
public abstract class AbstractDocumentIndexService {

    private final ElasticsearchTemplate elasticsearchTemplate;

    private final ObjectMapper objectMapper;

    public AbstractDocumentIndexService(ElasticsearchTemplate elasticsearchTemplate, ObjectMapper objectMapper) {
        this.elasticsearchTemplate = elasticsearchTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 更新索引
     *
     * @param indexName 索引名称
     * @param type      索引类型
     * @param id        ID
     * @param jsonDoc   JSON格式的文档
     * @param refresh   是否刷新索引
     * @return ID
     */
    public String index(String indexName, String type, String id, JsonNode jsonDoc, boolean refresh)
            throws JsonProcessingException {
        log.info("AbstractDocumentIndexService更新索引.indexName:{},type:{},id:{},jsonDoc:{}", indexName, type, id, jsonDoc);
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withIndexName(indexName)
                .withType(type)
                .withId(id)
                .withSource(objectMapper.writeValueAsString(jsonDoc))
                .build();
        try {
            if (elasticsearchTemplate.indexExists(indexName)) {
                String index = elasticsearchTemplate.index(indexQuery);
                if (refresh) {
                    elasticsearchTemplate.refresh(indexName);
                }
                return index;
            }
        } catch (Exception e) {
            log.error("更新索引失败,刷新ES重试", e);
            elasticsearchTemplate.refresh(indexName);
            return elasticsearchTemplate.index(indexQuery);
        }
        throw BusinessException.build(BusinessErrorCode.ES_ERROR);
    }

    /**
     * 更新索引
     *
     * @param indexName 索引名称
     * @param type      索引类型
     * @param id        ID
     * @param jsonDoc   JSON格式的文档
     * @param refresh   是否刷新ES索引
     * @return ID
     */
    public String index(String indexName, String type, String id, Map<String, Object> jsonDoc, boolean refresh) {
        try {
            JsonNode doc = objectMapper.readTree(JSON.toJSONString(jsonDoc));
            return index(indexName, type, id, doc, refresh);
        } catch (Exception e) {
            log.error("更新索引失败", e);
            throw BusinessException.build(BusinessErrorCode.ES_ERROR);
        }
    }

    /**
     * 批量更新索引
     *
     * @param indexName 索引名
     * @param type      索引类型
     * @param idList    需要更新的文档的ID
     * @param doc       需要更新的内容
     */
    public void bulkUpdate(String indexName, String type, List<Object> idList, Map doc) {
        List<UpdateQuery> updateQueryList = idList.stream().filter(Objects::nonNull)
                .map(id -> new UpdateQueryBuilder()
                        .withIndexName(indexName)
                        .withType(type)
                        .withId(id.toString())
                        .withUpdateRequest(new UpdateRequest(indexName, type, id.toString()).doc(doc))
                        .build())
                .collect(Collectors.toList());
        try {
            elasticsearchTemplate.bulkUpdate(updateQueryList);
            elasticsearchTemplate.refresh(indexName);
        } catch (Exception e) {
            log.error("批量更新索引失败", e);
            elasticsearchTemplate.refresh(indexName);
            elasticsearchTemplate.bulkUpdate(updateQueryList);
        }
    }

}
