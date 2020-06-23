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
import com.xl.ems.apigateway.model.DeviceConfigureModel;
import com.xl.ems.apigateway.service.ynservice.YNDeviceConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNDeviceConfigureImpl implements YNDeviceConfigureService{

	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDeviceConfigureImpl.class);
	
	/**
	 * 用能单位重点耗能设备信息 add
	 */
	public JSONObject DeviceConfigure_add(Map<String, Object> requestMap) {
		LOGGER.info("YNDeviceConfigureImpl deviceConfigureAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DeviceConfigureModel"));
        DeviceConfigureModel deviceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DeviceConfigureModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || deviceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(deviceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/deviceConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDeviceConfigureImpl deviceConfigureAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNDeviceConfigureImpl deviceConfigureAdd end...");
        return null;
	}
	
	/**
	 * 用能单位重点耗能设备信息 delete
	 */
	public JSONObject DeviceConfigure_delete(Map<String, Object> requestMap) {
		LOGGER.info("YNDeviceConfigureImpl deviceConfigureDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DeviceConfigureModel"));
        DeviceConfigureModel deviceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DeviceConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || deviceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(deviceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/deviceConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDeviceConfigureImpl deviceConfigureDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNDeviceConfigureImpl deviceConfigureDelete end...");
        return null;
	}
	
	/**
	 * 用能单位重点耗能设备信息 update
	 */
	public JSONObject DeviceConfigure_update(Map<String, Object> requestMap) {
		LOGGER.info("YNDeviceConfigureImpl deviceConfigureUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DeviceConfigureModel"));
        DeviceConfigureModel deviceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DeviceConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || deviceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDeviceConfigureImpl deviceConfigureUpdate requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(deviceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/deviceConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDeviceConfigureImpl deviceConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        LOGGER.info("YNDeviceConfigureImpl deviceConfigureUpdate end...");
        return null;
	}
}
