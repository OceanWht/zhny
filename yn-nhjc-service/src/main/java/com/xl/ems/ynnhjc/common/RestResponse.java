package com.xl.ems.ynnhjc.common;


public class RestResponse<T> {

    private int code;
    private String msg;
    private T result;

    public RestResponse() {
        this(RestCode.OK.code,RestCode.OK.msg);
    }

    public RestResponse(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> RestResponse<T> success(){
        return new RestResponse<T>();
    }

    public static <T> RestResponse<T> success(T result){
        RestResponse<T> restResponse = new RestResponse<T>();
        restResponse.setResult(result);
        return restResponse;
    }

    public static <T> RestResponse<T> error(RestCode restCode){
        return new RestResponse<T>(restCode.code,restCode.msg);
    }

    public static <T> RestResponse<T> error(T result){
        RestResponse<T> restResponse = new RestResponse<T>();
        restResponse.setCode(RestCode.UNKNOWN_ERROR.code);
        restResponse.setMsg(RestCode.UNKNOWN_ERROR.msg);
        restResponse.setResult(result);
        return restResponse;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
