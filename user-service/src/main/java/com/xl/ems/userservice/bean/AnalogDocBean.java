package com.xl.ems.userservice.bean;

import java.util.List;

/**
 * 模拟量档案
 */
public class AnalogDocBean {
    private String aid;
    private String aname;
    private String dataunit;
    private String dataperiod;

    //模拟量曲线数据
    private List<AnalogTSCurveDataBean> analogTSCurveDataBeans;

    //模拟量日数据
    private List<AnalogDataBean> analogDayDataBeans;

    //模拟量月数据
    private List<AnalogMonthDataBean> analogMonthDataBeans;

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

    public List<AnalogTSCurveDataBean> getAnalogTSCurveDataBeans() {
        return analogTSCurveDataBeans;
    }

    public void setAnalogTSCurveDataBeans(List<AnalogTSCurveDataBean> analogTSCurveDataBeans) {
        this.analogTSCurveDataBeans = analogTSCurveDataBeans;
    }

    public List<AnalogDataBean> getAnalogDayDataBeans() {
        return analogDayDataBeans;
    }

    public void setAnalogDayDataBeans(List<AnalogDataBean> analogDayDataBeans) {
        this.analogDayDataBeans = analogDayDataBeans;
    }

    public List<AnalogMonthDataBean> getAnalogMonthDataBeans() {
        return analogMonthDataBeans;
    }

    public void setAnalogMonthDataBeans(List<AnalogMonthDataBean> analogMonthDataBeans) {
        this.analogMonthDataBeans = analogMonthDataBeans;
    }
}
