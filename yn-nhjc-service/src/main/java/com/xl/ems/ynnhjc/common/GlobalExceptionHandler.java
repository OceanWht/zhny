package com.xl.ems.ynnhjc.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 微服务中返回结果都是json格式，所以不能返回页面
     *
     * @param request
     * @param throwable
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public RestResponse<Object> handler(HttpServletRequest request, Throwable throwable) {
        LOGGER.error(throwable.getMessage(), throwable);
        RestCode restCode = Exception2CodeRepo.getCode(throwable);
        RestResponse<Object> restResponse = new RestResponse<Object>(restCode.code, restCode.msg);
        return restResponse;
    }
}
