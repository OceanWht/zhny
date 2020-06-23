package com.xl.ems.ynnhjc.model;

public class SysEnergyModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String enterprisecode;

    private String dataindex;

    private String remark;

    private String energyoffice;

    private String energyofficial;

    private String energyofficialposition;

    private String phone;

    private String fax;

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

    public String getEnterprisecode() {
        return enterprisecode;
    }

    public void setEnterprisecode(String enterprisecode) {
        this.enterprisecode = enterprisecode == null ? null : enterprisecode.trim();
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

    public String getEnergyoffice() {
        return energyoffice;
    }

    public void setEnergyoffice(String energyoffice) {
        this.energyoffice = energyoffice == null ? null : energyoffice.trim();
    }

    public String getEnergyofficial() {
        return energyofficial;
    }

    public void setEnergyofficial(String energyofficial) {
        this.energyofficial = energyofficial == null ? null : energyofficial.trim();
    }

    public String getEnergyofficialposition() {
        return energyofficialposition;
    }

    public void setEnergyofficialposition(String energyofficialposition) {
        this.energyofficialposition = energyofficialposition == null ? null : energyofficialposition.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
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