package com.advance.mistra.plugin.esannotationversion.service;

import java.util.Map;

import com.advance.mistra.plugin.esannotationversion.config.IndexConfigEntity;
import com.advance.mistra.plugin.esannotationversion.config.IndexConfiguration;
import com.advance.mistra.plugin.esannotationversion.core.FieldDefinition;
import com.advance.mistra.plugin.esannotationversion.core.Key;
import org.springframework.stereotype.Service;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@Service
public class MemberDocumentIndexService extends SearchService {

    private Map<Key, FieldDefinition> keyMappings;

    private Map<String, Map<Key, FieldDefinition>> keyMappingsMap;

    /**
     * 默认排序
     */
    private static final String DEFAULT_SORT = "-baseInfo.createdTime";

    private final IndexConfiguration indexConfiguration;

    public MemberDocumentIndexService(IndexConfiguration indexConfiguration) {
        this.indexConfiguration = indexConfiguration;
    }

    public void index(Map<String, Object> params) {

    }

    public Object search(Map<String, String> params) {
        IndexConfigEntity config = indexConfiguration.getIndexConfigByIndexName(IndexConfiguration.MEMBER_INDEX_NAME);
        return commonSearch(params, config.getIndexName(), config.getType(), DEFAULT_SORT,
                keyMappings, keyMappingsMap);
    }
}
