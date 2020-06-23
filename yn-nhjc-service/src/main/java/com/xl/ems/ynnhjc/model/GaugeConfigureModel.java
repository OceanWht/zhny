package com.xl.ems.ynnhjc.model;

import java.util.Date;

public class GaugeConfigureModel {
    private Integer id;

    private String regversion;

    private String dicversion;

    private String dataindex;

    private String remark;

    private String enterprisecode;

    private String metername;

    private String metertype;

    private String meterlevel;
    
    private String paramsType;

    private String params;

    private String datacode;

    private String reportarithmetic;

    private String reportratio;

    private String manufacturer;

    private String model;

    private String precisionlevel;

    private String ranges;

    private String manageno;

    private String alignstate;

    private String aligncycle;

    private Date lastaligndate;

    private Date nextaligndate;

    private String alignorg;

    private String unalignreason;

    private String location;

    private String installer;

    private Date installtime;

    private String linksys;

    private String currentstate;

    private Date statechangetime;

    private String serno;

    private String barcode;

    private String replacedserno;

    private String replacedbarcode;

    private String installserno;

    private String installmanageno;

    private String installbarcode;

    private String verifyorg;

    private Date verifytime;

    private String backupField1;

    private String backupField2;

    private String backupField3;

    
    public String getParamsType() {
		return paramsType;
	}

	public void setParamsType(String paramsType) {
		this.paramsType = paramsType;
	}

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

    public String getMetername() {
        return metername;
    }

    public void setMetername(String metername) {
        this.metername = metername == null ? null : metername.trim();
    }

    public String getMetertype() {
        return metertype;
    }

    public void setMetertype(String metertype) {
        this.metertype = metertype == null ? null : metertype.trim();
    }

    public String getMeterlevel() {
        return meterlevel;
    }

    public void setMeterlevel(String meterlevel) {
        this.meterlevel = meterlevel == null ? null : meterlevel.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public String getDatacode() {
        return datacode;
    }

    public void setDatacode(String datacode) {
        this.datacode = datacode == null ? null : datacode.trim();
    }

    public String getReportarithmetic() {
        return reportarithmetic;
    }

    public void setReportarithmetic(String reportarithmetic) {
        this.reportarithmetic = reportarithmetic == null ? null : reportarithmetic.trim();
    }

    public String getReportratio() {
        return reportratio;
    }

    public void setReportratio(String reportratio) {
        this.reportratio = reportratio == null ? null : reportratio.trim();
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getPrecisionlevel() {
        return precisionlevel;
    }

    public void setPrecisionlevel(String precisionlevel) {
        this.precisionlevel = precisionlevel == null ? null : precisionlevel.trim();
    }

    public String getRanges() {
        return ranges;
    }

    public void setRanges(String ranges) {
        this.ranges = ranges == null ? null : ranges.trim();
    }

    public String getManageno() {
        return manageno;
    }

    public void setManageno(String manageno) {
        this.manageno = manageno == null ? null : manageno.trim();
    }

    public String getAlignstate() {
        return alignstate;
    }

    public void setAlignstate(String alignstate) {
        this.alignstate = alignstate == null ? null : alignstate.trim();
    }

    public String getAligncycle() {
        return aligncycle;
    }

    public void setAligncycle(String aligncycle) {
        this.aligncycle = aligncycle == null ? null : aligncycle.trim();
    }

    public Date getLastaligndate() {
        return lastaligndate;
    }

    public void setLastaligndate(Date lastaligndate) {
        this.lastaligndate = lastaligndate;
    }

    public Date getNextaligndate() {
        return nextaligndate;
    }

    public void setNextaligndate(Date nextaligndate) {
        this.nextaligndate = nextaligndate;
    }

    public String getAlignorg() {
        return alignorg;
    }

    public void setAlignorg(String alignorg) {
        this.alignorg = alignorg == null ? null : alignorg.trim();
    }

    public String getUnalignreason() {
        return unalignreason;
    }

    public void setUnalignreason(String unalignreason) {
        this.unalignreason = unalignreason == null ? null : unalignreason.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getInstaller() {
        return installer;
    }

    public void setInstaller(String installer) {
        this.installer = installer == null ? null : installer.trim();
    }

    public Date getInstalltime() {
        return installtime;
    }

    public void setInstalltime(Date installtime) {
        this.installtime = installtime;
    }

    public String getLinksys() {
        return linksys;
    }

    public void setLinksys(String linksys) {
        this.linksys = linksys == null ? null : linksys.trim();
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate == null ? null : currentstate.trim();
    }

    public Date getStatechangetime() {
        return statechangetime;
    }

    public void setStatechangetime(Date statechangetime) {
        this.statechangetime = statechangetime;
    }

    public String getSerno() {
        return serno;
    }

    public void setSerno(String serno) {
        this.serno = serno == null ? null : serno.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getReplacedserno() {
        return replacedserno;
    }

    public void setReplacedserno(String replacedserno) {
        this.replacedserno = replacedserno == null ? null : replacedserno.trim();
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

    public String getVerifyorg() {
        return verifyorg;
    }

    public void setVerifyorg(String verifyorg) {
        this.verifyorg = verifyorg == null ? null : verifyorg.trim();
    }

    public Date getVerifytime() {
        return verifytime;
    }

    public void setVerifytime(Date verifytime) {
        this.verifytime = verifytime;
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