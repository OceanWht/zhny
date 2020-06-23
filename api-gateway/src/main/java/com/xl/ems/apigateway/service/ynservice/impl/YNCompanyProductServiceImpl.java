package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.ProductStructureModel;
import com.xl.ems.apigateway.service.ynservice.YNCompanyProductService;
import com.xl.ems.apigateway.utils.GetYnHeaders;
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

import java.util.Map;

//用能单位产品结构信息接口实现类
@Service
public class YNCompanyProductServiceImpl implements YNCompanyProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyProductServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Value("${yn-nhjc.header-prefix}")
    String headrPrefix;

    //产品结构信息增加
    public JSONObject companyProductStructureAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNCompanyProductServiceImpl companyProductStructureAdd begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productStructureModel"));
        ProductStructureModel productStructureModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductStructureModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productStructureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyProductServiceImpl companyProductStructureUpdate requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productStructureModel,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyProductStructure/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyProductServiceImpl companyProductStructureAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //产品结构信息修改
    public JSONObject companyProductStructureUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNCompanyProductServiceImpl companyProductStructureUpdate begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productStructureModel"));
        ProductStructureModel productStructureModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductStructureModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productStructureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyProductServiceImpl companyProductStructureUpdate requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productStructureModel,headers);
        softUrl = "direct://"+softUrl;

        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyProductStructure/update", httpEntity,
                new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyProductServiceImpl companyProductStructureUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //产品结构信息删除
    public JSONObject companyProductStructureDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNCompanyProductServiceImpl companyProductStructureDelete begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productStructureModel"));
        ProductStructureModel productStructureModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductStructureModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productStructureModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyProductServiceImpl companyProductStructureDelete requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productStructureModel,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyProductStructure/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyProductServiceImpl companyProductStructureDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}