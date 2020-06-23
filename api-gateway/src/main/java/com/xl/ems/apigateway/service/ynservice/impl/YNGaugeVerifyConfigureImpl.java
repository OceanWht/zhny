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
import com.xl.ems.apigateway.model.GaugeVerifyConfigureModel;
import com.xl.ems.apigateway.service.ynservice.YNGaugeVerifyConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNGaugeVerifyConfigureImpl implements YNGaugeVerifyConfigureService {
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeVerifyConfigureImpl.class);
	
	/**
	 * 用能单位计量器具检定/校准记录 add
	 * return JSONObject
	 */
	public JSONObject gaugeVerifyConfigure_add(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeVerifyConfigureModel"));
        GaugeVerifyConfigureModel gaugeVerifyConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeVerifyConfigureModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeVerifyConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeVerifyConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeVerifyConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureAdd end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具检定/校准记录 delete
	 * return JSONObject
	 */
	public JSONObject gaugeVerifyConfigure_delete(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeVerifyConfigureModel"));
        GaugeVerifyConfigureModel gaugeVerifyConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeVerifyConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeVerifyConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeVerifyConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeVerifyConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureDelete end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具检定/校准记录 update
	 * return JSONObject
	 */
	public JSONObject gaugeVerifyConfigure_update(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeVerifyConfigureModel"));
        GaugeVerifyConfigureModel gaugeVerifyConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeVerifyConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeVerifyConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureUpdate requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeVerifyConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeVerifyConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        LOGGER.info("YNGaugeVerifyConfigureImpl GaugeVerifyConfigureUpdate end...");
        return null;
	}
}
