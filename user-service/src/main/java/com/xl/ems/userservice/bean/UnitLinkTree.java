package com.xl.ems.userservice.bean;

import java.util.List;

public class UnitLinkTree {

    //节点标题
    private String title;

    //节点ID 对应单元ID
    private String id;

    //能源类型
    private String dataid;

    //父节点
    private String parentId;

    //子节点
    private List<UnitLinkTree> children;

    //单元下得设备
    private List<MeterArchiveBean> dievices;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public String getParentid() {
        return parentId;
    }

    public void setParentid(String parentId) {
        this.parentId = parentId;
    }

    public List<UnitLinkTree> getChildren() {
        return children;
    }

    public void setChildren(List<UnitLinkTree> children) {
        this.children = children;
    }

    public List<MeterArchiveBean> getDievices() {
        return dievices;
    }

    public void setDievices(List<MeterArchiveBean> dievices) {
        this.dievices = dievices;
    }
}
