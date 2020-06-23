package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.service.ynservice.YNCompanyMaterielProductService;
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

//用能单位非能源产品信息接口实现类
@Service
public class YNCompanyMaterielProductServiceImpl implements YNCompanyMaterielProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyMaterielProductServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    //非能源产品信息增加
    public JSONObject YNCompanyMaterielProductAdd(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);

        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyMaterielProduct/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //非能源产品信息修改
    public JSONObject YNCompanyMaterielProductUpdate(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyMaterielProduct/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //非能源产品信息删除
    public JSONObject YNCompanyMaterielProductDelete(Map<String, String> requestMap) {

        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete begin...");

        String token = requestMap.get("token");
        HttpHeaders headers = GetYnHeaders.getYnHeaders("Bearer", token);
        HttpEntity httpEntity = new HttpEntity(requestMap,headers);

        String url = requestMap.get("softUrl");
        url = "direct://"+url;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(url + "/companyMaterielProduct/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
