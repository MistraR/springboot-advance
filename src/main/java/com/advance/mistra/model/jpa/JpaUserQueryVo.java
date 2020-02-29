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

    @ApiParam("id字符串，用来测试 in 查询")
    private String ids;

    @ApiParam("用户名")
    private String userName;

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("权限名称")
    private String permissionName;

    @ApiParam("角色名称")
    private String roleName;

    @ApiParam("岗位")
    private String position;

    @ApiParam(value = "年龄", example = "1")
    private Integer age;
}
