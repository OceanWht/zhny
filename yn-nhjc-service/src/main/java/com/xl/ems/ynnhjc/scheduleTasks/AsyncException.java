package com.xl.ems.ynnhjc.scheduleTasks;

/**
 * @ClassName AsyncException
 * @Description 异步处理异常类
 * @Author wht
 * @Date 10:03
 **/
public class AsyncException extends Exception{
    private int code;
    private String errorMessage;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
