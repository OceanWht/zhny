package com.xl.ems.apigateway.service.ynservice;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;

public interface YNDeviceConfigureService {
	
	//用能单位重点耗能设备信息 add
	JSONObject DeviceConfigure_add(Map<String, Object> map);
	
	//用能单位重点耗能设备信息 delete
	JSONObject DeviceConfigure_delete(Map<String, Object> map);
	
	//用能单位重点耗能设备信息 update
	JSONObject DeviceConfigure_update(Map<String, Object> map);
}
