package com.xl.ems.userservice.bean;

import com.xl.ems.userservice.model.XlGroupanalogModel;

import java.util.List;

/**
 * 设备档案
 */
public class MeterArchiveBean {

    private String did;

    private String dt;

    private String name;

    private String add;

    private String pid;

    private String pname;

    //单元计算组中配置得模拟量ID，通常是累计量ID，用于区分能源
    private List<XlGroupanalogModel> groupAnalog;

    //模拟量档案
    private List<AnalogDocBean> analogDocBeanList;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<AnalogDocBean> getAnalogDocBeanList() {
        return analogDocBeanList;
    }

    public void setAnalogDocBeanList(List<AnalogDocBean> analogDocBeanList) {
        this.analogDocBeanList = analogDocBeanList;
    }

    public List<XlGroupanalogModel> getGroupAnalog() {
        return groupAnalog;
    }

    public void setGroupAnalog(List<XlGroupanalogModel> groupAnalog) {
        this.groupAnalog = groupAnalog;
    }
}
