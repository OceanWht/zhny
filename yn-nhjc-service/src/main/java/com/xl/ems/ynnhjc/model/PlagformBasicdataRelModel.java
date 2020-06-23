package com.xl.ems.ynnhjc.model;

public class PlagformBasicdataRelModel {
    private Integer itemindex;

    private String basicdataname;

    private String remark;

    private String backFiled1;

    private String backFiled2;

    private String backFiled3;

    public Integer getItemindex() {
        return itemindex;
    }

    public void setItemindex(Integer itemindex) {
        this.itemindex = itemindex;
    }

    public String getBasicdataname() {
        return basicdataname;
    }

    public void setBasicdataname(String basicdataname) {
        this.basicdataname = basicdataname == null ? null : basicdataname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBackFiled1() {
        return backFiled1;
    }

    public void setBackFiled1(String backFiled1) {
        this.backFiled1 = backFiled1 == null ? null : backFiled1.trim();
    }

    public String getBackFiled2() {
        return backFiled2;
    }

    public void setBackFiled2(String backFiled2) {
        this.backFiled2 = backFiled2 == null ? null : backFiled2.trim();
    }

    public String getBackFiled3() {
        return backFiled3;
    }

    public void setBackFiled3(String backFiled3) {
        this.backFiled3 = backFiled3 == null ? null : backFiled3.trim();
    }
}