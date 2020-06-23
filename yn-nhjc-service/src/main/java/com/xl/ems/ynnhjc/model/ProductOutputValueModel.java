package com.xl.ems.ynnhjc.model;

public class ProductOutputValueModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String countyear;

    private String outputvalue;

    private String addedvalue;

    private String salesincome;

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

    public String getCountyear() {
        return countyear;
    }

    public void setCountyear(String countyear) {
        this.countyear = countyear == null ? null : countyear.trim();
    }

    public String getOutputvalue() {
        return outputvalue;
    }

    public void setOutputvalue(String outputvalue) {
        this.outputvalue = outputvalue == null ? null : outputvalue.trim();
    }

    public String getAddedvalue() {
        return addedvalue;
    }

    public void setAddedvalue(String addedvalue) {
        this.addedvalue = addedvalue == null ? null : addedvalue.trim();
    }

    public String getSalesincome() {
        return salesincome;
    }

    public void setSalesincome(String salesincome) {
        this.salesincome = salesincome == null ? null : salesincome.trim();
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