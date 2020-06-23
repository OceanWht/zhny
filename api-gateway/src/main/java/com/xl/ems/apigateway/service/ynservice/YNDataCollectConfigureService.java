package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNDataCollectConfigureService {
	//用能单位数据采集配置项信息  add
	JSONObject dataCollectConfigure_add(Map<String, Object> map);
	
	//用能单位数据采集配置项信息  delete
	JSONObject dataCollectConfigure_delete(Map<String, Object> map);
	
	//用能单位数据采集配置项信息  update
	JSONObject dataCollectConfigure_update(Map<String, Object> map);
}
