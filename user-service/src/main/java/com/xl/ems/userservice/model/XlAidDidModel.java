package com.xl.ems.userservice.model;

import com.xl.ems.userservice.bean.AnalogDocBean;

import java.util.List;

public class XlAidDidModel {
    private Integer id;

    private Integer aid;

    private Integer did;

    private Integer uid;

    private Integer dataid;

    private String dname;
    //模拟量档案
    private List<AnalogDocBean> analogDocBeans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getDataid() {
        return dataid;
    }

    public void setDataid(Integer dataid) {
        this.dataid = dataid;
    }
}