package com.advance.mistra.model.jpa;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:10
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
public class JpaUserQueryVo {

    @ApiParam("用户名")
    private String userName;

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("岗位")
    private String position;
}
