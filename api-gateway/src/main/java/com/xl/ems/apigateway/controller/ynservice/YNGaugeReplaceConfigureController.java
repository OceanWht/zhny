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
import com.xl.ems.apigateway.service.ynservice.YNGaugeReplaceConfigureService;

@RestController
public class YNGaugeReplaceConfigureController {
	
	@Autowired
	YNGaugeReplaceConfigureService yNGaugeReplaceConfigureService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeReplaceConfigureController.class);
	
	@RequestMapping(value = "/gaugeReplaceConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeReplaceConfigure_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureAdd begin...");
        JSONObject result = yNGaugeReplaceConfigureService.gaugeReplaceConfigure_add(requestMap);
        LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeReplaceConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeReplaceConfigure_update(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureUpdate begin...");
		JSONObject result = yNGaugeReplaceConfigureService.gaugeReplaceConfigure_update(requestMap);
		LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureUpdate end...");
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeReplaceConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeReplaceConfigure_delete(@RequestBody Map<String, Object> requestMap){
		LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureDelete begin...");
		JSONObject result = yNGaugeReplaceConfigureService.gaugeReplaceConfigure_delete(requestMap);
		LOGGER.info("YNGaugeReplaceConfigureController YNGaugeReplaceConfigureDelete end...");
	    return RestResponse.success(result);
	}
}
