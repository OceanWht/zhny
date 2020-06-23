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
import com.xl.ems.apigateway.service.ynservice.YNDataCollectConfigureService;

@RestController
public class YNDataCollectConfigureController {
	
	@Autowired
	YNDataCollectConfigureService yNDataCollectConfigureService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataCollectConfigureController.class);
	
	@RequestMapping(value = "/dataCollectConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DataCollectConfigure_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureAdd begin...");
        JSONObject result = yNDataCollectConfigureService.dataCollectConfigure_add(requestMap);
        LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/dataCollectConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DataCollectConfigure_update(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureUpdate begin...");
		JSONObject result = yNDataCollectConfigureService.dataCollectConfigure_update(requestMap);
		LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureUpdate end...");
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/dataCollectConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DataCollectConfigure_delete(@RequestBody Map<String, Object> requestMap){
		LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureDelete begin...");
		JSONObject result = yNDataCollectConfigureService.dataCollectConfigure_delete(requestMap);
		LOGGER.info("YNDataCollectConfigureController YNDataCollectConfigureDelete end...");
	    return RestResponse.success(result);
	}
}
