package com.xl.ems.apigateway.model;

public class XlUnitCalcGroupModelKey {
    private Integer id;

    private String groupid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }
}