package com.xl.ems.apigateway.model;

import java.util.Date;

public class GaugeReplaceConfigureModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String replacedserno;

    private String replacedmanageno;

    private String replacedbarcode;

    private String installserno;

    private String installmanageno;

    private String installbarcode;

    private Date installtime;

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

    public String getReplacedserno() {
        return replacedserno;
    }

    public void setReplacedserno(String replacedserno) {
        this.replacedserno = replacedserno == null ? null : replacedserno.trim();
    }

    public String getReplacedmanageno() {
        return replacedmanageno;
    }

    public void setReplacedmanageno(String replacedmanageno) {
        this.replacedmanageno = replacedmanageno == null ? null : replacedmanageno.trim();
    }

    public String getReplacedbarcode() {
        return replacedbarcode;
    }

    public void setReplacedbarcode(String replacedbarcode) {
        this.replacedbarcode = replacedbarcode == null ? null : replacedbarcode.trim();
    }

    public String getInstallserno() {
        return installserno;
    }

    public void setInstallserno(String installserno) {
        this.installserno = installserno == null ? null : installserno.trim();
    }

    public String getInstallmanageno() {
        return installmanageno;
    }

    public void setInstallmanageno(String installmanageno) {
        this.installmanageno = installmanageno == null ? null : installmanageno.trim();
    }

    public String getInstallbarcode() {
        return installbarcode;
    }

    public void setInstallbarcode(String installbarcode) {
        this.installbarcode = installbarcode == null ? null : installbarcode.trim();
    }

    public Date getInstalltime() {
        return installtime;
    }

    public void setInstalltime(Date installtime) {
        this.installtime = installtime;
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