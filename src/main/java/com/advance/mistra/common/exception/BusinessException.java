package com.advance.mistra.common.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:27
 * @Description: 业务异常。可以传入args参数。在messages.properties中的占位符会替换为指定的参数值
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 7716403813814830930L;

    private Integer code;
    private Object[] args;

    public BusinessException(String messageCode) {
        super(messageCode);
    }

    /**
     * 构建异常
     *
     * @return ApiException
     */
    public static BusinessException build(int messageCode) {
        return new BusinessException(messageCode);
    }

    public BusinessException(int messageCode) {
        super(getCodeMessage(messageCode));
        this.code = messageCode;
    }

    public BusinessException(int messageCode, Object... args) {
        super(getCodeMessage(messageCode));
        this.code = messageCode;
        this.args = args;
    }

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getErrorMessage());
        this.code = resultCode.getErrorCode();
    }

    private static String getCodeMessage(int messageCode) {
        List<String> fieldName = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class businessErrorCode = classLoader.loadClass("com.advance.mistra.common.exception.BusinessErrorCode");
            Field[] fields = businessErrorCode.getDeclaredFields();
            List<Field> fieldList = Arrays.asList(fields);
            fieldList.stream().forEach(field -> {
                try {
                    field.isAccessible();
                    if (Integer.parseInt(field.get(businessErrorCode).toString()) == messageCode) {
                        fieldName.add(field.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            return fieldName.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAIL";
        }
    }
}
