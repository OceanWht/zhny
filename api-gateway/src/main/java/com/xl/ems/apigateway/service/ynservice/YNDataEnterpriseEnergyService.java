package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNDataEnterpriseEnergyService {
	//用能单位能源资源计量采集数据 
	JSONObject dataEnterpriseEnergy(Map<String, Object> map);
}
