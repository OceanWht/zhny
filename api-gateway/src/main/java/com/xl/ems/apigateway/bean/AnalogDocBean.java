package com.xl.ems.apigateway.bean;

/**
 * 模拟量档案
 */
public class AnalogDocBean {
    private String aid;
    private String aname;
    private String dataunit;
    private String dataperiod;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getDataunit() {
        return dataunit;
    }

    public void setDataunit(String dataunit) {
        this.dataunit = dataunit;
    }

    public String getDataperiod() {
        return dataperiod;
    }

    public void setDataperiod(String dataperiod) {
        this.dataperiod = dataperiod;
    }
}
