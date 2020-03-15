package com.advance.mistra.plugin.es;

import com.advance.mistra.common.exception.BusinessErrorCode;
import com.advance.mistra.common.exception.BusinessException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.advance.mistra.common.SystemConstans.*;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/3/15 18:59
 * @ Description:
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class EsBaseService {

    private static final Logger logger = LoggerFactory.getLogger(EsBaseService.class);


    public void addDataToEs(EsRestClient esRestClient, String params, String indexName, String indexType, String id) {
        try {
            if (indexType == null) {
                indexType = ES_DEFAULT_TYPE;
            }
            StringBuilder url = new StringBuilder();
            url.append(SLASH).append(indexName).append(SLASH).append(indexType).append(SLASH).append(id);
            JSONObject jsonObject = esRestClient.doPutByJson(url.toString(), params);
            if (jsonObject.getIntValue(CODE) >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(jsonObject.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            esRestClient.doGet(SLASH + indexName + ES_REFRESH);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    public void bulkAddDataToEs(EsRestClient esRestClient, String params, String indexName) {
        try {
            JSONObject jsonObject = esRestClient.doPutByJson(ES_BULK, params);
            if (jsonObject.getIntValue(CODE) >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(jsonObject.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            esRestClient.doGet(SLASH + indexName + ES_REFRESH);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    public void updateEsDataById(EsRestClient esRestClient, String params, String indexName, String indexType, String id) {
        try {
            if (indexType == null) {
                indexType = ES_DEFAULT_TYPE;
            }
            StringBuilder url = new StringBuilder();
            url.append(SLASH).append(indexName).append(SLASH).append(indexType).append(SLASH).append(id).append(ES_UPDATE);
            JSONObject jsonObject = esRestClient.doPutByJson(url.toString(), params);
            if (jsonObject.getIntValue(CODE) >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(jsonObject.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            esRestClient.doGet(SLASH + indexName + ES_REFRESH);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    public JSONObject getEsDataById(EsRestClient esRestClient, String indexName, String indexType, String id) {
        try {
            if (indexType == null) {
                indexType = ES_DEFAULT_TYPE;
            }
            StringBuilder url = new StringBuilder();
            url.append(SLASH).append(indexName).append(SLASH).append(indexType).append(SLASH).append(id);
            JSONObject jsonObject = esRestClient.doGet(url.toString());
            int code = jsonObject.getIntValue(CODE);
            if (code == HttpStatus.SC_NOT_FOUND) {
                return null;
            }
            if (code >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(jsonObject.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            JSONObject result = JSONObject.parseObject(jsonObject.getString(DATA));
            if (result.getBooleanValue(FOUND)) {
                return result;
            }
            return null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    public JSONObject getEsDataListAndAggs(EsRestClient esRestClient, String params, String indexName, String indexType) {
        try {
            JSONObject tempResult;
            StringBuilder stringBuilder = new StringBuilder();
            String url = indexType != null ? stringBuilder.append(SLASH).append(indexName).append(SLASH).append(indexType).append(ES_SEARCH).toString()
                    : stringBuilder.append(SLASH).append(indexName).append(ES_SEARCH).toString();
            tempResult = esRestClient.doPostByJson(url, params);
            if (tempResult.getIntValue(CODE) >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(tempResult.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            JSONObject data = JSONObject.parseObject(tempResult.getString(DATA));
            JSONObject hits = data.getJSONObject(ES_HITS);
            JSONObject result = new JSONObject();
            result.put(TOTAL, hits.getJSONObject(TOTAL).getLongValue(VALUE));
            result.put(LIST, hits.getJSONArray(ES_HITS));
            result.put(ES_AGGS, hits.getJSONObject(ES_AGGS));
            return result;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    /**
     * 设置ES分页查询参数
     *
     * @param param 参数Map
     */
    public void setEsPageParam(Map<String, Object> param) {
        int current = 1;
        int pageSize = 10;
        if (param.containsKey(CURRENT)) {
            current = Integer.parseInt(param.get(CURRENT).toString());
        }
        if (param.containsKey(COUNT)) {
            pageSize = Integer.parseInt(param.get(COUNT).toString());
        }
        int from = (current - 1) * pageSize;
        param.put(FROM, from + "");
        param.put(SIZE, pageSize + "");
    }

    /**
     * 设置ES时间查询范围，默认查询7天内数据
     *
     * @param param 参数Map
     */
    public void setEsDateRangeParam(Map<String, Object> param) {
        String stime = param.get("stime").toString();
        if (StringUtils.isEmpty(stime)) {
            Date now = new Date();
            stime = DateFormatUtils.format(DateUtils.addDays(now, 7), TIME_FORMAT_YYYYMMDDHHMMSS);
            param.put("stime", stime);
            param.put("etime", DateFormatUtils.format(now, TIME_FORMAT_YYYYMMDDHHMMSS));
        }
    }

    public void deleteEsDataById(EsRestClient esRestClient, String indexName, String indexType, String id) {
        try {
            if (indexType == null) {
                indexType = ES_DEFAULT_TYPE;
            }
            StringBuilder url = new StringBuilder();
            url.append(SLASH).append(indexName).append(SLASH).append(indexType).append(SLASH).append(id);
            JSONObject tempResult = esRestClient.doDelete(url.toString());
            if (tempResult.getIntValue(CODE) >= HttpStatus.SC_MULTIPLE_CHOICES) {
                logger.error(tempResult.getString(DATA));
                throw new BusinessException(BusinessErrorCode.ES_ERROR);
            }
            esRestClient.doGet(SLASH + indexName + ES_REFRESH);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

    public void refreshIndex(EsRestClient esRestClient, String indexs) {
        try {
            esRestClient.doGet(SLASH + indexs + ES_REFRESH);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException(BusinessErrorCode.ES_ERROR);
        }
    }

}
