package com.advance.mistra.plugin.esannotationversion.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 描述
 *
 * @author qiaoyao@fiture.com
 * @date 2021/10/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CyclePayDTO {

    /**
     * 自动续费状态
     */
    @Field(type = FieldType.Keyword)
    private String status;

}
