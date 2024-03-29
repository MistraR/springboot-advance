package com.advance.mistra.plugin.esannotationversion.document;

import java.util.Date;

import com.advance.mistra.plugin.esannotationversion.annotation.EsQueryField;
import com.advance.mistra.plugin.esannotationversion.document.dto.ManualTagsDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.SystemTagsDTO;
import com.advance.mistra.plugin.esannotationversion.document.dto.UserBasicDTO;
import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;
import com.advance.mistra.utils.date.DateDeserializer;
import com.advance.mistra.utils.date.DateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * member
 *
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(indexName = "member", type = "type", shards = 3, replicas = 0, refreshInterval = "10s")
@EsQueryField(key = "page", type = EsQueryTypeEnum.IGNORE)
@EsQueryField(key = "size", type = EsQueryTypeEnum.IGNORE)
@EsQueryField(key = "q", type = EsQueryTypeEnum.FULLTEXT)
public class MemberDocument {

    /**
     * userId
     */
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @EsQueryField(type = EsQueryTypeEnum.IN)
    @EsQueryField(type = EsQueryTypeEnum.EQUAL)
    @Field(type = FieldType.Long)
    private Long userId;

    /**
     * 基础信息
     */
    @Field(type = FieldType.Nested)
    private UserBasicDTO basic;

    /**
     * 系统标签
     */
    @Field(type = FieldType.Nested)
    private SystemTagsDTO tags;

    /**
     * 手动标签
     */
    @Field(type = FieldType.Nested)
    private ManualTagsDTO manualTags;

    @Field(type = FieldType.Keyword)
    private String creator;

    @Field(type = FieldType.Keyword)
    private String modifier;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @EsQueryField(type = EsQueryTypeEnum.RANGE)
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    @EsQueryField(type = EsQueryTypeEnum.RANGE)
    private Date modifyTime;
}
