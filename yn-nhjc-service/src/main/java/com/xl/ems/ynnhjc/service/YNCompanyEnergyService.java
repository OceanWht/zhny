package com.xl.ems.ynnhjc.service;


import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.*;

import java.util.List;

public interface YNCompanyEnergyService {

    RestResponse<JSONObject> sysEnergyAdd(SysEnergyModel energyModel);

    RestResponse<JSONObject> sysEnergyUpdate(SysEnergyModel energyModel);

    RestResponse<JSONObject> sysEnergyDelete(SysEnergyModel energyModel);

    RestResponse<List<SysEnergyModel>> sysEnergyList(String enterpriseCode);

    RestResponse<JSONObject> energyManagerAdd(EnergyManagerModel energyModel);

    RestResponse<JSONObject> energyManagerUpdate(EnergyManagerModel energyModel);

    RestResponse<JSONObject> energyManagerDelete(EnergyManagerModel energyModel);

    RestResponse<List<EnergyManagerModel>> energyManagerList(String enterpriseCode);

    RestResponse<JSONObject> energyMonitorAdd(EnergyMonitorModel energyModel);

    RestResponse<JSONObject> energyMonitorUpdate(EnergyMonitorModel energyModel);

    RestResponse<JSONObject> energyMonitorDelete(EnergyMonitorModel energyModel);

    RestResponse<List<EnergyMonitorModel>> energyMonitorList(String enterpriseCode);

    RestResponse<JSONObject> calculaterAdd(CalculaterModel energyModel);

    RestResponse<JSONObject> calculaterUpdate(CalculaterModel energyModel);

    RestResponse<JSONObject> calculaterDelete(CalculaterModel energyModel);

    RestResponse<List<CalculaterModel>> calculaterList(String enterpriseCode);

    RestResponse<JSONObject> energyCertificationAdd(EnergyCertificationModel energyModel);

    RestResponse<JSONObject> energyCertificationUpdate(EnergyCertificationModel energyModel);

    RestResponse<JSONObject> energyCertificationDelete(EnergyCertificationModel energyModel);

    RestResponse<List<EnergyCertificationModel>> energyCertificationList(String enterpriseCode);
}
