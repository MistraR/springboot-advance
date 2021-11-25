package com.advance.mistra.plugin.esannotationversion.core;

import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldDefinition {

    /**
     * 查询参数
     */
    private String key;

    /**
     * 查询类型
     */
    private EsQueryTypeEnum queryType;

    /**
     * 查询参数对应的文档中的字段
     */
    private String queryField;

    /**
     * from后缀
     */
    private String fromSuffix;

    /**
     * to后缀
     */
    private String toSuffix;

    /**
     * 分隔符
     */
    private String separator;

    /**
     * 嵌套查询的路径
     */
    private String nestedPath;
}
