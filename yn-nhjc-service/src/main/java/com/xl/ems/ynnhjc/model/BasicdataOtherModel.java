package com.xl.ems.ynnhjc.model;

public class BasicdataOtherModel {
    private Integer id;

    private String itemindex;

    private String code;

    private String name;

    private String io1;

    private String io2;

    private String io3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemindex() {
        return itemindex;
    }

    public void setItemindex(String itemindex) {
        this.itemindex = itemindex == null ? null : itemindex.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIo1() {
        return io1;
    }

    public void setIo1(String io1) {
        this.io1 = io1 == null ? null : io1.trim();
    }

    public String getIo2() {
        return io2;
    }

    public void setIo2(String io2) {
        this.io2 = io2 == null ? null : io2.trim();
    }

    public String getIo3() {
        return io3;
    }

    public void setIo3(String io3) {
        this.io3 = io3 == null ? null : io3.trim();
    }
}