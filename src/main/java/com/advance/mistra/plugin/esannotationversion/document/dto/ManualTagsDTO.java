package com.advance.mistra.plugin.esannotationversion.document.dto;

import java.util.List;

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
public class ManualTagsDTO {

    @Field(type = FieldType.Long)
    private Long city;
    @Field(type = FieldType.Keyword)
    private String gender;
    @Field(type = FieldType.Text)
    private List<String> dietTaste;
    @Field(type = FieldType.Text)
    private List<String> injuryInfo;
    @Field(type = FieldType.Text)
    private List<String> injurySite;
    @Field(type = FieldType.Text)
    private List<String> profession;
    @Field(type = FieldType.Text)
    private List<String> fitnessAims;
    @Field(type = FieldType.Text)
    private List<String> fitnessGoal;
    @Field(type = FieldType.Text)
    private List<String> fitnessItem;
    @Field(type = FieldType.Text)
    private List<String> specialCase;
    @Field(type = FieldType.Text)
    private List<String> livingStatus;
    @Field(type = FieldType.Keyword)
    private String relationship;
    @Field(type = FieldType.Text)
    private List<String> sleepQuality;
    @Field(type = FieldType.Text)
    private List<String> specialStage;
    @Field(type = FieldType.Keyword)
    private String exerciseDaily;
    @Field(type = FieldType.Keyword)
    private String maritalStatus;
    @Field(type = FieldType.Text)
    private List<String> trainingParts;
    @Field(type = FieldType.Text)
    private List<String> courseDuration;
    @Field(type = FieldType.Text)
    private List<String> dietPreference;
    @Field(type = FieldType.Text)
    private List<Integer> preferredCoach;
    @Field(type = FieldType.Boolean)
    private Boolean conversionResult;
    @Field(type = FieldType.Text)
    private List<String> courseDifficulty;
    @Field(type = FieldType.Keyword)
    private String fitnessExperience;
    @Field(type = FieldType.Keyword)
    private String purchaseIntention;
    @Field(type = FieldType.Keyword)
    private String exerciseDaysPerWeek;
    @Field(type = FieldType.Text)
    private List<String> nonConversionReason;
    @Field(type = FieldType.Text)
    private List<String> favoriteCourseCategory;
    @Field(type = FieldType.Text)
    private List<String> notExercisingReason;
    @Field(type = FieldType.Boolean)
    private Boolean haveChildren;
}
