package com.xl.ems.apigateway.bean;

/**
 * @ClassName UnitInfoBean
 * @Description 企业单元信息
 * @Author wht
 * @Date 16:24
 **/
public class UnitInfoBean {

    private String uid;

    private String name;

    private String ut;

    private String in;

    private String islimit;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getIslimit() {
        return islimit;
    }

    public void setIslimit(String islimit) {
        this.islimit = islimit;
    }
}
