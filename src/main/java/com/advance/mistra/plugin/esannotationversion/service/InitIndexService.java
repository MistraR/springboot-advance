package com.advance.mistra.plugin.esannotationversion.service;

import java.util.List;
import java.util.stream.Collectors;

import com.advance.mistra.plugin.esannotationversion.config.IndexConfigEntity;
import com.advance.mistra.plugin.esannotationversion.config.IndexConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@Service
@Slf4j
public class InitIndexService {

    private final IndexConfiguration indexEntity;

    private final ElasticsearchTemplate elasticsearchTemplate;

    public InitIndexService(IndexConfiguration indexEntity, ElasticsearchTemplate elasticsearchTemplate) {
        this.indexEntity = indexEntity;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    /**
     * 初始化索引
     *
     * @return true:创建成功，其他：失败
     */
    public Object initIndex() {
        List<String> documentPath = indexEntity.getEntities().stream().map(IndexConfigEntity::getDocumentClassPath).collect(Collectors.toList());
        try {
            for (String path : documentPath) {
                try {
                    Class clazz = Class.forName(path);
                    if (!elasticsearchTemplate.indexExists(clazz.newInstance().getClass())) {
                        elasticsearchTemplate.createIndex(clazz.newInstance().getClass());
                        log.info("创建索引SUCCESS，clazz:{}", clazz);
                    }
                    elasticsearchTemplate.putMapping(clazz.newInstance().getClass());
                    log.info("创建Mapping映射SUCCESS，clazz:{}", clazz);
                } catch (ClassNotFoundException e) {
                    log.error("未加载到索引文件", e);
                    throw new ClassNotFoundException("未加载到索引文件,ClassPath:" + path);
                }
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return true;
    }

}
