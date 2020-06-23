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
import com.xl.ems.apigateway.model.GaugeReplaceConfigureModel;
import com.xl.ems.apigateway.service.ynservice.YNGaugeReplaceConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNGaugeReplaceConfigureImpl implements YNGaugeReplaceConfigureService {
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeReplaceConfigureImpl.class);
	
	/**
	 * 用能单位计量器具更换记录 add
	 *  return JSONObject
	 */
	public JSONObject gaugeReplaceConfigure_add(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeReplaceConfigureModel"));
        GaugeReplaceConfigureModel gaugeReplaceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeReplaceConfigureModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeReplaceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeReplaceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeReplaceConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureAdd end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具更换记录 delete
	 *  return JSONObject
	 */
	public JSONObject gaugeReplaceConfigure_delete(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeReplaceConfigureModel"));
        GaugeReplaceConfigureModel gaugeReplaceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeReplaceConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeReplaceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeReplaceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeReplaceConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureDelete end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具更换记录 update
	 *  return JSONObject
	 */
	public JSONObject gaugeReplaceConfigure_update(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeReplaceConfigureModel"));
        GaugeReplaceConfigureModel gaugeReplaceConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeReplaceConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeReplaceConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureUpdate requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeReplaceConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeReplaceConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        LOGGER.info("YNGaugeReplaceConfigureImpl GaugeReplaceConfigureUpdate end...");
        return null;
	}
}
