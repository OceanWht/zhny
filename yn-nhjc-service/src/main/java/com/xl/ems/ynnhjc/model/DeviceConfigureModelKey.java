package com.xl.ems.ynnhjc.model;

public class DeviceConfigureModelKey {
    private Integer id;

    private String energycode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnergycode() {
        return energycode;
    }

    public void setEnergycode(String energycode) {
        this.energycode = energycode == null ? null : energycode.trim();
    }
}