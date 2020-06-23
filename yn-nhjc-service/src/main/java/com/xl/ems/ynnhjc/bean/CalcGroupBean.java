package com.xl.ems.ynnhjc.bean;

import java.util.List;

/**
 * @ClassName CalcGroupBean
 * @Description TODO
 * @Author wht
 * @Date 18:00
 **/
public class CalcGroupBean {
    private long uId;
    private String uName;
    private int InputOutput;
    private int GroupId;
    private String GroupName;
    private int DataCodeId;
    private String DataCodeName;
    private String DataCode;
    private String DuName;
    private String DuCode;
    private int GroupPeriod;
    private int GroupCalcStyle;
    private List<AnalogInfoBean> AnalogInfos;

    public List<AnalogInfoBean> getAnalogInfos() {
        return AnalogInfos;
    }

    public void setAnalogInfos(List<AnalogInfoBean> analogInfos) {
        AnalogInfos = analogInfos;
    }

    public void setUId(long uId) {
        this.uId = uId;
    }
    public long getUId() {
        return uId;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }
    public String getUName() {
        return uName;
    }

    public void setInputOutput(int InputOutput) {
        this.InputOutput = InputOutput;
    }
    public int getInputOutput() {
        return InputOutput;
    }

    public void setGroupId(int GroupId) {
        this.GroupId = GroupId;
    }
    public int getGroupId() {
        return GroupId;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }
    public String getGroupName() {
        return GroupName;
    }

    public void setDataCodeId(int DataCodeId) {
        this.DataCodeId = DataCodeId;
    }
    public int getDataCodeId() {
        return DataCodeId;
    }

    public void setDataCodeName(String DataCodeName) {
        this.DataCodeName = DataCodeName;
    }
    public String getDataCodeName() {
        return DataCodeName;
    }

    public void setDataCode(String DataCode) {
        this.DataCode = DataCode;
    }
    public String getDataCode() {
        return DataCode;
    }

    public void setDuName(String DuName) {
        this.DuName = DuName;
    }
    public String getDuName() {
        return DuName;
    }

    public void setDuCode(String DuCode) {
        this.DuCode = DuCode;
    }
    public String getDuCode() {
        return DuCode;
    }

    public void setGroupPeriod(int GroupPeriod) {
        this.GroupPeriod = GroupPeriod;
    }
    public int getGroupPeriod() {
        return GroupPeriod;
    }

    public void setGroupCalcStyle(int GroupCalcStyle) {
        this.GroupCalcStyle = GroupCalcStyle;
    }
    public int getGroupCalcStyle() {
        return GroupCalcStyle;
    }
}
