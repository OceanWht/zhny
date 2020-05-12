package com.xl.ems.apigateway.bean;

/**
 * 模拟量曲线数据
 */
public class AnalogTSCurveDataBean {

    //模拟量ID
    private String did;

    //数据时间
    private String dt;

    //数据
    private String aed;
    //数据来源
    private String ds;
    //修改时间
    private String udt;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getAed() {
        return aed;
    }

    public void setAed(String aed) {
        this.aed = aed;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public String getUdt() {
        return udt;
    }

    public void setUdt(String udt) {
        this.udt = udt;
    }
}
