package com.advance.mistra.model.jpa;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/19 22:06
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
@Entity
@Table(name = "jpa_permission")
@org.hibernate.annotations.Table(appliesTo = "jpa_permission", comment = "权限表")
public class JpaPermission {

    @Id
    @GeneratedValue
    @ApiParam(value = "主键ID", example = "1")
    @Column(name = "id", length = 20)
    protected Long id;

    @ApiParam("权限名称")
    @Column(name = "permission_name", columnDefinition = "VARCHAR(64) NOT NULL COMMENT '权限名称'")
    private String permissionName;

}
