{
  "from": ${pageNumber},
  "size": ${pageSize},
  "sort": [
    {
      "${orderField}": {
        "nested": {
          "path": "${nestedPath}"
        },
        "order": "${order}"
      }
    }
  ],
  "query": {
    "bool": {
      "must": [
        {
          "exists": {
            "field": "userId"
          }
        }
        <#if accountId ??>,
        {
          "match": {
            "userId": ${accountId}
          }
        }</#if>
        <#if accountIds ??>,
        {
          "terms": {
            "userId": [
            <#list accountIds as item>
              ${item}<#if item_has_next>,</#if>
            </#list>
            ]
          }
        }</#if>
        ,{
          "nested": {
            "path": "basic",
            "query": {
              "bool": {
                "must_not": [
                  {
                    "exists": {
                      "field": "empty"
                    }
                  }
                  <#if needAllUser?exists || (needAllUser ?? && needAllUser == false)>,
                  {
                    "match": {
                      "basic.memberType": ""
                    }
                  }</#if>
                ],
                "must": [
                  {
                    "exists": {
                      "field": "basic"
                    }
                  }
                  <#if firstMemberDayStart ?? || firstMemberDayEnd ??>,
                  {
                    "range": {
                      "basic.firstMemberDay": {
                        <#if firstMemberDayStart ?? && firstMemberDayEnd ??>
                          "gte": "${firstMemberDayStart}",
                          "lte": "${firstMemberDayEnd}"
                        <#elseif firstMemberDayStart ??>
                          "gte": "${firstMemberDayStart}"
                        <#else>
                          "lte": "${firstMemberDayEnd}"
                        </#if>
                      }
                    }
                  }</#if>
                  <#if memberValid ??>,
                  {
                    "range": {
                      "basic.memberExpireDay": {
                        <#if memberValid == true>
                        "gte": "${curdate}"
                        <#else>
                        "lte": "${curdate}"
                        </#if>
                      }
                    }
                  }</#if>
                  <#if consultantEmail ??>,
                  {
                    "match": {
                      "basic.consultantEmail": "${consultantEmail}"
                    }
                  }</#if>
                  <#if mainAccount ??>,
                  {
                    "match": {
                      "basic.main": ${mainAccount}
                    }
                  }</#if>
                  <#if measured ?? && measured == false>,
                  {
                    "bool": {
                      "should": [
                        {
                          "match": {
                            "basic.measured": false
                          }
                        },
                        {
                          "bool": {
                            "must_not": [
                              {
                                "exists": {
                                  "field": "basic.measured"
                                }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  }</#if>
                  <#if measured ?? && measured == true>,
                  {
                    "match": {
                      "basic.measured": ${measured}
                    }
                  }</#if>
                  <#if wechatAdded ?? && wechatAdded == false>,
                  {
                    "bool": {
                      "should": [
                        {
                          "match": {
                            "basic.wechatAdded": false
                          }
                        },
                        {
                          "bool": {
                            "must_not": [
                              {
                                "exists": {
                                  "field": "basic.wechatAdded"
                                }
                              }
                            ]
                          }
                        }
                      ]
                    }
                  }</#if>
                  <#if wechatAdded ?? && wechatAdded == true>,
                  {
                    "match": {
                      "basic.wechatAdded": ${wechatAdded}
                    }
                  }</#if>
                  <#if memberInfo ??>,
                  {
                    "bool": {
                      "should": [
                        {
                          "wildcard": {
                            "basic.nickName": {
                              "value": "*${memberInfo}*"
                            }
                          }
                        },
                        {
                          "wildcard": {
                            "basic.wechatNickname": {
                              "value": "*${memberInfo}*"
                            }
                          }
                        },
                        {
                          "wildcard": {
                            "basic.wechatAccount": {
                              "value": "*${memberInfo}*"
                            }
                          }
                        }
                      ]
                    }
                  }</#if>
                  <#if consultant ??>,
                  {
                    "bool": {
                      "should": [
                        {
                          "wildcard": {
                            "basic.consultantName": {
                              "value": "*${consultant}*"
                            }
                          }
                        },
                        {
                          "wildcard": {
                            "basic.consultantEmail": {
                              "value": "*${consultant}*"
                            }
                          }
                        }
                      ]
                    }
                  }</#if>
                ]
              }
            }
          }
        },
        {
          "nested": {
            "path": "tags",
            "query": {
              "bool": {
                "must_not": [
                  {
                    "exists": {
                      "field": "empty"
                    }
                  }
                  <#if weChatBind ?? && weChatBind == false>,
                  {
                    "exists": {
                      "field": "tags.wechatUnionId"
                    }
                  }</#if>
                ],
                "must": [
                  {
                    "exists": {
                      "field": "tags"
                    }
                  }
                  <#if weChatBind ?? && weChatBind == true>,
                  {
                    "exists": {
                      "field": "tags.wechatUnionId"
                    }
                  }</#if>
                  <#if vitalityLevel ??>,
                  {
                    "match": {
                      "tags.vitalityLevel": "${vitalityLevel}"
                    }
                  }</#if>
                  <#if wechatUnionIds ??>,
                  {
                    "terms": {
                      "tags.wechatUnionId": [
                      <#list wechatUnionIds as item>
                        "${item}"<#if item_has_next>,</#if>
                      </#list>
                      ]
                    }
                  }</#if>
                  <#if lastTrainingDayStart ?? || lastTrainingDayEnd ??>,
                  {
                    "range": {
                      "tags.lastCompleteCourseTime": {
                        <#if lastTrainingDayStart ?? && lastTrainingDayEnd ??>
                          "gte": "${lastTrainingDayStart}",
                          "lte": "${lastTrainingDayEnd}"
                        <#elseif lastTrainingDayStart ??>
                          "gte": "${lastTrainingDayStart}"
                        <#else>
                          "lte": "${lastTrainingDayEnd}"
                        </#if>
                      }
                    }
                  }</#if>
                  <#if lastSevenFinishTrainingCountStart ?? || lastSevenFinishTrainingCountEnd ??>,
                  {
                    "range": {
                      "tags.lastSevenCompleteCourseNumber": {
                        <#if lastSevenFinishTrainingCountStart ?? && lastSevenFinishTrainingCountEnd ??>
                          "gte": ${lastSevenFinishTrainingCountStart},
                          "lte": ${lastSevenFinishTrainingCountEnd}
                        <#elseif lastSevenFinishTrainingCountStart ??>
                          "gte": ${lastSevenFinishTrainingCountStart}
                        <#else>
                          "lte": ${lastSevenFinishTrainingCountEnd}
                        </#if>
                      }
                    }
                  }</#if>
                  <#if real ??>,
                  {
                    "match": {
                      "tags.real": ${real}
                    }
                  }
                  </#if>
                  <#if commonMember ??>,
                  {
                    "match": {
                      "tags.commonMember": ${commonMember}
                    }
                  }</#if>
                  <#if employee ??>,
                  {
                    "match": {
                      "tags.employee": ${employee}
                    }
                  }</#if>
                  <#if kol ??>,
                  {
                    "match": {
                      "tags.kol": ${kol}
                    }
                  }</#if>
                  <#if vvip ??>,
                  {
                    "match": {
                      "tags.vvip": ${vvip}
                    }
                  }</#if>
                  <#if memberActive ??>,
                  {
                    "match": {
                      "tags.memberActive": "${memberActive}"
                    }
                  }</#if>
                  <#if activitySource ??>,
                  {
                    "match": {
                      "tags.activitySource": "${activitySource}"
                    }
                  }</#if>
                  <#if communityId ?? || addGroupStatus ?? || addGroupStatusList ?? || communityName ??>,
                  {
                    "nested": {
                      "path": "tags.community",
                      "query": {
                        "bool": {
                          "must": [
                            {
                              "exists": {
                                "field": "tags.community"
                              }
                            }
                            <#if communityId ??>,
                            {
                              "match": {
                                "tags.community.communityId": ${communityId}
                              }
                            }</#if>
                            <#if communityName ??>,
                            {
                              "wildcard": {
                                "tags.community.communityName": {
                                  "value": "*${communityName}*"
                                }
                              }
                            }</#if>
                            <#if addGroupStatus ??>,
                            {
                              "match": {
                                "tags.community.status": "${addGroupStatus}"
                              }
                            }</#if>
                            <#if addGroupStatusList ??>,
                            {
                              "terms": {
                                "tags.community.status": [
                                <#list addGroupStatusList as item>
                                  "${item}"<#if item_has_next>,</#if>
                                </#list>
                                ]
                              }
                            }</#if>
                          ]
                        }
                      }
                    }
                  }</#if>
                  <#if cyclePayStatus ??>,
                  {
                    "nested": {
                      "path": "tags.cyclePay",
                      "query": {
                        "bool": {
                          "must": [
                            {
                              "match": {
                                "tags.cyclePay.status": "${cyclePayStatus}"
                              }
                            }
                          ]
                        }
                      }
                    }
                  }</#if>
                  <#if activityCode ?? || activityStatus ??>,
                  {
                    "nested": {
                      "path": "tags.activity",
                      "query": {
                        "bool": {
                          "must": [
                            {
                              "exists": {
                                "field": "tags.activity"
                              }
                            }
                            <#if activityCode ??>,
                            {
                              "match": {
                                "tags.activity.activityCode": "${activityCode}"
                              }
                            }</#if>
                            <#if activityStatus ??>,
                            {
                              "match": {
                                "tags.activity.status": "${activityStatus}"
                              }
                            }</#if>
                          ]
                        }
                      }
                    }
                  }</#if>
                  <#if freezeStatus ??>,
                  {
                    "nested": {
                      "path": "tags.freezeInfo",
                      "query": {
                        "bool": {
                          "must": [
                            {
                              "match": {
                                "tags.freezeInfo.freezeStatus": ${freezeStatus}
                              }
                            }
                          ]
                        }
                      }
                    }
                  }</#if>
                ]
              }
            }
          }
        }
        <#if purchaseIntention ?? || conversionResult ??>,
        {
          "nested": {
            "path": "manualTags",
            "query": {
              "bool": {
                "must": [
                  {
                    "exists": {
                      "field": "manualTags"
                    }
                  }
                  <#if purchaseIntention ??>,
                  {
                    "match": {
                      "manualTags.purchaseIntention": "${purchaseIntention}"
                    }
                  }</#if>
                  <#if conversionResult ??>,
                  {
                    "match": {
                      "manualTags.conversionResult": ${conversionResult}
                    }
                  }</#if>
                ]
              }
            }
          }
        }</#if>
      ]
    }
  }
}
