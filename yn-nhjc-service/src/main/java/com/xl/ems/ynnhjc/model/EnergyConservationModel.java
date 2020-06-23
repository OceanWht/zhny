package com.xl.ems.ynnhjc.model;

public class EnergyConservationModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String projectname;

    private String projecttype;

    private String improvemeasure;

    private String investmentamount;

    private String projecttimeline;

    private String energysavingamount;

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

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname == null ? "" : projectname.trim();
    }

    public String getProjecttype() {
        return projecttype;
    }

    public void setProjecttype(String projecttype) {
        this.projecttype = projecttype == null ? "" : projecttype.trim();
    }

    public String getImprovemeasure() {
        return improvemeasure;
    }

    public void setImprovemeasure(String improvemeasure) {
        this.improvemeasure = improvemeasure == null ? null : improvemeasure.trim();
    }

    public String getInvestmentamount() {
        return investmentamount;
    }

    public void setInvestmentamount(String investmentamount) {
        this.investmentamount = investmentamount == null ? null : investmentamount.trim();
    }

    public String getProjecttimeline() {
        return projecttimeline;
    }

    public void setProjecttimeline(String projecttimeline) {
        this.projecttimeline = projecttimeline == null ? null : projecttimeline.trim();
    }

    public String getEnergysavingamount() {
        return energysavingamount;
    }

    public void setEnergysavingamount(String energysavingamount) {
        this.energysavingamount = energysavingamount == null ? null : energysavingamount.trim();
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