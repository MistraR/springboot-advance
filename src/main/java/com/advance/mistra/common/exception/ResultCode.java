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
    SYSTEM_RUNTIME_EXCEPTION("X100", "系统异常，请重试", 500),
    SYSTEM_HTTP_MSG_RESOLVE_EXCEPTION("X101", "请求数据格式错误", 400),
    SYSTEM_PARAM_NOT_VALID_EXCEPTION("X102", "请求参数格式错误", 400),
    OPERATE_TOO_FREQUENTLY("X104", "您操作过于频繁，请稍后再试", 400),
    PERMISSION_DENIED("X105", "没有权限，拒绝访问", 400),
    ENTITY_NOT_FOUND("X106", "对象实体不存在", 400),
    ENTITY_EXISTED("X107", "对象实体已存在", 400),
    NETWORK_FAILURE("X108", "内部网络错误", 500),
    PROCESS_TIMEOUT("X109", "内部处理超时", 500),
    SYSTEM_INVALID_REQUEST("X998", "请求无效", 400),
    SYSTEM_CRYPTO_ERROR("X999", "加解密错误", 400),
    RPC_FAILURE("X110", "RPC调用错误", 500),
    SYSTEM_SESSION_EXPIRED("SSO_100001", "当前账号登录过期，请重新登录", 400);

    private final String code;
    private final String message;
    private final Integer httpCode;

    public String getErrorCode() {
        return this.code;
    }

    public String getErrorMessage() {
        return this.message;
    }

    public Integer getHttpCode() {
        return this.httpCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ResultCode(String code, String message, Integer httpCode) {
        this.code = code;
        this.message = message;
        this.httpCode = httpCode;
    }
}
