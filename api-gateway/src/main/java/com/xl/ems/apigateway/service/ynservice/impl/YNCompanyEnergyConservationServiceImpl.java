package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.service.ynservice.YNCompanyEnergyConservationService;
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

//用能单位节能项目情况信息接口实现类
@Service
public class YNCompanyEnergyConservationServiceImpl implements YNCompanyEnergyConservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyConservationServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    //节能项目情况增加
    public JSONObject YNCompanyEnergyConservationAdd(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);

        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyConservation/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //节能项目情况修改
    public JSONObject YNCompanyEnergyConservationUpdate(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyConservation/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //节能项目情况删除
    public JSONObject YNCompanyEnergyConservationDelete(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyEnergyConservation/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
