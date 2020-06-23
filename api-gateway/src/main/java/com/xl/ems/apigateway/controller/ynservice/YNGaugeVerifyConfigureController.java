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
import com.xl.ems.apigateway.service.ynservice.YNGaugeVerifyConfigureService;

@RestController
public class YNGaugeVerifyConfigureController {
	
	@Autowired
	YNGaugeVerifyConfigureService yNGaugeVerifyConfigureService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeVerifyConfigureController.class);
	
	@RequestMapping(value = "/gaugeVerifyConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeVerifyConfigure_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureAdd begin...");
        JSONObject result = yNGaugeVerifyConfigureService.gaugeVerifyConfigure_add(requestMap);
        LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeVerifyConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeVerifyConfigure_update(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureUpdate begin...");
		JSONObject result = yNGaugeVerifyConfigureService.gaugeVerifyConfigure_update(requestMap);
		LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureUpdate end...");
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeVerifyConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> GaugeVerifyConfigure_delete(@RequestBody Map<String, Object> requestMap){
		LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureDelete begin...");
		JSONObject result = yNGaugeVerifyConfigureService.gaugeVerifyConfigure_delete(requestMap);
		LOGGER.info("YNGaugeVerifyConfigureController YNGaugeVerifyConfigureDelete end...");
	    return RestResponse.success(result);
	}
}
