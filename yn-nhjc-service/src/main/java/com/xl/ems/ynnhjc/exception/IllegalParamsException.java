package com.xl.ems.ynnhjc.exception;

/**
 * 由于异常会越来越多，没有必要新增一个异常就在RestCode中新增一个枚举类型，这样异常会越来越多
 * 所以新增一个type类型得枚举，针对同一类型异常得再次细分
 */
public class IllegalParamsException extends RuntimeException implements WithTypeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Type type;

    public IllegalParamsException(){

    }

    public IllegalParamsException(Type type,String msg){
        super(msg);
        this.type = type;
    }

    public Type type(){
        return type;
    }

    public enum Type{
        WRONG_PAGE_NUM(),WRONG_TYPE();
    }

}
