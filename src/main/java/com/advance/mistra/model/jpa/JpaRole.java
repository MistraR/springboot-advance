package com.advance.mistra.model.jpa;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 18:52
 * @Description: Jpa测试实体类
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
@Entity
@Table(name = "jpa_role")
public class JpaRole {

    @Id
    @GeneratedValue
    @ApiParam("主键ID")
    protected Long id;

    @ApiParam("用角色名")
    private String roleName;
}
