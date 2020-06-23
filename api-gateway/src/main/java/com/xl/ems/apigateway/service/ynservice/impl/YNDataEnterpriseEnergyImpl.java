package com.xl.ems.apigateway.service.ynservice.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.EnterpriseEnergyModel;
import com.xl.ems.apigateway.service.ynservice.YNDataEnterpriseEnergyService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNDataEnterpriseEnergyImpl implements YNDataEnterpriseEnergyService {
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataEnterpriseEnergyImpl.class);
	
	/**
	 * 用能单位能源资源计量采集数据
	 */
	public JSONObject dataEnterpriseEnergy(Map<String, Object> requestMap) {
		LOGGER.info("YNDataEnterpriseEnergyImpl DataEnterpriseEnergyAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDataEnterpriseEnergyImpl DataEnterpriseEnergyAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnterpriseEnergyModel"));
        EnterpriseEnergyModel enterpriseEnergyModel = JSONObject.toJavaObject(JSONObject.parseObject(data),EnterpriseEnergyModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || enterpriseEnergyModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDataEnterpriseEnergyImpl DataEnterpriseEnergyAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(enterpriseEnergyModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/dataEnterpriseEnergy", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDataEnterpriseEnergyImpl DataEnterpriseEnergyAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNDataEnterpriseEnergyImpl DataEnterpriseEnergyAdd end...");
        return null;
	}
}
