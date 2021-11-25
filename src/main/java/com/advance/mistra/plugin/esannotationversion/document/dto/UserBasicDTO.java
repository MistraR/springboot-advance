package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.util.Date;
import java.util.List;

import com.advance.mistra.plugin.esannotationversion.annotation.EsQueryField;
import com.advance.mistra.plugin.esannotationversion.enums.EsQueryTypeEnum;
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
 * 用户基本数据
 *
 * @author mistra@future.com
 * @date 2021/11/24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserBasicDTO {

    @Field(type = FieldType.Long)
    private Long accountId;

    /**
     * 昵称
     */
    @Field(type = FieldType.Keyword)
    @EsQueryField(type = EsQueryTypeEnum.FUZZY)
    @EsQueryField(key = "name", type = EsQueryTypeEnum.SHOULD)
    private String nickName;

    /**
     * 微信昵称
     */
    @Field(type = FieldType.Keyword)
    private String wechatNickname;

    /**
     * 脱敏手机号
     */
    @Field(type = FieldType.Keyword)
    private String maskedPhoneNumber;

    /**
     * 顾问邮箱
     */
    @Field(type = FieldType.Keyword)
    @EsQueryField(type = EsQueryTypeEnum.FUZZY)
    @EsQueryField(key = "consultantEmail", type = EsQueryTypeEnum.SHOULD)
    private String consultantEmail;

    /**
     * 会员类型，级别
     */
    @Field(type = FieldType.Keyword)
    private String memberLevel;

    /**
     * 会员期限时间单位 (MONTH,DAY)
     */
    @Field(type = FieldType.Keyword)
    private String memberUnit;

    /**
     * 会员 数量(1个月为1,3个月为3)
     */
    @Field(type = FieldType.Integer)
    private Integer memberQuantity;

    /**
     * 会员剩余天数
     */
    @Field(type = FieldType.Long)
    private Long memberDayLast;

    /**
     * 会员来源
     */
    @Field(type = FieldType.Nested)
    private List<String> memberSource;

    /**
     * 首成会员日
     */
    @Field(type = FieldType.Date)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    @EsQueryField(type = EsQueryTypeEnum.RANGE)
    private Date firstMemberDay;

    /**
     * 会员到期日，计算剩余天数
     */
    @Field(type = FieldType.Date)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    @EsQueryField(type = EsQueryTypeEnum.RANGE)
    private Date memberExpireDay;

    /**
     * 会员注册日期
     */
    @Field(type = FieldType.Date)
    @JsonDeserialize(using = JodaDateTimeDeserializer.class)
    @JsonSerialize(using = JodaDateTimeSerializer.class)
    @EsQueryField(type = EsQueryTypeEnum.RANGE)
    private Date registerTime;

    /**
     * 是否加了微信
     */
    @Field(type = FieldType.Boolean)
    @EsQueryField()
    private Boolean wechatAdded;

    /**
     * 会员微信账号
     */
    @Field(type = FieldType.Keyword)
    private String wechatAccount;

    /**
     * 会员版本
     */
    @Field(type = FieldType.Keyword)
    private String memberType;

    /**
     * 会员头像
     */
    @Field(type = FieldType.Text)
    private String avatarUrl;

    /**
     * 顾问名称
     */
    @Field(type = FieldType.Keyword)
    @EsQueryField(type = EsQueryTypeEnum.FUZZY)
    private String consultantName;

    /**
     * 是否评测
     */
    @Field(type = FieldType.Boolean)
    private Boolean measured;

    /**
     * 是否是主账号
     */
    @Field(type = FieldType.Boolean)
    private Boolean main;

    /**
     * 是否是到家体验会员
     */
    @Field(type = FieldType.Boolean)
    private Boolean homeExperience;

    /**
     * 是否是kol
     */
    @Field(type = FieldType.Boolean)
    private Boolean kol;

    /**
     * kolType
     */
    @Field(type = FieldType.Nested)
    private List<String> kolType;

    /**
     * 会员备注
     */
    @Field(type = FieldType.Text)
    private String remark;
}
