package com.xl.ems.apigateway.controller.ynservice;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNDataEnterpriseEnergyService;
import com.xl.ems.apigateway.service.ynservice.impl.YNDataEnterpriseEnergyImpl;

@RestController
public class YNDataEnterpriseEnergyController {
	
	@Autowired
	YNDataEnterpriseEnergyService yNDataEnterpriseEnergyService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataEnterpriseEnergyController.class);
	
	@RequestMapping(value = "/dataEnterpriseEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DataEnterpriseEnergy_add(@RequestBody Map<String,Object> requestMap){
		LOGGER.info("YNDataEnterpriseEnergyController YNDataEnterpriseEnergyAdd begin...");
        JSONObject result = yNDataEnterpriseEnergyService.dataEnterpriseEnergy(requestMap);
        LOGGER.info("YNDataEnterpriseEnergyController YNDataEnterpriseEnergyAdd end OK...");
        return RestResponse.success(result);
	}
}
