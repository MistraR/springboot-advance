package com.advance.mistra.common.exception;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:43
 * @Description: 异常code
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
public class BusinessErrorCode {

    /**
     * 请求成功!
     */
    public static final int SUCCESS = 0;
    /**
     * 请求失败!
     */
    public static final int FAIL = -1;
    /**
     * 系统发生错误!
     */
    public static final int SYSTEM_ERROR = 500;

    /**
     * 请求参数为空!
     */
    public static final int REQUEST_PARAM_IS_EMPTY = 100001;
    /**
     * 请求参数错误!
     */
    public static final int REQUEST_PARAM_ERROR = 100002;
    /**
     * 请求地址不存在!
     */
    public static final int REQUEST_NO_HANDLER_FOUND = 100003;
}
