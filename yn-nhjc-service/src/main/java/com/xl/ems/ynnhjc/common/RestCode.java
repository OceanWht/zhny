package com.xl.ems.ynnhjc.common;


public enum RestCode {

    OK(0, "OK"),
    SAVE_SUCCESS(200, "保存成功"),
    SAVE_FAIL(440, "保存失败"),
    UPDATE_SUCCESS(200, "修改成功"),
    UPDATE_FAIL(440, "修改失败"),
    DELETE_SUCCESS(200, "删除成功"),
    DELETE_FAIL(440, "删除失败"),
    UNKNOWN_ERROR(1,"未知异常"),
    TOKEN_INVALID(2,"TOKEN失效"),
    USER_NOT_EXIST(3,"用户不存在"),
    USER_ALREADY_EXIST(5,"用户已存在"),
    WRONG_LOGIN(4,"登陆失败"),
    WRONG_PAGE(10100,"页码不合法"),
    LACK_PARAMS(10101,"缺少参数"),
    WRONG_QUERY(10102,"查询失败"),
    WRONG_PASS(10103,"密码不符合规范或密码错误，要求包含大写字母、小写字母、数字、特殊符号" +
            "（不是字母，数字，下划线，汉字的字符，四种里至少三种）的8位以上组合，是否修改密码?若已经修改点击取消输入正确的密码"),
    WRONG_PASSCONFIRM(10104,"新密码与确认密码不一致"),
    WRONG_SAVEPASS(10105,"修改密码失败"),
    WRONG_APPLY(10106,"企业不可重复注册"),
    WRONG_APPLY_UPDATE(10107,"企业更新错误");


    public final int code;

    public final String msg;

    private RestCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
