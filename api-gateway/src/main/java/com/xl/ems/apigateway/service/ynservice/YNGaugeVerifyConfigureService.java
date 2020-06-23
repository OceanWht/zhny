package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface YNGaugeVerifyConfigureService {
	//用能单位计量器具检定/校准记录 add
	JSONObject gaugeVerifyConfigure_add(Map<String, Object> map);
	
	//用能单位计量器具检定/校准记录 delete
	JSONObject gaugeVerifyConfigure_delete(Map<String, Object> map);
	
	//用能单位计量器具检定/校准记录 update
	JSONObject gaugeVerifyConfigure_update(Map<String, Object> map);
}
