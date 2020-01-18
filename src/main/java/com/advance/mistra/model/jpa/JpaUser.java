package com.advance.mistra.model.jpa;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 18:50
 * @Description: Jpa测试实体类
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
@Entity
@Table(name = "jpa_user")
public class JpaUser {

    @Id
    @GeneratedValue
    @ApiParam("主键ID")
    protected Long id;

    @ApiParam("用户名")
    private String userName;

    @ApiParam("昵称")
    private String nickName;

    @ApiParam("岗位")
    private String position;

    @ApiParam("年龄")
    private Integer age;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private JpaRole role;
}
