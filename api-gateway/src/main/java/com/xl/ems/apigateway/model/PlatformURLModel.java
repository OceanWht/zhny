package com.xl.ems.apigateway.model;

public class PlatformURLModel {
    private Integer id;

    private String enterprisecode;

    private String enterprisedatadownloadurl;

    private String enterprisedataurl;

    private String enterpriseinfodownloadurl;

    private String enterpriseinfourl;

    private String loadbasedataurl;

    private String organizationdataurl;

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

    public String getEnterprisedatadownloadurl() {
        return enterprisedatadownloadurl;
    }

    public void setEnterprisedatadownloadurl(String enterprisedatadownloadurl) {
        this.enterprisedatadownloadurl = enterprisedatadownloadurl == null ? null : enterprisedatadownloadurl.trim();
    }

    public String getEnterprisedataurl() {
        return enterprisedataurl;
    }

    public void setEnterprisedataurl(String enterprisedataurl) {
        this.enterprisedataurl = enterprisedataurl == null ? null : enterprisedataurl.trim();
    }

    public String getEnterpriseinfodownloadurl() {
        return enterpriseinfodownloadurl;
    }

    public void setEnterpriseinfodownloadurl(String enterpriseinfodownloadurl) {
        this.enterpriseinfodownloadurl = enterpriseinfodownloadurl == null ? null : enterpriseinfodownloadurl.trim();
    }

    public String getEnterpriseinfourl() {
        return enterpriseinfourl;
    }

    public void setEnterpriseinfourl(String enterpriseinfourl) {
        this.enterpriseinfourl = enterpriseinfourl == null ? null : enterpriseinfourl.trim();
    }

    public String getLoadbasedataurl() {
        return loadbasedataurl;
    }

    public void setLoadbasedataurl(String loadbasedataurl) {
        this.loadbasedataurl = loadbasedataurl == null ? null : loadbasedataurl.trim();
    }

    public String getOrganizationdataurl() {
        return organizationdataurl;
    }

    public void setOrganizationdataurl(String organizationdataurl) {
        this.organizationdataurl = organizationdataurl == null ? null : organizationdataurl.trim();
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