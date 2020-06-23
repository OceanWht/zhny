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
import com.xl.ems.apigateway.model.ArganEnergyConsumeModel;
import com.xl.ems.apigateway.model.ArganEnergyIDCModel;
import com.xl.ems.apigateway.model.ArganEnergyWarmModel;
import com.xl.ems.apigateway.service.ynservice.YNArganEnergyService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNArganEnergyImpl implements YNArganEnergyService {
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNArganEnergyImpl.class);
	
	/**
	 * 公共机构能源资源消费信息
	 */
	public JSONObject arganEnergy_addEnergy(Map<String, Object> requestMap) {
		LOGGER.info("YNArganEnergyImpl AddEnergyAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNArganEnergyImpl AddEnergyAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("ArganEnergyConsumeModel"));
        ArganEnergyConsumeModel arganEnergyConsumeModel = JSONObject.toJavaObject(JSONObject.parseObject(data),ArganEnergyConsumeModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || arganEnergyConsumeModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyImpl AddEnergyAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(arganEnergyConsumeModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/arganEnergy/addEnergy", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNArganEnergyImpl AddEnergyAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNArganEnergyImpl AddEnergyAdd end...");
        return null;
	}
	
	/**
	 * 公共机构数据中心机房能源消费信息
	 */
	public JSONObject arganEnergy_addIDC(Map<String, Object> requestMap) {
		LOGGER.info("YNArganEnergyImpl AddIDCAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNArganEnergyImpl AddIDCAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("ArganEnergyIdcModel"));
        ArganEnergyWarmModel arganEnergyIDCModel = JSONObject.toJavaObject(JSONObject.parseObject(data),ArganEnergyWarmModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || arganEnergyIDCModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyImpl AddIDCAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(arganEnergyIDCModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/arganEnergy/addIDC", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNArganEnergyImpl AddIDCAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNArganEnergyImpl AddIDCAdd end...");
        return null;
	}
	
	/**
	 * 公共机构采暖能源资源消费信息
	 */
	public JSONObject arganEnergy_addWarm(Map<String, Object> requestMap) {
		LOGGER.info("YNArganEnergyImpl AddWarmAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNArganEnergyImpl AddWarmAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("ArganEnergyWarmModel"));
        ArganEnergyIDCModel arganEnergyWarmModel = JSONObject.toJavaObject(JSONObject.parseObject(data),ArganEnergyIDCModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || arganEnergyWarmModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyImpl AddWarmAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(arganEnergyWarmModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/arganEnergy/addWarm", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNArganEnergyImpl AddWarmAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNArganEnergyImpl AddWarmAdd end...");
        return null;
	}
}
