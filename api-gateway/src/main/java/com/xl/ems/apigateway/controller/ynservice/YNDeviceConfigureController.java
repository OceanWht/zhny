package com.xl.ems.apigateway.controller.ynservice;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNDeviceConfigureService;

@RestController
public class YNDeviceConfigureController {
	
	@Autowired
	YNDeviceConfigureService yNDeviceConfigureService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDeviceConfigureController.class);
	
	@RequestMapping(value = "/deviceConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DeviceConfigure_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNDeviceConfigureController YNDeviceConfigureAdd begin...");
        JSONObject result = yNDeviceConfigureService.DeviceConfigure_add(requestMap);
        LOGGER.info("YNDeviceConfigureController YNDeviceConfigureAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/deviceConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DeviceConfigure_update(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_Update begin...");
		JSONObject result = yNDeviceConfigureService.DeviceConfigure_update(requestMap);
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_Update end...");
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/deviceConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DeviceConfigure_delete(@RequestBody Map<String, Object> requestMap){
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_Delete begin...");
		JSONObject result = yNDeviceConfigureService.DeviceConfigure_delete(requestMap);
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_Delete end...");
	    return RestResponse.success(result);
	}
}
