package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.util.Date;

import com.advance.mistra.utils.date.DateDeserializer;
import com.advance.mistra.utils.date.DateSerializer;
import com.advance.mistra.utils.date.JodaDateTimeDeserializer;
import com.advance.mistra.utils.date.JodaDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author liuzhi@fiture.com
 * @date 2021/11/18
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FreezeInfoDTO {

    /**
     * 冻结状态
     */
    @Field(type = FieldType.Boolean)
    private Boolean freezeStatus;

    /**
     * 冻结原因
     */
    @Field(type = FieldType.Keyword)
    private String freezeReason;

    /**
     * 冻结时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date freezeDate;

    /**
     * 解冻时间
     */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonSerialize(using = DateSerializer.class)
    private Date unFreezeDate;

}
