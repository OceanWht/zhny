package com.xl.ems.apigateway.bean;

/**
 * @ClassName AnalogInfos
 * @Description TODO
 * @Author wht
 * @Date 17:58
 **/
public class AnalogInfoBean {
    private long ParentId;
    private String ParentName;
    private long AnalogId;
    private String AnalogName;
    private String AnalogType;
    private int DataUnitId;
    private String DataUnitName;
    private int DataPeriod;
    private String StartTime;
    private String EndTime;
    private int Symbol;
    private int Coeff;
    public void setParentId(long ParentId) {
        this.ParentId = ParentId;
    }
    public long getParentId() {
        return ParentId;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }
    public String getParentName() {
        return ParentName;
    }

    public void setAnalogId(long AnalogId) {
        this.AnalogId = AnalogId;
    }
    public long getAnalogId() {
        return AnalogId;
    }

    public void setAnalogName(String AnalogName) {
        this.AnalogName = AnalogName;
    }
    public String getAnalogName() {
        return AnalogName;
    }

    public void setAnalogType(String AnalogType) {
        this.AnalogType = AnalogType;
    }
    public String getAnalogType() {
        return AnalogType;
    }

    public void setDataUnitId(int DataUnitId) {
        this.DataUnitId = DataUnitId;
    }
    public int getDataUnitId() {
        return DataUnitId;
    }

    public void setDataUnitName(String DataUnitName) {
        this.DataUnitName = DataUnitName;
    }
    public String getDataUnitName() {
        return DataUnitName;
    }

    public void setDataPeriod(int DataPeriod) {
        this.DataPeriod = DataPeriod;
    }
    public int getDataPeriod() {
        return DataPeriod;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }
    public String getStartTime() {
        return StartTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }
    public String getEndTime() {
        return EndTime;
    }

    public void setSymbol(int Symbol) {
        this.Symbol = Symbol;
    }
    public int getSymbol() {
        return Symbol;
    }

    public void setCoeff(int Coeff) {
        this.Coeff = Coeff;
    }
    public int getCoeff() {
        return Coeff;
    }
}
