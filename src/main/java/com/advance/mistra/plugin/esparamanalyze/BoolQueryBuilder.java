//package com.fiture.seiya.service.impl;
//
//import com.alibaba.fastjson.JSONObject;
//import com.fiture.foundation.commons.enums.OrderEnum;
//import com.fiture.foundation.commons.page.Page;
//import com.fiture.seiya.config.SeiyaConfig;
//import com.fiture.seiya.enumeration.*;
//import com.fiture.seiya.pojo.dto.MemberTagsDTO;
//import com.fiture.seiya.pojo.entity.MemberEntity;
//import com.fiture.seiya.pojo.entity.MemberTag;
//import com.fiture.seiya.pojo.param.*;
//import com.fiture.seiya.service.MemberSearchService;
//import com.fiture.seiya.service.TagService;
//import com.google.common.collect.ImmutableSet;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.BooleanUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.time.DateFormatUtils;
//import org.apache.lucene.search.join.ScoreMode;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.ExistsQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.RangeQueryBuilder;
//import org.elasticsearch.index.query.TermsQueryBuilder;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.NestedSortBuilder;
//import org.elasticsearch.search.sort.SortBuilders;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//import static com.fiture.seiya.enumeration.ManualTagFieldEnum.*;
//import static com.fiture.seiya.enumeration.TagFieldEnum.*;
//
///**
// * 会员查询service
// *
// * @author mistra@future.com
// * @date //
// */
//@Slf4j
//@Service(value = "memberSearchServiceEs")
//public class BoolQueryBuilder implements MemberSearchService {
//
//    private final TagService memberTagService;
//    private final SeiyaConfig seiyaConfig;
//    private final MemberCommonService memberCommonService;
//    private final RestHighLevelClient restHighLevelClient;
//
//    /**
//     * 嵌套类型标签field
//     */
//    private static final Set<String> NESTED_TAG_FIELDS =
//            ImmutableSet.of(ACTIVITY.getField(), COMMUNITY_ID.getField());
//
//
//    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
//    private static final String WILDCARD = "*";
//    private static final String STATUS = ".status";
//    private static final String BASIC_NESTED_PATH = "basic.";
//    private static final String TAGS_NESTED_PATH = "tags.";
//    private static final String MANUAL_TAGS_NESTED_PATH = "manual_tags.";
//
//    private static final String SORT_MISSING_FIRST = "_first";
//
//    public BoolQueryBuilder(TagService memberTagService, SeiyaConfig
//            seiyaConfig,
//
//                            MemberCommonService memberCommonService, RestHighLevelClient
//                                    restHighLevelClient) {
//
//        this.seiyaConfig = seiyaConfig;
//        this.memberTagService = memberTagService;
//        this.memberCommonService = memberCommonService;
//        this.restHighLevelClient = restHighLevelClient;
//    }
//
//    private Page<MemberTagsDTO> createMemberTagsPage(Integer pageNumber, Integer
//            pageSize) {
//
//        return Page.<MemberTagsDTO>builder().current(pageNumber).total().size(pageSize)
//                .records(Collections.emptyList()).build();
//    }
//
//    public Page<MemberTagsDTO> searchFromEsDynamic(MemberDynamicSearchParam param) {
//        Map<String, MemberTag> tagMetaMap = memberTagService.listAllTagsMap();
//        this.validDynamicParam(param.getRuleContent(), tagMetaMap);
//        return executeQuery(createMemberTagsPage(param.getPageNumber(),
//                        param.getPageSize()),
//
//                splicingEsDynamicParam(param, tagMetaMap), Collections.emptyList());
//    }
//
//    private void validDynamicParam(RuleContentParam param, Map<String, MemberTag>
//            tagMetaMap) {
//
//        if (Objects.isNull(param) || CollectionUtils.isEmpty(param.getRules())) {
//            return;
//        }
//        for (RuleContentParam rule : param.getRules()) {
//            try {
//                RuleTypeEnum.valueOf(rule.getType());
//            } catch (Exception e) {
//                throw new IllegalArgumentException(String.format("规则类型输入值不正确:
//                        [ % s]", rule.getType()));
//
//            }
//            if (RuleTypeEnum.RULE_PROFILE.name().equals(rule.getType())) {
//                if (!tagMetaMap.containsKey(rule.getField())) {
//                    throw new IllegalArgumentException(String.format("field输入值不正确:
//                            [ % s]", rule.getType()));
//
//                }
//                FunctionEnum functionEnum;
//                try {
//                    functionEnum = FunctionEnum.valueOf(rule.getFunction());
//                } catch (Exception e) {
//                    throw new IllegalArgumentException(String.format("操作符输入值不正确:
//                            [ % s]", rule.getType()));
//
//                }
//                switch (functionEnum) {
//                    case EQ:
//                    case IN:
//                    case NE:
//                    case NOT_IN:
//                    case GT:
//                    case LT:
//                    case GTE:
//                    case LTE:
//                        if (CollectionUtils.isEmpty(rule.getFiledValues())) {
//                            throw new IllegalArgumentException(
//                                    String.format("filedValues不能为空，操作符:[%s]",
//                                            functionEnum.name()));
//
//                        }
//                        break;
//                    case BETWEEN:
//                        if (rule.getFiledValues().size() !=) {
//                            throw new IllegalArgumentException(
//                                    String.format("BETWEEN操作符的参数有误:[%s]",
//                                            rule.getFiledValues()));
//
//                        }
//                        break;
//                    default:
//                }
//                if (TagFieldEnum.ACCOUNT_ID.getField().equals(rule.getField())
//                        && CollectionUtils.isNotEmpty(rule.getFiledValues())) {
//                    for (Object accountId : rule.getFiledValues()) {
//                        if (!StringUtils.isNumeric(accountId.toString())) {
//                            throw new IllegalArgumentException(String.format("用户ID输入
//                                    格式不正确:[%s]", accountId));
//
//                        }
//                        try {
//                            Long.parseLong(accountId.toString());
//                        } catch (NumberFormatException e) {
//                            throw new IllegalArgumentException(String.format("用户ID输入
//                                    格式不正确:[%s]", accountId));
//
//                        }
//                    }
//                }
//            } else {
//                try {
//                    RuleRelationEnum.valueOf(rule.getRelation());
//                } catch (Exception e) {
//                    throw new IllegalArgumentException(String.format("逻辑运算符输入值不
//                            正确:[%s]", rule.getRelation()));
//
//                }
//            }
//            validDynamicParam(rule, tagMetaMap);
//        }
//    }
//
//    private SearchSourceBuilder splicingEsDynamicParam(MemberDynamicSearchParam param,
//                                                       Map<String, MemberTag> tagMetaMap) {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.from(param.getPageNumber() <= ?  :(param.getPageNumber()
//                -) * param.getPageSize());
//
//        searchSourceBuilder.size(param.getPageSize());
//        searchSourceBuilder.trackTotalHits(true);
//
//        searchSourceBuilder.sort(SortBuilders.fieldSort("user_id").order(SortOrder.DESC));
//
//
//        searchSourceBuilder.postFilter(this.splicingEsDynamicParam(param.getRuleContent(),
//                tagMetaMap));
//
//        return searchSourceBuilder;
//    }
//
//    private org.elasticsearch.index.query.BoolQueryBuilder splicingEsDynamicParam(RuleContentParam ruleContentParam,
//                                                                                  Map<String, MemberTag> tagMetaMap) {
//        org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        if (RuleTypeEnum.RULE_PROFILE.name().equals(ruleContentParam.getType())) {
//            String path =
//                    splicingMemberTagPath(tagMetaMap.get(ruleContentParam.getField()));
//
//            FunctionEnum functionEnum =
//                    FunctionEnum.valueOf(ruleContentParam.getFunction());
//
//            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery(path);
//            TermsQueryBuilder termsQuery = QueryBuilders.termsQuery(path,
//                    ruleContentParam.getFiledValues());
//
//            ExistsQueryBuilder existsQuery = QueryBuilders.existsQuery(path);
//            org.elasticsearch.index.query.BoolQueryBuilder nestedQuery = QueryBuilders.boolQuery();
//            List<Object> filedValues = ruleContentParam.getFiledValues();
//            if (NESTED_TAG_FIELDS.contains(ruleContentParam.getField())) {
//                switch (functionEnum) {
//                    case EQ:
//                    case IN:
//                        nestedQuery.filter(termsQuery);
//                        break;
//                    case NE:
//                    case NOT_IN:
//                        nestedQuery.mustNot(termsQuery);
//                        break;
//                    case EXISTS:
//                        nestedQuery.filter(existsQuery);
//                        break;
//                    case NOT_EXISTS:
//                        nestedQuery.mustNot(existsQuery);
//                        break;
//                    case GT:
//                        rangeQuery.gt(filedValues.get());
//                        nestedQuery.filter(rangeQuery);
//                        break;
//                    case LT:
//                        rangeQuery.lt(filedValues.get());
//                        nestedQuery.filter(rangeQuery);
//                        break;
//                    case GTE:
//                        rangeQuery.gte(filedValues.get());
//                        nestedQuery.filter(rangeQuery);
//                        break;
//                    case LTE:
//                        rangeQuery.lte(filedValues.get());
//                        nestedQuery.filter(rangeQuery);
//                        break;
//                    case BETWEEN:
//                        rangeQuery.gt(filedValues.get());
//                        rangeQuery.lt(filedValues.get());
//                        nestedQuery.filter(rangeQuery);
//                        break;
//                    default:
//                }
//                boolQueryBuilder.filter(QueryBuilders.nestedQuery(
//                        tagMetaMap.get(ruleContentParam.getField()).getMysqlExpression(),
//                        nestedQuery, ScoreMode.None));
//
//            } else {
//                switch (functionEnum) {
//                    case EQ:
//                    case IN:
//                        boolQueryBuilder.must(termsQuery);
//                        break;
//                    case NE:
//                    case NOT_IN:
//                        boolQueryBuilder.mustNot(termsQuery);
//                        break;
//                    case EXISTS:
//                        boolQueryBuilder.must(existsQuery);
//                        break;
//                    case NOT_EXISTS:
//                        boolQueryBuilder.mustNot(existsQuery);
//                        break;
//                    case GT:
//                        rangeQuery.gt(filedValues.get());
//                        boolQueryBuilder.filter(rangeQuery);
//                        break;
//                    case LT:
//                        rangeQuery.lt(filedValues.get());
//                        boolQueryBuilder.filter(rangeQuery);
//                        break;
//                    case GTE:
//                        rangeQuery.gte(filedValues.get());
//                        boolQueryBuilder.filter(rangeQuery);
//                        break;
//                    case LTE:
//                        rangeQuery.lte(filedValues.get());
//                        boolQueryBuilder.filter(rangeQuery);
//                        break;
//                    case BETWEEN:
//                        rangeQuery.gt(filedValues.get());
//                        rangeQuery.lt(filedValues.get());
//                        boolQueryBuilder.filter(rangeQuery);
//                        break;
//                    default:
//                }
//            }
//        } else {
//            if (RuleRelationEnum.AND.name().equals(ruleContentParam.getRelation())) {
//                for (RuleContentParam term : ruleContentParam.getRules()) {
//                    boolQueryBuilder.must(splicingEsDynamicParam(term, tagMetaMap));
//                }
//            } else {
//                org.elasticsearch.index.query.BoolQueryBuilder shouldQueryBuilder = QueryBuilders.boolQuery();
//                for (RuleContentParam term : ruleContentParam.getRules()) {
//                    shouldQueryBuilder.should(splicingEsDynamicParam(term, tagMetaMap));
//                }
//                boolQueryBuilder.filter(shouldQueryBuilder);
//            }
//        }
//        return boolQueryBuilder;
//    }
//
//    private Page<MemberTagsDTO> executeQuery(Page<MemberTagsDTO> memberTagsPage,
//                                             SearchSourceBuilder searchSourceBuilder, List<String> returnFields) {
//        try {
//            SearchResponse response = restHighLevelClient.search(
//                    new SearchRequest(new String[]{seiyaConfig.getMemberIndexName()},
//                            searchSourceBuilder),
//
//                    RequestOptions.DEFAULT);
//            memberTagsPage.setTotal(response.getHits().getTotalHits().value);
//            return this.convertMemberTagsPage(memberTagsPage,
//                    convertSourceToMemberEsEntity(response.getHits(), MemberEntity.class),
//                    returnFields);
//
//        } catch (IOException e) {
//            log.error("Seiya search member tags from es failed,message:[{}]",
//                    e.getMessage(), e);
//
//            return memberTagsPage;
//        }
//    }
//
//    /**
//     * 通过条件查询用户标签List 通过微信id搜索会员标签
//     *
//     * @param param MemberSearchParam
//     * @return Page<MemberTagsDTO>
//     */
//    @Override
//    public Page<MemberTagsDTO> search(MemberSearchParam param) {
//        Page<MemberTagsDTO> memberTagsPage = createMemberTagsPage(param.getPageNumber(),
//                param.getPageSize());
//
//        if (!memberCommonService.parseMemberSearchParam(param)) {
//            return memberTagsPage;
//        }
//        return executeQuery(memberTagsPage, splicingEsParam(param),
//                param.getReturnFields());
//
//    }
//
//    private Page<MemberTagsDTO> convertMemberTagsPage(Page<MemberTagsDTO>
//                                                              memberTagsPage,
//
//                                                      List<MemberEntity> memberEsEntities, List<String> returnFields) {
//        if (CollectionUtils.isNotEmpty(memberEsEntities)) {
//            Map<String, MemberTag> tagMetaMap = memberTagService.listAllTagsMap();
//            memberTagsPage.setRecords(memberEsEntities.stream()
//                    .map(e -> memberCommonService.memberEntityConvertMemberTags(e,
//                            returnFields, tagMetaMap))
//
//                    .collect(Collectors.toList()));
//        }
//        return memberTagsPage;
//    }
//
//    /**
//     * 拼接ES查询参数
//     *
//     * @param param MemberSearchParam
//     * @return SearchSourceBuilder
//     */
//    private SearchSourceBuilder splicingEsParam(MemberSearchParam param) {
//        SearchSourceBuilder builder =
//                this.createSearchQueryBuilder(param.getPageNumber(), param.getPageSize(),
//
//                        param.getOrderField(), param.getOrder(), param);
//        org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//
//        if (CollectionUtils.isNotEmpty(param.getAccountIds())) {
//            boolQueryBuilder.filter(QueryBuilders.termsQuery("user_id",
//                    param.getAccountIds()));
//
//        }
//        splicingBasicParam(boolQueryBuilder, param);
//        splicingTagsParam(boolQueryBuilder, param);
//        builder.postFilter(boolQueryBuilder);
//        return builder;
//    }
//
//    private SearchSourceBuilder createSearchQueryBuilder(Integer pageNumber, Integer
//            pageSize,
//
//                                                         MemberListSortEnum orderField, OrderEnum order) {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.from(pageNumber <= ?  :(pageNumber -) * pageSize);
//        searchSourceBuilder.size(pageSize);
//        searchSourceBuilder.trackTotalHits(true);
//        if (Objects.nonNull(orderField) && Objects.nonNull(order)) {
//            searchSourceBuilder.sort(
//                    SortBuilders.fieldSort(orderField.getEsColumn()).order(order.isAsc() ?
//                            SortOrder.ASC : SortOrder.DESC));
//
//        } else {
//
//            searchSourceBuilder.sort(SortBuilders.fieldSort("user_id").order(SortOrder.DESC));
//
//        }
//        return searchSourceBuilder;
//    }
//
//    private SearchSourceBuilder createSearchQueryBuilder(Integer pageNumber, Integer
//            pageSize,
//
//                                                         MemberListSortEnum orderField, OrderEnum order, MemberSearchParam param) {
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.from(pageNumber <= ?  :(pageNumber -) * pageSize);
//        searchSourceBuilder.size(pageSize);
//        searchSourceBuilder.trackTotalHits(true);
//        if (Objects.nonNull(orderField) && Objects.nonNull(order)) {
//            if (MemberListSortEnum.signUpDate.name().equals(orderField.name())) {
//                NestedSortBuilder nestedSortBuilder = new
//                        NestedSortBuilder(orderField.getNestedPath());
//
//                nestedSortBuilder.setFilter(splicingActivityParam(param));
//                if (order.isAsc()) {
//
//                    searchSourceBuilder.sort(SortBuilders.fieldSort(orderField.getEsColumn())
//
//
//                            .missing(SORT_MISSING_FIRST).order(SortOrder.ASC).setNestedSort(nestedSortBuilder));
//
//                } else {
//
//                    searchSourceBuilder.sort(SortBuilders.fieldSort(orderField.getEsColumn()).order(SortOrde
//                                    r.DESC)
//
//                            .setNestedSort(nestedSortBuilder));
//                }
//            } else {
//                if (order.isAsc()) {
//
//                    searchSourceBuilder.sort(SortBuilders.fieldSort(orderField.getEsColumn())
//
//                            .missing(SORT_MISSING_FIRST).order(SortOrder.ASC));
//                } else {
//
//                    searchSourceBuilder.sort(SortBuilders.fieldSort(orderField.getEsColumn()).order(SortOrde
//                            r.DESC));
//
//                }
//            }
//        } else {
//
//            searchSourceBuilder.sort(SortBuilders.fieldSort("user_id").order(SortOrder.DESC));
//
//        }
//        return searchSourceBuilder;
//    }
//
//    /**
//     * 拼接basic参数
//     *
//     * @param boolQueryBuilder BoolQueryBuilder
//     * @param param            MemberSearchParam
//     */
//    private void splicingBasicParam(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                    MemberSearchParam param) {
//
//        org.elasticsearch.index.query.BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        splicingBasicTermQuery(queryBuilder, CONSULTANT_EMAIL,
//                param.getConsultantEmail(), true);
//
//        splicingBasicTermQuery(queryBuilder, MAIN, param.getMainAccount());
//        splicingBasicTermQuery(queryBuilder, NICK_NAME, param.getNickName(), true);
//        splicingMemberInfo(queryBuilder, param.getMemberInfo());
//        splicingWechatInfo(queryBuilder, param.getWechatInfo());
//        if (StringUtils.isNotBlank(param.getNickNameWildcard())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderNickname = QueryBuilders.boolQuery();
//
//            queryBuilderNickname.filter(QueryBuilders.wildcardQuery(splicingBasicPath(NICK_NAME),
//
//                    StringUtils.wrapIfMissing(param.getNickNameWildcard(),
//                            WILDCARD)).caseInsensitive(true));
//
//            queryBuilder.filter(queryBuilderNickname);
//        }
//        if (Objects.nonNull(param.getUserScope())) {
//            if (UserScopeEnum.MEMBER.name().equals(param.getUserScope())) {
//
//                queryBuilder.mustNot(QueryBuilders.termQuery(splicingBasicPath(MEMBER_TYPE), ""));
//
//
//                queryBuilder.must(QueryBuilders.existsQuery(splicingBasicPath(MEMBER_TYPE)));
//
//            } else if (UserScopeEnum.NON_MEMBER.name().equals(param.getUserScope())) {
//                org.elasticsearch.index.query.BoolQueryBuilder queryBuilderWechatAdd = QueryBuilders.boolQuery();
//                queryBuilderWechatAdd.should(
//
//                        QueryBuilders.boolQuery().must(QueryBuilders.termQuery(splicingBasicPath(MEMBER_TYPE),
//                                "")));
//
//                queryBuilderWechatAdd.should(
//
//                        QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingBasicPath(MEMBER_TYP
//                                E))));
//
//                queryBuilderWechatAdd.minimumShouldMatch();
//                queryBuilder.filter(queryBuilderWechatAdd);
//            }
//        }
//
//        if (Objects.nonNull(param.getWechatAdded())) {
//            if (param.getWechatAdded()) {
//
//                queryBuilder.filter(QueryBuilders.termQuery(splicingBasicPath(WECHAT_ADDED),
//                        param.getWechatAdded()));
//
//            } else {
//                org.elasticsearch.index.query.BoolQueryBuilder queryBuilderWechatAdd = QueryBuilders.boolQuery();
//                queryBuilderWechatAdd
//                        .should(QueryBuilders.termQuery(splicingBasicPath(WECHAT_ADDED),
//                                param.getWechatAdded()));
//
//                queryBuilderWechatAdd.should(
//
//                        QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingBasicPath(WECHAT_ADD
//                                ED))));
//
//                queryBuilderWechatAdd.minimumShouldMatch();
//                queryBuilder.filter(queryBuilderWechatAdd);
//            }
//        }
//
//        setRangeQueryBuilder(queryBuilder, splicingBasicPath(MEMBER_EXPIRE_DAY),
//                param.getMemberExpireDayStart(),
//
//                param.getMemberExpireDayEnd());
//
//        setRangeQueryBuilder(queryBuilder, splicingBasicPath(BIRTHDAY),
//                param.getBirthdayStart(),
//
//                param.getBirthdayEnd());
//
//        if (Objects.nonNull(param.getConsultant())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderConsultant = QueryBuilders.boolQuery();
//
//            queryBuilderConsultant.should(QueryBuilders.wildcardQuery(splicingBasicPath(CONSULTANT_E
//                            MAIL),
//
//                    StringUtils.wrapIfMissing(param.getConsultant(),
//                            WILDCARD)).caseInsensitive(true));
//
//
//            queryBuilderConsultant.should(QueryBuilders.wildcardQuery(splicingBasicPath(CONSULTANT_N
//                            AME),
//
//                    StringUtils.wrapIfMissing(param.getConsultant(),
//                            WILDCARD)).caseInsensitive(true));
//
//            queryBuilderConsultant.minimumShouldMatch();
//            queryBuilder.filter(queryBuilderConsultant);
//        }
//
//        setRangeQueryBuilder(queryBuilder, splicingBasicPath(FIRST_MEMBER_DAY),
//                param.getFirstMemberDayStart(),
//
//                param.getFirstMemberDayEnd());
//
//        splicingBasicTermQuery(queryBuilder, GENDER, param.getGender());
//        splicingBasicWildcardQuery(queryBuilder, MEMBER_SOURCE,
//                param.getRedemptionCodeTag(), true);
//
//
//        if (queryBuilder.hasClauses()) {
//            boolQueryBuilder.filter(queryBuilder);
//        }
//    }
//
//    private String splicingBasicPath(TagFieldEnum tag) {
//        return BASIC_NESTED_PATH + tag.getField();
//    }
//
//    private void splicingBasicTermQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder, TagFieldEnum
//            tagFieldEnum, Object param) {
//
//        this.splicingBasicTermQuery(boolQueryBuilder, tagFieldEnum, param, false);
//    }
//
//    private void splicingBasicTermQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder, TagFieldEnum
//            tagFieldEnum, Object param,
//
//                                        boolean caseInsensitive) {
//        if (Objects.nonNull(param)) {
//            boolQueryBuilder.filter(
//                    QueryBuilders.termQuery(splicingBasicPath(tagFieldEnum),
//                            param).caseInsensitive(caseInsensitive));
//
//        }
//    }
//
//    private void splicingTagsTermQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder, TagFieldEnum
//            tagFieldEnum, Object param) {
//
//        this.splicingTagsTermQuery(boolQueryBuilder, tagFieldEnum, param, false);
//    }
//
//    private void splicingTagsTermQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder, TagFieldEnum
//            tagFieldEnum, Object param,
//
//                                       boolean caseInsensitive) {
//        if (Objects.nonNull(param)) {
//            boolQueryBuilder.filter(
//                    QueryBuilders.termQuery(splicingTagsPath(tagFieldEnum),
//                            param).caseInsensitive(caseInsensitive));
//
//        }
//    }
//
//    private void splicingBasicWildcardQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                            TagFieldEnum tagFieldEnum, String param,
//
//                                            boolean caseInsensitive) {
//        if (StringUtils.isNotBlank(param)) {
//            boolQueryBuilder.filter(
//                    QueryBuilders.wildcardQuery(splicingBasicPath(tagFieldEnum),
//                                    StringUtils.wrapIfMissing(param, WILDCARD))
//
//                            .caseInsensitive(caseInsensitive));
//        }
//    }
//
//    private void splicingManualTagsTermsQuery(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                              ManualTagFieldEnum tagFieldEnum,
//
//                                              List<?> param) {
//        if (CollectionUtils.isNotEmpty(param)) {
//
//            boolQueryBuilder.filter(QueryBuilders.termsQuery(splicingManualTagsPath(tagFieldEnum),
//                    param));
//
//        }
//    }
//
//    /**
//     * 拼接tags参数
//     *
//     * @param boolQueryBuilder BoolQueryBuilder
//     * @param param            MemberSearchParam
//     */
//    private void splicingTagsParam(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder, MemberSearchParam
//            param) {
//
//        org.elasticsearch.index.query.BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        splicingTagsTermQuery(queryBuilder, REAL, param.getReal());
//        splicingTagsTermQuery(queryBuilder, MEMBER_ACTIVE, param.getMemberActive());
//        splicingTagsTermQuery(queryBuilder, COMMON_MEMBER, param.getCommonMember());
//        splicingTagsTermQuery(queryBuilder, EMPLOYEE, param.getEmployee());
//        splicingTagsTermQuery(queryBuilder, KOL, param.getKol());
//        splicingTagsTermQuery(queryBuilder, VVIP, param.getVvip());
//        splicingTagsTermQuery(queryBuilder, ACTIVITY_SOURCE, param.getActivitySource());
//        splicingTagsTermQuery(queryBuilder, VITALITY_LEVEL, param.getVitalityLevel());
//        splicingTagsTermQuery(queryBuilder, OPERATE_WEEK, param.getOperateWeek());
//        splicingTagsTermQuery(queryBuilder, FIRST_MEMBER_VALIDITY,
//                param.getFirstMemberValidity());
//
//        if (CollectionUtils.isNotEmpty(param.getWechatUnionIds())) {
//
//            queryBuilder.filter(QueryBuilders.termsQuery(splicingTagsPath(WECHAT_UNION_ID),
//                    param.getWechatUnionIds()));
//
//        }
//
//        setRangeQueryBuilder(queryBuilder, splicingTagsPath(LAST_COMPLETE_COURSE_TIME),
//                param.getLastTrainingDayStart(),
//
//                null);
//
//        setRangeEndIncludeNull(queryBuilder,
//                splicingTagsPath(LAST_COMPLETE_COURSE_TIME),
//
//                param.getLastTrainingDayEnd());
//
//        if (Objects.nonNull(param.getWeChatBind())) {
//            if (param.getWeChatBind()) {
//
//                queryBuilder.filter(QueryBuilders.existsQuery(splicingTagsPath(WECHAT_UNION_ID)));
//
//            } else {
//
//                queryBuilder.mustNot(QueryBuilders.existsQuery(splicingTagsPath(WECHAT_UNION_ID)));
//
//            }
//        }
//
//        splicingCyclePayParam(queryBuilder, param);
//
//        if (Objects.nonNull(param.getFreezeStatus())) {
//            queryBuilder.filter(
//                    QueryBuilders.termQuery(splicingTagsPath(FREEZE_INFO) + ".freezeStatus",
//                            param.getFreezeStatus()));
//
//        }
//
//        splicingFirstRenewParam(queryBuilder, param);
//
//        org.elasticsearch.index.query.BoolQueryBuilder nestedQueryBuilder = splicingActivityParam(param);
//        if (nestedQueryBuilder != null) {
//            queryBuilder
//                    .filter(QueryBuilders.nestedQuery(splicingTagsPath(ACTIVITY),
//                            nestedQueryBuilder, ScoreMode.None));
//
//        }
//
//        splicingCommunityParam(queryBuilder, param);
//
//        setRangeQueryBuilder(queryBuilder, splicingTagsPath(FAMILY_LAST_COMPLETE_TIME),
//                param.getFamilyCompleteCourseTimeStart(), null);
//
//        setRangeEndIncludeNull(queryBuilder,
//                splicingTagsPath(FAMILY_LAST_COMPLETE_TIME),
//
//                param.getFamilyCompleteCourseTimeEnd());
//
//        if (CollectionUtils.isNotEmpty(param.getWorkoutAims())) {
//            queryBuilder.filter(QueryBuilders.termsQuery(splicingTagsPath(WORKOUT_AIM),
//                    param.getWorkoutAims()));
//
//        }
//        if (BooleanUtils.isTrue(param.getSearchByNickname())) {
//            queryBuilder.filter(QueryBuilders.termsQuery(
//                    splicingTagsPath(PRIVACY_SETTING) + "." +
//                            UserSettingEnum.SEARCH_BY_NICKNAME.getField(),
//
//                    param.getSearchByNickname()));
//        }
//        if (BooleanUtils.isTrue(param.getSearchByPhoneNumber())) {
//            queryBuilder.filter(QueryBuilders.termsQuery(
//                    splicingTagsPath(PRIVACY_SETTING) + "." +
//                            UserSettingEnum.SEARCH_BY_PHONE_NUMBER.getField(),
//
//                    param.getSearchByPhoneNumber()));
//        }
//
//        if (queryBuilder.hasClauses()) {
//            boolQueryBuilder.filter(queryBuilder);
//        }
//    }
//
//    private void splicingCyclePayParam(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder, MemberSearchParam
//            param) {
//
//        if (StringUtils.isNotBlank(param.getCyclePayStatus())) {
//            // cyclePy.status=SIGNED为已签约
//            if (CyclePayReqEnum.SIGNED.name().equals(param.getCyclePayStatus())) {
//                org.elasticsearch.index.query.BoolQueryBuilder queryBuilderSigned = QueryBuilders.boolQuery();
//                queryBuilderSigned
//                        .filter(QueryBuilders.termQuery(splicingTagsPath(CYCLE_PAY) +
//                                STATUS, param.getCyclePayStatus()));
//
//                queryBuilder.filter(queryBuilderSigned);
//            } else {
//                // cyclePay.status=TERMINATED或cyclePay.status=CANCELLED或cyclePay为{}的
//                都视为未签约
//
//                org.elasticsearch.index.query.BoolQueryBuilder queryBuilderNotSigned = QueryBuilders.boolQuery();
//                List<String> cyclePayStatusList =
//                        List.of(CyclePayStatusEnum.TERMINATED.name(),
//                                CyclePayStatusEnum.CANCELLED.name());
//
//                queryBuilderNotSigned
//                        .should(QueryBuilders.termsQuery(splicingTagsPath(CYCLE_PAY) +
//                                STATUS, cyclePayStatusList));
//
//                queryBuilderNotSigned
//
//                        .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingTagsPath(CYC
//                                LE_PAY))));
//
//                queryBuilderNotSigned.minimumShouldMatch();
//                queryBuilder.filter(queryBuilderNotSigned);
//            }
//        }
//    }
//
//    private void splicingFirstRenewParam(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder,
//                                         MemberSearchParam param) {
//
//        if (Objects.nonNull(param.getFirstRenew())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderFirstRenew = QueryBuilders.boolQuery();
//            if (param.getFirstRenew()) {
//                queryBuilderFirstRenew.filter(
//                        QueryBuilders.termQuery(splicingTagsPath(RENEW_INFO) +
//                                ".renewStatus", param.getFirstRenew()));
//
//            } else {
//                queryBuilderFirstRenew.should(
//                        QueryBuilders.termQuery(splicingTagsPath(RENEW_INFO) +
//                                ".renewStatus", param.getFirstRenew()));
//
//                queryBuilderFirstRenew
//
//                        .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingTagsPath(REN
//                                EW_INFO))));
//
//                queryBuilderFirstRenew.minimumShouldMatch();
//            }
//            queryBuilder.filter(queryBuilderFirstRenew);
//        }
//    }
//
//    private void splicingCommunityParam(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder,
//                                        MemberSearchParam param) {
//
//        if (Objects.nonNull(param.getCommunityName()) ||
//                Objects.nonNull(param.getCommunityId())
//
//                || Objects.nonNull(param.getAddGroupStatus())
//                || CollectionUtils.isNotEmpty(param.getAddGroupStatusList())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderNested = QueryBuilders.boolQuery();
//            if (Objects.nonNull(param.getCommunityName())) {
//
//                queryBuilderNested.filter(QueryBuilders.wildcardQuery(splicingTagsPath(COMMUNITY) +
//                                ".communityName",
//
//                        StringUtils.wrapIfMissing(param.getCommunityName(), WILDCARD)));
//            }
//            if (Objects.nonNull(param.getCommunityId())) {
//                queryBuilderNested.filter(
//                        QueryBuilders.termQuery(splicingTagsPath(COMMUNITY) +
//                                ".communityId", param.getCommunityId()));
//
//            }
//            if (Objects.nonNull(param.getAddGroupStatus())) {
//                queryBuilderNested
//                        .filter(QueryBuilders.termQuery(splicingTagsPath(COMMUNITY) +
//                                STATUS, param.getAddGroupStatus()));
//
//            }
//            if (CollectionUtils.isNotEmpty(param.getAddGroupStatusList())) {
//                queryBuilderNested.filter(
//                        QueryBuilders.termsQuery(splicingTagsPath(COMMUNITY) + STATUS,
//                                param.getAddGroupStatusList()));
//
//            }
//            queryBuilder
//                    .filter(QueryBuilders.nestedQuery(splicingTagsPath(COMMUNITY),
//                            queryBuilderNested, ScoreMode.None));
//
//        }
//    }
//
//    private org.elasticsearch.index.query.BoolQueryBuilder splicingActivityParam(MemberSearchParam param) {
//        if (Objects.nonNull(param.getActivityCode()) ||
//                Objects.nonNull(param.getActivityStatus())) {
//
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderNested = QueryBuilders.boolQuery();
//            if (Objects.nonNull(param.getActivityCode())) {
//                queryBuilderNested.filter(
//                        QueryBuilders.termQuery(splicingTagsPath(ACTIVITY) +
//                                        ".activityCode", param.getActivityCode())
//
//                                .caseInsensitive(true));
//            }
//            if (Objects.nonNull(param.getActivityStatus())) {
//                queryBuilderNested
//                        .filter(QueryBuilders.termQuery(splicingTagsPath(ACTIVITY) + STATUS,
//                                param.getActivityStatus()));
//
//            }
//            return queryBuilderNested;
//        }
//        return null;
//    }
//
//    private String splicingMemberTagPath(MemberTag tag) {
//        return tag.getMysqlExpression() + "." + tag.getField();
//    }
//
//    private String splicingTagsPath(TagFieldEnum tag) {
//        return TAGS_NESTED_PATH + tag.getField();
//    }
//
//    private String splicingManualTagsPath(TagFieldEnum tag) {
//        return MANUAL_TAGS_NESTED_PATH + tag.getField();
//    }
//
//    private String splicingManualTagsPath(ManualTagFieldEnum tag) {
//        return MANUAL_TAGS_NESTED_PATH + tag.getField();
//    }
//
//    /**
//     * 批量排课 从ES查询会员标签
//     *
//     * @param param CalendarMemberSearchParam
//     * @return IPage<MemberEntity>
//     */
//    public Page<MemberTagsDTO> batchScheduleSearchMember(CalendarMemberSearchParam
//                                                                 param) {
//
//        Page<MemberTagsDTO> memberTagsPage = Page.
//                <MemberTagsDTO>builder().current(param.getPageNumber()).total()
//
//                .size(param.getPageSize()).records(Collections.emptyList()).build();
//        try {
//            SearchResponse response = restHighLevelClient.search(
//                    new SearchRequest(new String[]{seiyaConfig.getMemberIndexName()},
//                            splicingEsParam(param)),
//
//                    RequestOptions.DEFAULT);
//            memberTagsPage.setTotal(response.getHits().getTotalHits().value);
//            return this.convertMemberTagsPage(memberTagsPage,
//                    convertSourceToMemberEsEntity(response.getHits(), MemberEntity.class),
//                    null);
//
//        } catch (IOException e) {
//            log.error("Seiya search member tags from es failed,message:[{}]",
//                    e.getMessage(), e);
//
//            return memberTagsPage;
//        }
//    }
//
//    private <T> List<T> convertSourceToMemberEsEntity(SearchHits hits, Class<T> clazz) {
//        List<T> memberEsEntities = new ArrayList<>(hits.getHits().length);
//        for (SearchHit next : hits) {
//            if (Objects.nonNull(next)) {
//                memberEsEntities.add(JSONObject.parseObject(next.getSourceAsString(),
//                        clazz));
//
//            }
//        }
//        return memberEsEntities;
//    }
//
//    /**
//     * 批量排课 拼接ES查询参数
//     *
//     * @param param MemberSearchParam
//     * @return SearchQuery
//     */
//    private SearchSourceBuilder splicingEsParam(CalendarMemberSearchParam param) {
//        SearchSourceBuilder searchQueryBuilder =
//                this.createSearchQueryBuilder(param.getPageNumber(),
//
//                        param.getPageSize(), param.getOrderField(), param.getOrder());
//        org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        if (StringUtils.isNotBlank(param.getAccountId())) {
//            boolQueryBuilder.filter(QueryBuilders.termQuery("user_id",
//                    param.getAccountId()));
//
//        }
//        if (Objects.nonNull(param.getGender())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderGender =
//                    QueryBuilders.boolQuery().minimumShouldMatch()
//
//                            .should(QueryBuilders.termQuery(splicingManualTagsPath(GENDER),
//                                    param.getGender()));
//
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderTagsGender = QueryBuilders.boolQuery();
//
//            queryBuilderTagsGender.mustNot(QueryBuilders.existsQuery(splicingManualTagsPath(GENDER))
//            );
//
//
//            queryBuilderTagsGender.mustNot(QueryBuilders.termQuery(splicingManualTagsPath(GENDER),
//                    ""));
//
//
//            queryBuilderTagsGender.filter(QueryBuilders.termQuery(splicingBasicPath(GENDER),
//                    param.getGender()));
//
//            queryBuilderGender.should(queryBuilderTagsGender);
//            boolQueryBuilder.must(queryBuilderGender);
//        }
//        if (CollectionUtils.isNotEmpty(param.getExcludeInjuryInfo())) {
//            org.elasticsearch.index.query.BoolQueryBuilder query = QueryBuilders.boolQuery().minimumShouldMatch();
//            query.should(QueryBuilders.boolQuery()
//                    .mustNot(QueryBuilders.existsQuery(splicingManualTagsPath(INJURY_INFO)))
//                    .must(QueryBuilders.boolQuery()
//
//                            .should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingTagsPath(INJ
//                                    URY_INFO))))
//
//                            .should(QueryBuilders.termQuery(splicingTagsPath(INJURY_INFO),
//                                    "")).minimumShouldMatch())
//
//                    .minimumShouldMatch());
//
//            query.should(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(splicingManualTags
//                            Path(INJURY_INFO)))
//
//                    .mustNot(QueryBuilders.termsQuery(splicingManualTagsPath(INJURY_INFO),
//                            param.getExcludeInjuryInfo())));
//
//            query.should(
//
//                    QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingManualTagsPath(INJUR
//                                    Y_INFO)))
//
//                            .must(QueryBuilders.existsQuery(splicingTagsPath(INJURY_INFO)))
//                            .mustNot(QueryBuilders.termsQuery(splicingTagsPath(INJURY_INFO),
//                                    param.getExcludeInjuryInfo())));
//
//            boolQueryBuilder.must(query);
//        }
//        if (CollectionUtils.isNotEmpty(param.getExcludeInjurySite())) {
//            org.elasticsearch.index.query.BoolQueryBuilder query = QueryBuilders.boolQuery().minimumShouldMatch();
//            query.should(
//
//                    QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingManualTagsPath(INJUR
//                                    Y_SITE)))
//
//                            .mustNot(QueryBuilders.existsQuery(splicingTagsPath(INJURY_SITE))));
//
//            query.should(QueryBuilders.boolQuery().must(QueryBuilders.existsQuery(splicingManualTags
//                            Path(INJURY_SITE)))
//
//                    .mustNot(QueryBuilders.termsQuery(splicingManualTagsPath(INJURY_SITE),
//                            param.getExcludeInjurySite())));
//
//            query.should(
//
//                    QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(splicingManualTagsPath(INJUR
//                                    Y_SITE)))
//
//                            .must(QueryBuilders.existsQuery(splicingTagsPath(INJURY_SITE)))
//                            .mustNot(QueryBuilders.termsQuery(splicingTagsPath(INJURY_SITE),
//                                    param.getExcludeInjurySite())));
//
//            boolQueryBuilder.must(query);
//        }
//        splicingBasicParam(boolQueryBuilder, param);
//        splicingTagsParam(boolQueryBuilder, param);
//        splicingManualTagsParam(boolQueryBuilder, param);
//        searchQueryBuilder.postFilter(boolQueryBuilder);
//        return searchQueryBuilder;
//    }
//
//    /**
//     * 拼接basic参数
//     *
//     * @param boolQueryBuilder BoolQueryBuilder
//     * @param param            MemberSearchParam
//     */
//    private void splicingBasicParam(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                    CalendarMemberSearchParam param) {
//
//        org.elasticsearch.index.query.BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        splicingMemberInfo(queryBuilder, param.getMemberInfo());
//        splicingBasicTermQuery(queryBuilder, CONSULTANT_EMAIL,
//                param.getConsultantEmail(), true);
//
//        splicingBasicTermQuery(queryBuilder, MAIN, param.getMainAccount());
//        queryBuilder.mustNot(QueryBuilders.termQuery(splicingBasicPath(MEMBER_TYPE),
//                ""));
//
//        queryBuilder.must(QueryBuilders.existsQuery(splicingBasicPath(MEMBER_TYPE)));
//        setRangeQueryBuilder(queryBuilder, splicingBasicPath(MEMBER_EXPIRE_DAY), new
//                Date(), null);
//
//
//        if (queryBuilder.hasClauses()) {
//            boolQueryBuilder.filter(queryBuilder);
//        }
//    }
//
//    private void splicingMemberInfo(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder, String memberInfo) {
//        if (Objects.nonNull(memberInfo)) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderMemberInfo = QueryBuilders.boolQuery();
//            queryBuilderMemberInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(WECHAT_ACCOUNT),
//                            StringUtils.wrapIfMissing(memberInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderMemberInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(WECHAT_NICK_NAME),
//                            StringUtils.wrapIfMissing(memberInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderMemberInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(NICK_NAME),
//                            StringUtils.wrapIfMissing(memberInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderMemberInfo.minimumShouldMatch();
//            queryBuilder.filter(queryBuilderMemberInfo);
//        }
//    }
//
//    private void splicingWechatInfo(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder, String wechatInfo) {
//        if (StringUtils.isNotBlank(wechatInfo)) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderWechatInfo = QueryBuilders.boolQuery();
//            queryBuilderWechatInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(WECHAT_ACCOUNT),
//                            StringUtils.wrapIfMissing(wechatInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderWechatInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(WECHAT_NICK_NAME),
//                            StringUtils.wrapIfMissing(wechatInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderWechatInfo.should(QueryBuilders
//                    .wildcardQuery(splicingBasicPath(WECHAT_REMARK),
//                            StringUtils.wrapIfMissing(wechatInfo, WILDCARD))
//
//                    .caseInsensitive(true));
//            queryBuilderWechatInfo.minimumShouldMatch();
//            queryBuilder.filter(queryBuilderWechatInfo);
//        }
//    }
//
//    /**
//     * 拼接tags参数
//     *
//     * @param boolQueryBuilder BoolQueryBuilder
//     * @param param            MemberSearchParam
//     */
//    private void splicingTagsParam(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                   CalendarMemberSearchParam param) {
//
//        org.elasticsearch.index.query.BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        splicingTagsTermQuery(queryBuilder, MEMBER_ACTIVE, param.getMemberActive());
//        if (Objects.nonNull(param.getCommunityId())) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderNested = QueryBuilders.boolQuery();
//            if (Objects.nonNull(param.getCommunityId())) {
//                queryBuilderNested.filter(
//                        QueryBuilders.termQuery(splicingTagsPath(COMMUNITY) +
//                                ".communityId", param.getCommunityId()));
//
//            }
//            queryBuilder
//                    .filter(QueryBuilders.nestedQuery(splicingTagsPath(COMMUNITY),
//                            queryBuilderNested, ScoreMode.None));
//
//        }
//        if (queryBuilder.hasClauses()) {
//            boolQueryBuilder.filter(queryBuilder);
//        }
//    }
//
//    /**
//     * 拼接manualTags参数
//     *
//     * @param boolQueryBuilder BoolQueryBuilder
//     * @param param            MemberSearchParam
//     */
//    private void splicingManualTagsParam(org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder,
//                                         CalendarMemberSearchParam param) {
//
//        org.elasticsearch.index.query.BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        splicingManualTagsTermsQuery(queryBuilder, FITNESS_AIMS,
//                param.getFitnessAims());
//
//        splicingManualTagsTermsQuery(queryBuilder, FAVORITE_COURSE_CATEGORY,
//                param.getFavoriteCourseCategory());
//
//        splicingManualTagsTermsQuery(queryBuilder, PREFERRED_COACH,
//                param.getPreferredCoach());
//
//        splicingManualTagsTermsQuery(queryBuilder, SPECIAL_STAGE,
//                param.getSpecialStage());
//
//        splicingManualTagsTermsQuery(queryBuilder, FITNESS_EXPERIENCE,
//                param.getFitnessExperience());
//
//        splicingManualTagsTermsQuery(queryBuilder, EXERCISE_DAYS_PER_WEEK,
//                param.getExerciseDaysPerWeek());
//
//        splicingManualTagsTermsQuery(queryBuilder, EXERCISE_DAILY,
//                param.getExerciseDaily());
//
//        splicingManualTagsTermsQuery(queryBuilder, TRAINING_PARTS,
//                param.getTrainingParts());
//
//        splicingManualTagsTermsQuery(queryBuilder, COURSE_DIFFICULTY,
//                param.getCourseDifficulty());
//
//        splicingManualTagsTermsQuery(queryBuilder, COURSE_DURATION,
//                param.getCourseDuration());
//
//        if (CollectionUtils.isNotEmpty(param.getSpecialStageExclude())) {
//            queryBuilder.mustNot(
//                    QueryBuilders.termsQuery(splicingManualTagsPath(SPECIAL_STAGE),
//                            param.getSpecialStageExclude()));
//
//        }
//        if (queryBuilder.hasClauses()) {
//            boolQueryBuilder.filter(queryBuilder);
//        }
//    }
//
//    public Page<MemberTagsDTO> pageMembersByTag(PageMemberByTagParam param) {
//        Page<MemberTagsDTO> memberTagsPage = Page.
//                <MemberTagsDTO>builder().current(param.getPageNumber()).total()
//
//                .size(param.getPageSize()).records(Collections.emptyList()).build();
//        MemberTag memberTag = memberTagService.getMemberTagByFiled(param.getField());
//        if (Objects.isNull(memberTag) ||
//                StringUtils.isBlank(memberTag.getMysqlExpression())) {
//
//            return memberTagsPage;
//        }
//        try {
//            SearchResponse response = restHighLevelClient.search(
//                    new SearchRequest(new String[]{seiyaConfig.getMemberIndexName()},
//                            splicingEsParam(param, memberTag)),
//
//                    RequestOptions.DEFAULT);
//            memberTagsPage.setTotal(response.getHits().getTotalHits().value);
//            return this.convertMemberTagsPage(memberTagsPage,
//                    convertSourceToMemberEsEntity(response.getHits(), MemberEntity.class),
//                    null);
//
//        } catch (IOException e) {
//            log.error("Seiya search member tags from es failed,message:[{}]",
//                    e.getMessage(), e);
//
//            return memberTagsPage;
//        }
//    }
//
//    /**
//     * 拼接ES查询参数 查询存在某个标签的所有会员
//     *
//     * @param memberTag MemberTag
//     * @return SearchSourceBuilder
//     */
//    private SearchSourceBuilder splicingEsParam(PageMemberByTagParam param, MemberTag
//            memberTag) {
//
//        SearchSourceBuilder builder =
//                this.createSearchQueryBuilder(param.getPageNumber(), param.getPageSize(),
//                        null, null, null);
//
//        org.elasticsearch.index.query.BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//                .must(QueryBuilders.existsQuery(memberTag.getMysqlExpression() + "." +
//                        memberTag.getField()));
//
//        builder.postFilter(boolQueryBuilder);
//        return builder;
//    }
//
//    private void setRangeQueryBuilder(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder, String name,
//                                      Object from, Object to) {
//
//        if (Objects.nonNull(from) || Objects.nonNull(to)) {
//            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(name);
//            if (Objects.nonNull(from)) {
//                if (from instanceof Date) {
//                    rangeQueryBuilder.gte(DateFormatUtils.format((Date) from,
//                            DATE_FORMAT));
//
//                } else {
//                    rangeQueryBuilder.gte(from);
//                }
//            }
//            if (Objects.nonNull(to)) {
//                if (to instanceof Date) {
//                    rangeQueryBuilder.lte(DateFormatUtils.format((Date) to,
//                            DATE_FORMAT));
//
//                } else {
//                    rangeQueryBuilder.lte(to);
//                }
//            }
//            queryBuilder.filter(rangeQueryBuilder);
//        }
//    }
//
//    private void setRangeEndIncludeNull(org.elasticsearch.index.query.BoolQueryBuilder queryBuilder, String name,
//                                        Object to) {
//
//        if (Objects.nonNull(to)) {
//            org.elasticsearch.index.query.BoolQueryBuilder queryBuilderFamily = QueryBuilders.boolQuery();
//            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(name);
//            if (to instanceof Date) {
//                rangeQueryBuilder.lte(DateFormatUtils.format((Date) to, DATE_FORMAT));
//            } else {
//                rangeQueryBuilder.lte(to);
//            }
//
//            queryBuilderFamily.should(rangeQueryBuilder);
//
//            queryBuilderFamily.should(QueryBuilders.boolQuery().mustNot(QueryBuilders.existsQuery(na
//                    me)));
//
//            queryBuilderFamily.minimumShouldMatch();
//            queryBuilder.filter(queryBuilderFamily);
//        }
//
//    }
//}
