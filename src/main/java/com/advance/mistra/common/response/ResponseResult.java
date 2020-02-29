package com.advance.mistra.common.response;

import com.advance.mistra.common.exception.BusinessErrorCode;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.MDC;

/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/1/18 19:49
 * @Description: 统一封装返回值
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Data
public class ResponseResult {

    @ApiModelProperty(value = "是否处理成功", name = "success", example = "true")
    private Boolean success;
    @ApiModelProperty(value = "返回码", name = "code", example = "0")
    private Integer code;
    @ApiModelProperty(value = "处理消息", name = "msg", example = "处理成功")
    private String msg;
    @ApiModelProperty(value = "返回数据", name = "data", example = "{}")
    private Object data;
    @ApiModelProperty(value = "唯一请求id，由logback生成", name = "requestId", example = "a1b2c3d4e5f6g7h8i9")
    private String requestId = MDC.get("requestId");

    public ResponseResult(Boolean success) {
        super();
        this.success = success;
    }

    @Override
    public String toString() {
        return "ResponseResult [success=" + success + ", code=" + code + ", msg=" + msg + ", data=" + data + "]";
    }

    public ResponseResult(Boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public ResponseResult(Boolean success, Integer code, String msg) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult setMsg(Boolean success, String msg) {
        this.success = success;
        this.msg = msg;
        return this;
    }

    public ResponseResult(Boolean success, String msg, Object data) {
        super();
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult(Boolean success, Integer code, String msg, Object data) {
        super();
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseResult setAll(Boolean success, Integer code, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        if (data == null) {
            data = new Object();
        }
        this.data = data;
        return this;
    }

    public ResponseResult setAll(Boolean success, Integer code, String msg) {
        this.success = success;
        this.msg = msg;
        this.code = code;
        return this;
    }

    /**
     * 默认构建成功请求 注意 默认success=true code=200 data=null
     *
     * @param
     * @return com.advance.mistra.common.response.ResponseResult
     * @author Mistra
     * @date 2020/1/18 20:00
     */
    public static ResponseResult buildSuccess() {
        return new ResponseResult(true, BusinessErrorCode.SUCCESS, "处理成功！", new JSONObject());
    }

    public static ResponseResult buildFail() {
        return new ResponseResult(true, BusinessErrorCode.FAIL, "请求失败！", new JSONObject());
    }

    public static ResponseResult buildSuccess(Object data) {
        if (data == null) {
            return new ResponseResult(true, BusinessErrorCode.SUCCESS, "处理成功！");
        }
        return new ResponseResult(true, BusinessErrorCode.SUCCESS, "处理成功！", data);
    }

    public ResponseResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResponseResult() {
        super();
    }

    public String toJson() {
        String json = JSONObject.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
        return json;
    }
}
