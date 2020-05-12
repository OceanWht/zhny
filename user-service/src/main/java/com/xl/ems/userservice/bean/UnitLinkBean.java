package com.xl.ems.userservice.bean;

import java.util.List;

public class UnitLinkBean {
    private String uid;
    private String ut;
    private String name;
    private String childunitid;
    private String parentunitid;
    private String on;
    private String udt;
    private List<UnitLinkBean> cu;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildunitid() {
        return childunitid;
    }

    public void setChildunitid(String childunitid) {
        this.childunitid = childunitid;
    }

    public String getParentunitid() {
        return parentunitid;
    }

    public void setParentunitid(String parentunitid) {
        this.parentunitid = parentunitid;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getUdt() {
        return udt;
    }

    public void setUdt(String udt) {
        this.udt = udt;
    }

    public List<UnitLinkBean> getCu() {
        return cu;
    }

    public void setCu(List<UnitLinkBean> cu) {
        this.cu = cu;
    }
}
