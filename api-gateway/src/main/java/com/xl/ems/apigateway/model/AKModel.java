package com.xl.ems.apigateway.model;

public class AKModel {
    private Integer id;

    private String enterprisecode;

    private String password;

    private String akexpiresin;

    private String akvalidat;

    private String enterpriseuploadtime;

    private String platformurl;

    private String token;

    private String backupField1;

    private String backupField2;

    private String backupField3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode == null ? null : enterprisecode.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getAkexpiresin() {
        return akexpiresin;
    }

    public void setAkexpiresin(String akexpiresin) {
        this.akexpiresin = akexpiresin == null ? null : akexpiresin.trim();
    }

    public String getAkvalidat() {
        return akvalidat;
    }

    public void setAkvalidat(String akvalidat) {
        this.akvalidat = akvalidat == null ? null : akvalidat.trim();
    }

    public String getEnterpriseuploadtime() {
        return enterpriseuploadtime;
    }

    public void setEnterpriseuploadtime(String enterpriseuploadtime) {
        this.enterpriseuploadtime = enterpriseuploadtime == null ? null : enterpriseuploadtime.trim();
    }

    public String getPlatformurl() {
        return platformurl;
    }

    public void setPlatformurl(String platformurl) {
        this.platformurl = platformurl == null ? null : platformurl.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
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