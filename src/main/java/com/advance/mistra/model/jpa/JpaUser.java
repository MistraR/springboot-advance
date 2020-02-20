package com.advance.mistra.model.jpa;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @javax.persistence.Table 修改默认ORM规则，属性name设置表名；
 * @org.hibernate.annotations.Table 建表时的描述， 属性comment修改表描述；
 * @Id 主键
 * @GenericGenerator 设置主键策略，这里使用了Hibernate的UUID来生成主键；
 * @GeneratedValue 设置主键值, 属性generator关联主键策略的name；
 * @Column 修改默认ORM规则；
 * name设置表中字段名称，表字段和实体类属性相同，则该属性可不写；
 * unique设置该字段在表中是否唯一，默认false；
 * nullable是否可为空，默认true；
 * insertable表示insert操作时该字段是否响应写入，默认为true；
 * updatable表示update操作时该字段是否响应修改，默认为true；
 * columnDefinition是自定义字段，可以用这个属性来设置字段的注释；
 * table表示当映射多个表时，指定表的表中的字段，默认值为主表的表名；
 * length是长度，仅对varchar类型的字段生效,默认长度为255；
 * precision表示一共多少位；
 * scale表示小数部分占precision总位数的多少位，例子中两者共同使用来确保经纬度的精度；
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
@org.hibernate.annotations.Table(appliesTo = "jpa_user", comment = "用户表")
public class JpaUser {

    @Id
    @GeneratedValue
    @ApiParam("主键ID")
    @Column(name = "id", length = 20)
    private Long id;

    @ApiParam("用户名")
    @Column(name = "user_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '用户名'")
    private String userName;

    @ApiParam("昵称")
    @Column(name = "nick_name", columnDefinition = "VARCHAR(255) NOT NULL COMMENT '昵称'")
    private String nickName;

    @ApiParam("岗位")
    @Column(name = "position")
    private String position;

    @ApiParam("年龄")
    @Column(name = "age")
    private Integer age;

    @Column(name = "longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    /**
     * @ManyToOne 用户:角色 多个用户对应一个角色，当我们创建表结构时，应在多的一方去维护表关系，也就是说，应将@ManyToOne注解加在用户表中，并且设置为懒加载。
     * @JsonBackReference 生成json时该属性排除
     */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private JpaRole role;
}
