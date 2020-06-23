package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.ProductOutputValueModel;
import com.xl.ems.apigateway.service.ynservice.YNCompanyOutputValueService;
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

//用能单位产值、增加值、销售收入信息接口实现类
@Service
public class YNCompanyOutputValueServiceImpl implements YNCompanyOutputValueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyOutputValueServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Value("${yn-nhjc.header-prefix}")
    String headrPrefix;

    //销售收入信息增加
    public JSONObject YNCompanyOutputValueAdd(Map<String, Object> requestMap) {

        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productOutputValueModel"));
        ProductOutputValueModel productOutputValueModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductOutputValueModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productOutputValueModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productOutputValueModel,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyOutputValue/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //销售收入信息修改
    public JSONObject YNCompanyOutputValueUpdate(Map<String, Object> requestMap) {

        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productOutputValueModel"));
        ProductOutputValueModel productOutputValueModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductOutputValueModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productOutputValueModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productOutputValueModel,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyOutputValue/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate end OK...");
            return responseEntity.getBody();
        }
        return null;
    }

    //销售收入信息删除
    public JSONObject YNCompanyOutputValueDelete(Map<String, Object> requestMap) {

        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete begin...");

        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("productOutputValueModel"));
        ProductOutputValueModel productOutputValueModel = JSONObject.toJavaObject(JSONObject.parseObject(data), ProductOutputValueModel.class);

        String softUrl = (String) requestMap.get("softUrl");
        if (Strings.isNullOrEmpty(softUrl) || productOutputValueModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        HttpEntity httpEntity = new HttpEntity(productOutputValueModel,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyOutputValue/delete", httpEntity,
                new ParameterizedTypeReference<JSONObject>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){

            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete end OK...");
            return responseEntity.getBody();
        }
        return null;
    }
}
