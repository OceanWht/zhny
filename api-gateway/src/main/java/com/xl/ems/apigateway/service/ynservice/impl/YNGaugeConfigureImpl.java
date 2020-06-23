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
import com.xl.ems.apigateway.model.GaugeConfigureModel;
import com.xl.ems.apigateway.service.ynservice.YNGaugeConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;

@Service
public class YNGaugeConfigureImpl implements YNGaugeConfigureService {
	
	
	@Autowired
    GenericRest genericRest;
	
	@Value("${yn-nhjc.header-prefix}")
    String headrPrefix;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeConfigureImpl.class);
	
	/**
	 * 用能单位计量器具信息  add
	 *  return JSONObject
	 */
	public JSONObject gaugeConfigure_add(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeConfigureImpl gaugeConfigureAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeConfigureModel"));
        GaugeConfigureModel gaugeConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeConfigureModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeConfigureImpl gaugeConfigureAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeConfigureImpl gaugeConfigureAdd end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具信息 delete
	 *  return JSONObject
	 */
	public JSONObject gaugeConfigure_delete(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeConfigureImpl gaugeConfigureDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeConfigureModel"));
        GaugeConfigureModel gaugeConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeConfigureImpl gaugeConfigureDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNGaugeConfigureImpl gaugeConfigureDelete end...");
        return null;
	}
	
	/**
	 * 用能单位计量器具信息 update
	 *  return JSONObject
	 */
	public JSONObject gaugeConfigure_update(Map<String, Object> requestMap) {
		LOGGER.info("YNGaugeConfigureImpl gaugeConfigureUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("GaugeConfigureModel"));
        GaugeConfigureModel gaugeConfigureModel = JSONObject.toJavaObject(JSONObject.parseObject(data),GaugeConfigureModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || gaugeConfigureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeConfigureImpl gaugeConfigureUpdate requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(gaugeConfigureModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/gaugeConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNGaugeConfigureImpl gaugeConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        LOGGER.info("YNGaugeConfigureImpl gaugeConfigureUpdate end...");
        return null;
	}
}
