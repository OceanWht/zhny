package com.xl.ems.ynnhjc.common;

import com.google.common.collect.ImmutableMap;
import com.xl.ems.ynnhjc.exception.IllegalParamsException;
import com.xl.ems.ynnhjc.exception.WithTypeException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.reflect.FieldUtils;


public class Exception2CodeRepo {

    /**
     * 添加了一些常量键值对，避免写if else 比较优雅
     */
    private static final ImmutableMap<Object,RestCode> MAP = ImmutableMap.<Object,RestCode>builder()
            .put(IllegalParamsException.Type.WRONG_PAGE_NUM,RestCode.WRONG_PAGE)
            .put(IllegalStateException.class,RestCode.UNKNOWN_ERROR)
            .build();


    public static RestCode getCode(Throwable throwable) {

        if (throwable == null){
            return RestCode.UNKNOWN_ERROR;
        }

        Object target = throwable;

        if (throwable instanceof WithTypeException) {
            Object type = getType(throwable);
            if (type != null) {
                target = type;
            }
        }

            //如果还是找不到错误类型，也就是说之前得枚举类里没有，就进一步挖掘
            RestCode restCode = MAP.get(target);
            if (restCode != null){
                //找到了就直接返回
                return restCode;
            }

            //找到根本原因
            Throwable rootCase = ExceptionUtils.getRootCause(throwable);
            if (rootCase != null){
                return getCode(rootCase);
            }

            return restCode.UNKNOWN_ERROR;

        }

    /**
     * 利用反射读取类型
     * @param throwable
     * @return
     */
    private static Object getType(Throwable throwable) {

        try {
            return  FieldUtils.readDeclaredField(throwable,"type",true);
        } catch (Exception e) {
            return null;
        }


    }
}
