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
import com.xl.ems.apigateway.service.ynservice.YNArganEnergyService;

@RestController
public class YNArganEnergyController {

	@Autowired
	YNArganEnergyService yNArganEnergyService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNArganEnergyController.class);
	
	@RequestMapping(value = "/arganEnergy/addEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addEnergy(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNArganEnergyController YNAddEnergyAdd begin...");
        JSONObject result = yNArganEnergyService.arganEnergy_addEnergy(requestMap);
        LOGGER.info("YNArganEnergyController YNAddEnergyAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/arganEnergy/addIDC",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addIDC(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNArganEnergyController YNAddIDCAdd begin...");
        JSONObject result = yNArganEnergyService.arganEnergy_addIDC(requestMap);
        LOGGER.info("YNArganEnergyController YNAddIDCAdd end OK...");
        return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/arganEnergy/addWarm",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addWarm(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNArganEnergyController YNAddWarmAdd begin...");
        JSONObject result = yNArganEnergyService.arganEnergy_addWarm(requestMap);
        LOGGER.info("YNArganEnergyController YNAddWarmAdd end OK...");
        return RestResponse.success(result);
	}
}
