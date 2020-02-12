package com.advance.mistra.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/2/12 16:53
 * @Description:
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
public class ParameterTestModel {

    /**
     * DateTimeFormat规范接收前端参数的格式
     * JsonFormat规范返回给前端的格式
     * 一律用java.util.Date  java.sql.Date接收会报错
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
