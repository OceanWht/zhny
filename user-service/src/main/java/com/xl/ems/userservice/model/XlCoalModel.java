package com.xl.ems.userservice.model;

public class XlCoalModel extends XlCoalModelKey {
    private String radio;

    private String iscalculate;

    public String getRadio() {
        return radio;
    }

    public void setRadio(String radio) {
        this.radio = radio == null ? null : radio.trim();
    }

    public String getIscalculate() {
        return iscalculate;
    }

    public void setIscalculate(String iscalculate) {
        this.iscalculate = iscalculate == null ? null : iscalculate.trim();
    }
}