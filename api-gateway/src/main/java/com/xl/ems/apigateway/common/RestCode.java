package com.xl.ems.apigateway.common;


public enum RestCode {


    OK(0, "OK"), UNKNOWN_ERROR(1, "服务异常"),
    WRONG_PAGE(10100, "页码不存在"),WRONG_PARAMS(10200, "参数错误");

    public final int code;

    public final String msg;

    RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
