package com.xl.ems.apigateway.model;

public class CalculaterModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String calculatername;

    private String phone;

    private String dept;

    private String job;

    private String technicalpost;

    private String certificatename;

    private String certificateno;

    private String backupField1;

    private String backupField2;

    private String backupField3;

    private String certificate;

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

    public String getCalculatername() {
        return calculatername;
    }

    public void setCalculatername(String calculatername) {
        this.calculatername = calculatername == null ? null : calculatername.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept == null ? null : dept.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getTechnicalpost() {
        return technicalpost;
    }

    public void setTechnicalpost(String technicalpost) {
        this.technicalpost = technicalpost == null ? null : technicalpost.trim();
    }

    public String getCertificatename() {
        return certificatename;
    }

    public void setCertificatename(String certificatename) {
        this.certificatename = certificatename == null ? null : certificatename.trim();
    }

    public String getCertificateno() {
        return certificateno;
    }

    public void setCertificateno(String certificateno) {
        this.certificateno = certificateno == null ? null : certificateno.trim();
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

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate == null ? null : certificate.trim();
    }
}