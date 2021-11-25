package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.advance.mistra.utils.date.JodaDateTimeDeserializer;
import com.advance.mistra.utils.date.JodaDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
public class SystemTagsDTO {

    /**
     * 今天完成课程数量
     */
    @Field(type = FieldType.Long)
    private Long todayCompleteCourseNumber;

    /**
     * 今天完课率
     */
    @Field(type = FieldType.Double)
    private BigDecimal todayCompleteCourseRadio;

    /**
     * 今天有效运动时长
     */
    @Field(type = FieldType.Long)
    private Long todayEffectiveCourseDuration;

    /**
     * 今天开始运动时间
     */
    @Field(type = FieldType.Date)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    private Date todayStartTrainingTime;

    /**
     * 最近7天完课数量
     */
    @Field(type = FieldType.Long)
    private Long lastSevenCompleteCourseNumber;

    /**
     * 最近7天完课率
     */
    @Field(type = FieldType.Double)
    private BigDecimal lastSevenCompleteCourseRadio;

    /**
     * 最近7天有效运动时长
     */
    @Field(type = FieldType.Long)
    private Long lastSevenEffectiveCourseDuration;

    /**
     * 最近30天完课数量
     */
    @Field(type = FieldType.Long)
    private Long lastThirtyCompleteCourseNumber;

    /**
     * 最近30天完课率
     */
    @Field(type = FieldType.Double)
    private BigDecimal lastThirtyCompleteCourseRadio;

    /**
     * 最近30天有效运动时长
     */
    @Field(type = FieldType.Long)
    private Long lastThirtyEffectiveCourseDuration;

    /**
     * 最近完成运动时间
     */
    @Field(type = FieldType.Date)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    private Date lastCompleteCourseTime;

    /**
     * 会员活跃度
     */
    @Field(type = FieldType.Keyword)
    private String memberActive;

    /**
     * 会员活力值等级
     */
    @Field(type = FieldType.Keyword)
    private String vitalityLevel;

    /**
     * 活动来源
     */
    @Field(type = FieldType.Keyword)
    private String activitySource;

    /**
     * 是否是普通会员
     */
    @Field(type = FieldType.Boolean)
    private Boolean commonMember;

    /**
     * 是否是内部员工
     */
    @Field(type = FieldType.Boolean)
    private Boolean employee;

    /**
     * 是否是kol
     */
    @Field(type = FieldType.Boolean)
    private Boolean kol;

    /**
     * 是否是vvip
     */
    @Field(type = FieldType.Boolean)
    private Boolean vvip;

    /**
     * 社群信息
     */
    @Field(type = FieldType.Nested)
    private List<CommunityUserStatusDTO> community;

    /**
     * 活动信息
     */
    @Field(type = FieldType.Nested)
    private List<MemberActivityStatusDTO> activity;

    /**
     * 微信唯一id
     */
    @Field(type = FieldType.Keyword)
    private String wechatUnionId;

    /**
     * 是否删除
     */
    @Field(type = FieldType.Boolean)
    private Boolean delete;

    /**
     * 是否是真实用户
     */
    @Field(type = FieldType.Boolean)
    private Boolean real;

    /**
     * 连续包月
     */
    @Field(type = FieldType.Object)
    private CyclePayDTO cyclePay;

    /**
     * 健身态度
     */
    @Field(type = FieldType.Keyword)
    private String workoutAttitude;

    /**
     * 健身场景
     */
    @Field(type = FieldType.Nested)
    private List<String> senseAttitude;

    /**
     * 生活状态
     */
    @Field(type = FieldType.Keyword)
    private String workSchedule;

    /**
     * 冻结信息
     */
    @Field(type = FieldType.Object)
    private FreezeInfoDTO freezeInfo;
}
