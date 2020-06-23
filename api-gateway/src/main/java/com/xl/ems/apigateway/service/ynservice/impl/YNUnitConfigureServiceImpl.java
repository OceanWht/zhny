package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.service.ynservice.YNUnitConfigureService;
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

//用能单位工序单元信息接口实现类
@Service
public class YNUnitConfigureServiceImpl implements YNUnitConfigureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNUnitConfigureServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    //工序单元增加
    public JSONObject YNUnitConfigureAdd(Map<String, String> requestMap) {

        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);

        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/unitConfigure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //工序单元修改
    public JSONObject YNUnitConfigureUpdate(Map<String, String> requestMap) {

        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/unitConfigure/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //工序单元删除
    public JSONObject YNUnitConfigureDelete(Map<String, String> requestMap) {

        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/unitConfigure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
