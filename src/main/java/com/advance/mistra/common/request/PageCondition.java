package com.advance.mistra.common.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:06
 * @Description: 分页参数
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
public class PageCondition implements Serializable {

    private static final long serialVersionUID = 9036579753735013235L;

    @ApiParam("当前页,从0开始")
    private int pageNum = 0;

    @ApiParam("每页数量")
    private int pageSize = 10;

    @ApiParam("排序字段")
    private String order;

    @ApiParam("排序规则 默认降序，升序=ASC")
    private String orderBy = "DESC";
}
