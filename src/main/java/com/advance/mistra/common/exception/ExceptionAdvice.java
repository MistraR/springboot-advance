package com.advance.mistra.common.exception;

import cn.hutool.core.util.StrUtil;
import com.advance.mistra.common.response.ResponseResult;
import com.advance.mistra.utils.InternationalizationUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
@ControllerAdvice(annotations = RestController.class)
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
     * 参数校验未通过异常 @RequestBody参数校验失败
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

    /**
     * 合并之后的写法
     *
     * @param e        异常
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult globalException(Exception e, HttpServletResponse response) {
        ResponseResult responseResult = new ResponseResult();
        // 参数校验未通过异常,@RequestBody参数校验失败
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = exception.getBindingResult().getAllErrors();
            StringBuffer sb = new StringBuffer();
            List<String> errorArr = Lists.newArrayList();
            for (ObjectError error : errors) {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    errorArr.add(fieldError.getField() + fieldError.getDefaultMessage());
                } else {
                    errorArr.add(error.getObjectName() + error.getDefaultMessage());
                }
            }
            String errMsg = StrUtil.join(";", errorArr.toArray(new String[]{}));
            responseResult.setAll(false, BusinessErrorCode.REQUEST_PARAM_ERROR, errMsg);
        } else if (e instanceof ConstraintViolationException) {
            // @RequestParam 参数校验失败
            ConstraintViolationException exception = (ConstraintViolationException) e;
            StringBuffer sb = new StringBuffer();
            List<String> errorArr = Lists.newArrayList();
            for (ConstraintViolation constraint : exception.getConstraintViolations()) {
                errorArr.add(constraint.getInvalidValue() + "非法" + constraint.getMessage());
                //sb.append(constraint.getInvalidValue()).append("值不正确,").append(constraint.getMessage()).append(";");
            }
            responseResult.setAll(false, BusinessErrorCode.REQUEST_PARAM_ERROR, StrUtil.join(";", errorArr.toArray(new String[]{})));
        } else {
            responseResult.setAll(false, BusinessErrorCode.SYSTEM_ERROR, StrUtil.join(";", e.getMessage()));
        }
        return responseResult;
    }
}
