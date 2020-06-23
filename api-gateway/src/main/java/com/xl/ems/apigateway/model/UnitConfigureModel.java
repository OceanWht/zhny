package com.xl.ems.apigateway.model;

import java.util.Date;

public class UnitConfigureModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String unitcode;

    private String unitname;

    private String processcode;

    private Date commdate;

    private String designedcapacity;

    private String backupField1;

    private String backupField2;

    private String backupField3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegversion() {
        return regversion;
    }

    public void setRegversion(String regversion) {
        this.regversion = regversion == null ? null : regversion.trim();
    }

    public String getDicversion() {
        return dicversion;
    }

    public void setDicversion(String dicversion) {
        this.dicversion = dicversion == null ? null : dicversion.trim();
    }

    public String getDataindex() {
        return dataindex;
    }

    public void setDataindex(String dataindex) {
        this.dataindex = dataindex == null ? null : dataindex.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode == null ? null : enterprisecode.trim();
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode == null ? null : unitcode.trim();
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname == null ? null : unitname.trim();
    }

    public String getProcesscode() {
        return processcode;
    }

    public void setProcesscode(String processcode) {
        this.processcode = processcode == null ? null : processcode.trim();
    }

    public Date getCommdate() {
        return commdate;
    }

    public void setCommdate(Date commdate) {
        this.commdate = commdate;
    }

    public String getDesignedcapacity() {
        return designedcapacity;
    }

    public void setDesignedcapacity(String designedcapacity) {
        this.designedcapacity = designedcapacity == null ? null : designedcapacity.trim();
    }

    public String getBackupField1() {
        return backupField1;
    }

    public void setBackupField1(String backupField1) {
        this.backupField1 = backupField1 == null ? null : backupField1.trim();
    }

    public String getBackupField2() {
        return backupField2;
    }

    public void setBackupField2(String backupField2) {
        this.backupField2 = backupField2 == null ? null : backupField2.trim();
    }

    public String getBackupField3() {
        return backupField3;
    }

    public void setBackupField3(String backupField3) {
        this.backupField3 = backupField3 == null ? null : backupField3.trim();
    }
}