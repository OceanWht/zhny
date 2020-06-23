package com.xl.ems.ynnhjc.model;


public class SysApplicationModelWithBLOBs extends SysApplicationModel {
    private String license;

    private String organization;

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization == null ? null : organization.trim();
    }
}