package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNArganEnergyService {
	
	//公共机构能源资源消费信息 addEnergy
	JSONObject arganEnergy_addEnergy(Map<String, Object> map);
	
	//公共机构数据中心机房能源消费信息 addIDC
	JSONObject arganEnergy_addIDC(Map<String, Object> map);
	
	//公共机构采暖能源资源消费信息 addWarm
	JSONObject arganEnergy_addWarm(Map<String, Object> map);
}
