package com.xl.ems.userservice.model;

public class XlUnitCalcGroupModel extends XlUnitCalcGroupModelKey {
    private Integer uid;

    private String uname;

    private String ioo;

    private String groupname;

    private String dataid;

    private String dataname;

    private String datacode;

    private String duname;

    private String ducode;

    private Integer iscalculate;

    private Integer isautocollect;

    private String i1;

    private String i2;

    private String i3;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    public String getIoo() {
        return ioo;
    }

    public void setIoo(String ioo) {
        this.ioo = ioo == null ? null : ioo.trim();
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname == null ? null : groupname.trim();
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid == null ? null : dataid.trim();
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname == null ? null : dataname.trim();
    }

    public String getDatacode() {
        return datacode;
    }

    public void setDatacode(String datacode) {
        this.datacode = datacode == null ? null : datacode.trim();
    }

    public String getDuname() {
        return duname;
    }

    public void setDuname(String duname) {
        this.duname = duname == null ? null : duname.trim();
    }

    public String getDucode() {
        return ducode;
    }

    public void setDucode(String ducode) {
        this.ducode = ducode == null ? null : ducode.trim();
    }

    public Integer getIscalculate() {
        return iscalculate;
    }

    public void setIscalculate(Integer iscalculate) {
        this.iscalculate = iscalculate;
    }

    public Integer getIsautocollect() {
        return isautocollect;
    }

    public void setIsautocollect(Integer isautocollect) {
        this.isautocollect = isautocollect;
    }

    public String getI1() {
        return i1;
    }

    public void setI1(String i1) {
        this.i1 = i1 == null ? null : i1.trim();
    }

    public String getI2() {
        return i2;
    }

    public void setI2(String i2) {
        this.i2 = i2 == null ? null : i2.trim();
    }

    public String getI3() {
        return i3;
    }

    public void setI3(String i3) {
        this.i3 = i3 == null ? null : i3.trim();
    }
}