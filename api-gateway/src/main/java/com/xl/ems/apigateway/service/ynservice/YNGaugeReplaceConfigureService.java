package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNGaugeReplaceConfigureService {
	//用能单位计量器具更换记录 add
	JSONObject gaugeReplaceConfigure_add(Map<String, Object> map);
	
	//用能单位计量器具更换记录 delete
	JSONObject gaugeReplaceConfigure_delete(Map<String, Object> map);
	
	//用能单位计量器具更换记录 update
	JSONObject gaugeReplaceConfigure_update(Map<String, Object> map);
}
