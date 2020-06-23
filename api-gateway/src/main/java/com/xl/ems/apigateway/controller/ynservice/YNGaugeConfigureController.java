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
import com.xl.ems.apigateway.service.ynservice.YNGaugeConfigureService;

@RestController
public class YNGaugeConfigureController {
	
	@Autowired
	YNGaugeConfigureService yNGaugeConfigureService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeConfigureController.class);
	
	@RequestMapping(value = "/gaugeConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeConfigure_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeConfigureController YNGaugeConfigureAdd begin...");
        JSONObject result = yNGaugeConfigureService.gaugeConfigure_add(requestMap);
        LOGGER.info("YNGaugeConfigureController YNGaugeConfigureAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeConfigure_update(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeConfigureController YNGaugeConfigureUpdate begin...");
		JSONObject result = yNGaugeConfigureService.gaugeConfigure_update(requestMap);
		LOGGER.info("YNGaugeConfigureController YNGaugeConfigureUpdate end...");
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeConfigure_delete(@RequestBody Map<String, Object> requestMap){
		LOGGER.info("YNGaugeConfigureController YNGaugeConfigureDelete begin...");
		JSONObject result = yNGaugeConfigureService.gaugeConfigure_delete(requestMap);
		LOGGER.info("YNGaugeConfigureController YNGaugeConfigureDelete end...");
	    return RestResponse.success(result);
	}
}
