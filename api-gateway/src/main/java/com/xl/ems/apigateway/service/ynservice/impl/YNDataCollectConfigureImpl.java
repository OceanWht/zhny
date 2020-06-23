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
import com.xl.ems.apigateway.model.DataCollectConfigureModel;
import com.xl.ems.apigateway.service.ynservice.YNDataCollectConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNDataCollectConfigureImpl implements YNDataCollectConfigureService {
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataCollectConfigureImpl.class);
	
	/**
	 * 用能单位数据采集配置项信息 add
	 */
	public JSONObject dataCollectConfigure_add(Map<String, Object> requestMap) {
		LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DataCollectConfigureModel"));
        DataCollectConfigureModel dataCollectConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DataCollectConfigureModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || dataCollectConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(dataCollectConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/dataCollectConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureAdd end...");
        return null;
	}
	
	/**
	 * 用能单位数据采集配置项信息 delete
	 */
	public JSONObject dataCollectConfigure_delete(Map<String, Object> requestMap) {
		LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DataCollectConfigureModel"));
        DataCollectConfigureModel dataCollectConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DataCollectConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || dataCollectConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(dataCollectConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/dataCollectConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureDelete end...");
        return null;
	}
	
	/**
	 * 用能单位数据采集配置项信息 update
	 */
	public JSONObject dataCollectConfigure_update(Map<String, Object> requestMap) {
		LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("DataCollectConfigureModel"));
        DataCollectConfigureModel dataCollectConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),DataCollectConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || dataCollectConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDataCollectConfigureImpl DataCollectConfigureUpdate requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(dataCollectConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/dataCollectConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        LOGGER.info("YNDataCollectConfigureImpl DataCollectConfigureUpdate end...");
        return null;
	}
}
