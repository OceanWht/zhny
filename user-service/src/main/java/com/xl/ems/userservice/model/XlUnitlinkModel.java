package com.xl.ems.userservice.model;

import com.xl.ems.userservice.bean.MeterArchiveBean;

import java.util.List;

public class XlUnitlinkModel {
    private Integer id;

    private Integer uid;

    private String ut;

    private String name;

    private String childunitid;

    private Integer parentunitid;

    private String userid;

    private Integer dataid;

    private Integer io;

    private List<XlUnitlinkModel> cu;

    private List<MeterArchiveBean> devices;

    private String i1;

    private String i2;

    private String i3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUt() {
        return ut;
    }

    public void setUt(String ut) {
        this.ut = ut == null ? null : ut.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getChildunitid() {
        return childunitid;
    }

    public void setChildunitid(String childunitid) {
        this.childunitid = childunitid == null ? null : childunitid.trim();
    }

    public Integer getParentunitid() {
        return parentunitid;
    }

    public void setParentunitid(Integer parentunitid) {
        this.parentunitid = parentunitid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public Integer getDataid() {
        return dataid;
    }

    public void setDataid(Integer dataid) {
        this.dataid = dataid;
    }

    public Integer getIo() {
        return io;
    }

    public void setIo(Integer io) {
        this.io = io;
    }

    public String getI1() {
        return i1;
    }

    public void setI1(String i1) {
        this.i1 = i1 == null ? null : i1.trim();
    }

    public String getI2() {
        return i2;
    }

    public void setI2(String i2) {
        this.i2 = i2 == null ? null : i2.trim();
    }

    public String getI3() {
        return i3;
    }

    public void setI3(String i3) {
        this.i3 = i3 == null ? null : i3.trim();
    }

    public List<XlUnitlinkModel> getCu() {
        return cu;
    }

    public void setCu(List<XlUnitlinkModel> cu) {
        this.cu = cu;
    }
}