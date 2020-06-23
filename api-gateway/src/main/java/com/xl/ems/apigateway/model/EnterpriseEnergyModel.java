package com.xl.ems.apigateway.model;

import java.util.Date;

public class EnterpriseEnergyModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String datacode;

    private String datavalue;

    private String convertration;

    private String valid;

    private String scope;

    private String inputtype;

    private String stattype;

    private String statdate;

    private String uploaddate;

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

    public String getDatacode() {
        return datacode;
    }

    public void setDatacode(String datacode) {
        this.datacode = datacode == null ? null : datacode.trim();
    }

    public String getDatavalue() {
        return datavalue;
    }

    public void setDatavalue(String datavalue) {
        this.datavalue = datavalue == null ? null : datavalue.trim();
    }

    public String getConvertration() {
        return convertration;
    }

    public void setConvertration(String convertration) {
        this.convertration = convertration == null ? null : convertration.trim();
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid == null ? null : valid.trim();
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype == null ? null : inputtype.trim();
    }

    public String getStattype() {
        return stattype;
    }

    public void setStattype(String stattype) {
        this.stattype = stattype == null ? null : stattype.trim();
    }

    public String getStatdate() {
        return statdate;
    }

    public void setStatdate(String statdate) {
        this.statdate = statdate;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
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