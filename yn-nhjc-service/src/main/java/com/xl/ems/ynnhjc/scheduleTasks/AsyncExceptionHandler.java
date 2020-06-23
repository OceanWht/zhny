package com.xl.ems.ynnhjc.scheduleTasks;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

/**
 * @ClassName AsyncExceptionHandler
 * @Description 异步异常处理类
 * @Author wht
 * @Date 10:02
 **/
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncExceptionHandler.class);

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        LOGGER.info("Async method: {} has uncaught exception,params:{}", method.getName(), JSON.toJSONString(params));

        if (ex instanceof AsyncException) {
            AsyncException asyncException = (AsyncException) ex;
            LOGGER.info("asyncException:{}",asyncException.getErrorMessage());
        }

        LOGGER.info("Exception :");
        ex.printStackTrace();
    }
}
