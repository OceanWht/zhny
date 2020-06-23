package com.xl.ems.ynnhjc.service.task;

public interface YNEnterpriseEnergyTaskService {
    void enterPrinseEnergyTaskManual();

    /**
     * 实时数据上报
     */
    void enterPrinseEnergyTaskAutomatic_15(String date);

    void enterPrinseEnergyTaskAutomatic_day();

    void enterPrinseEnergyTaskAutomatic_month();

    void enterPrinseEnergyTaskAutomatic_year();

    void enterPrinseEnergyTaskAutomatic_day_backup();

    void enterPrinseEnergyTaskAutomatic_month_backup();

    void enterPrinseEnergyTaskAutomatic_year_backup();
}
