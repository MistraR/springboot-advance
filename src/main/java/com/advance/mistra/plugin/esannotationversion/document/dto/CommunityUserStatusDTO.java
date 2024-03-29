package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.util.List;

import com.advance.mistra.plugin.esannotationversion.annotation.EsQueryField;
import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommunityUserStatusDTO {

    /**
     * 社群ID
     */
    @Field(type = FieldType.Long)
    @EsQueryField(type = EsQueryTypeEnum.IN, nestedPath = "tags.community")
    private Long communityId;

    /**
     * 社群名
     */
    @Field(type = FieldType.Keyword)
    private String communityName;

    /**
     * 状态
     */
    @Field(type = FieldType.Keyword)
    @EsQueryField(type = EsQueryTypeEnum.IN, nestedPath = "tags.community")
    private String status;

    /**
     * 关联活动code
     */
    @Field(type = FieldType.Keyword)
    private List<String> activityCode;
}
