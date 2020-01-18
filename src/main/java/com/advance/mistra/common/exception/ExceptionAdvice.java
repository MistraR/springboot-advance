package com.advance.mistra.common.exception;

import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.utils.InternationalizationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:25
 * @Description: 全局异常处理
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @Autowired
    private InternationalizationUtil i18nUtil;

    /**
     * 业务异常返回结果
     *
     * @param businessException
     * @return com.advance.mistra.common.response.ResponseResult
     * @author Mistra
     * @date 2020/1/18 20:27
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseResult handler(BusinessException businessException) {
        String message = businessException.getMessage();
        Integer errorCode = businessException.getCode();
        if (StringUtils.isEmpty(errorCode.toString())) {
            errorCode = BusinessErrorCode.SYSTEM_ERROR;
        }
        String resultMessage = i18nUtil.i18n(errorCode + "", businessException.getArgs());
        log.info("业务异常:{}-{}-{}", errorCode, message, resultMessage, businessException);
        return new ResponseResult(false, errorCode, resultMessage);
    }

    /**
     * 参数验证异常
     *
     * @param e
     * @return com.advance.mistra.common.response.ResponseResult
     * @author Mistra
     * @date 2020/1/18 20:27
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseResult handler(BindException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        return new ResponseResult(false, BusinessErrorCode.SYSTEM_ERROR, objectErrors.get(0).getDefaultMessage());
    }

    /**
     * 参数验证异常
     *
     * @param e
     * @return com.advance.mistra.common.response.ResponseResult
     * @author Mistra
     * @date 2020/1/18 20:27
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult handler(MethodArgumentNotValidException e) {
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
        return new ResponseResult(false, BusinessErrorCode.SYSTEM_ERROR, objectErrors.get(0).getDefaultMessage());
    }
}
