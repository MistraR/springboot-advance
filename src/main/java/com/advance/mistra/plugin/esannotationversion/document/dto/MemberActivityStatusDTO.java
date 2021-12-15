package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.util.Date;

import com.advance.mistra.plugin.esannotationversion.annotation.EsQueryField;
import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2021/11/1
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberActivityStatusDTO {

    /**
     * 活动code
     */
    @Field(type = FieldType.Keyword)
    @EsQueryField(type = EsQueryTypeEnum.IN, nestedPath = "tags.activity")
    private String activityCode;

    /**
     * 状态
     */
    @Field(type = FieldType.Keyword)
    private String status;

    /**
     * 报名时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date signUpDate;

}
