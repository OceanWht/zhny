package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNGaugeConfigureService {
	//用能单位计量器具信息  add
	JSONObject gaugeConfigure_add(Map<String, Object> map);
	
	//用能单位计量器具信息  delete
	JSONObject gaugeConfigure_delete(Map<String, Object> map);
	
	//用能单位计量器具信息  update
	JSONObject gaugeConfigure_update(Map<String, Object> map);
}
