package com.advance.mistra.plugin.esannotationversion.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * IndexConfigEntity
 *
 * @author mistra@future.com
 * @date 2021/11/25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IndexConfigEntity {

    /**
     * 索引名称
     */
    private String indexName;

    /**
     * 索引类型
     */
    private String type;

    /**
     * 索引文档实体类包路径
     */
    private String documentClassPath;

}
