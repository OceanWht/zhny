package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.service.ynservice.YNCompanyEnergyAccountService;
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

//用能单位水、电、燃气户号信息接口实现类
@Service
public class YNCompanyEnergyAccountServiceImpl implements YNCompanyEnergyAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyAccountServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    //能源账号信息增加
    public JSONObject YNCompanyEnergyAccountAdd(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);

        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyAccount/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //能源账号信息修改
    public JSONObject YNCompanyEnergyAccountUpdate(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyAccount/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //能源账号信息删除
    public JSONObject YNCompanyEnergyAccountDelete(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyAccount/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
