package com.advance.mistra.common.exception;

/**
 * 描述
 *
 * @author mistra@future.com
 * @date 2022/2/28
 */
public enum ResultCode {
    /**
     *
     */
    SUCCESS(0, "请求成功", 200),
    SYSTEM_RUNTIME_EXCEPTION(500, "系统异常，请重试", 500),
    SYSTEM_HTTP_MSG_RESOLVE_EXCEPTION(1, "请求数据格式错误", 400),
    SYSTEM_PARAM_NOT_VALID_EXCEPTION(2, "请求参数格式错误", 400),
    OPERATE_TOO_FREQUENTLY(3, "您操作过于频繁，请稍后再试", 400),
    PERMISSION_DENIED(4, "没有权限，拒绝访问", 400),
    ENTITY_NOT_FOUND(5, "对象实体不存在", 400),
    ENTITY_EXISTED(6, "对象实体已存在", 400),
    NETWORK_FAILURE(7, "内部网络错误", 500),
    PROCESS_TIMEOUT(8, "内部处理超时", 500),
    SYSTEM_INVALID_REQUEST(9, "请求无效", 400),
    SYSTEM_CRYPTO_ERROR(10, "加解密错误", 400),
    RPC_FAILURE(11, "RPC调用错误", 500),
    SYSTEM_SESSION_EXPIRED(12, "当前账号登录过期，请重新登录", 400);

    private final Integer code;
    private final String message;
    private final Integer httpCode;

    public Integer getErrorCode() {
        return this.code;
    }

    public String getErrorMessage() {
        return this.message;
    }

    public Integer getHttpCode() {
        return this.httpCode;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResultCode(Integer code, String message, Integer httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }
}
