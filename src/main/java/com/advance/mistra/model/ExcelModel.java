package com.advance.mistra.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 12:39
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
@TableName("excel")
public class ExcelModel implements Serializable {

    private static final long serialVersionUID = 7978635902861749469L;

    @ApiParam("主键id")
    @TableId(value = "id")
    private Long id;

    @ApiParam("姓名")
    @TableField("name")
    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ApiParam(value = "年龄", example = "10")
    @TableField("age")
    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ApiParam("学校")
    @TableField("school")
    @ExcelProperty(value = "学校", index = 2)
    private String school;

}
