package com.xl.ems.apigateway.model;

public class XlGroupanalogModel {
    private Integer analogid;

    private String groupid;

    private String analogname;

    private String groupname;

    private String sym;

    private String coe;

    private String sdt;

    private String edt;

    public Integer getAnalogid() {
        return analogid;
    }

    public void setAnalogid(Integer analogid) {
        this.analogid = analogid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid == null ? null : groupid.trim();
    }

    public String getAnalogname() {
        return analogname;
    }

    public void setAnalogname(String analogname) {
        this.analogname = analogname == null ? null : analogname.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym == null ? null : sym.trim();
    }

    public String getCoe() {
        return coe;
    }

    public void setCoe(String coe) {
        this.coe = coe == null ? null : coe.trim();
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt == null ? null : sdt.trim();
    }

    public String getEdt() {
        return edt;
    }

    public void setEdt(String edt) {
        this.edt = edt == null ? null : edt.trim();
    }
}