package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.service.ynservice.YNProcessConfigureService;
import com.xl.ems.apigateway.utils.GetYnHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

//用能单位生产工序信息接口实现类
@Service
public class YNProcessConfigureServiceImpl implements YNProcessConfigureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNProcessConfigureServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    //生产工序增加
    public JSONObject YNProcessConfigureAdd(Map<String, String> requestMap) {

        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);

        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/processConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //生产工序修改
    public JSONObject YNProcessConfigureUpdate(Map<String, String> requestMap) {

        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/processConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //生产工序删除
    public JSONObject YNProcessConfigureDelete(Map<String, String> requestMap) {

        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/processConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
