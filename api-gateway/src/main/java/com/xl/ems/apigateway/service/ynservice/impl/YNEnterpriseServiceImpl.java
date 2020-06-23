package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.SysApplicationModelWithBLOBs;
import com.xl.ems.apigateway.service.ynservice.YNEnterpriseService;
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

import java.util.HashMap;
import java.util.Map;

@Service
public class YNEnterpriseServiceImpl implements YNEnterpriseService {


    @Autowired
    GenericRest genericRest;

    @Value("${yn-nhjc.header-prefix}")
    String headrPrefix;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseServiceImpl.class);

    public JSONObject enterpriseInfoRWK(Map<String, String> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoRWK begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoRWK requestMap error");
            return null;
        }

        String enterpriseCode = requestMap.get("enterpriseCode");
        String secretKey = requestMap.get("secretKey");
        String softurl = requestMap.get("softurl");
        if (Strings.isNullOrEmpty(enterpriseCode) ||Strings.isNullOrEmpty(softurl)){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoRWK requestMap error");
            return null;
        }
        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders();
        softurl = "direct://"+softurl;
        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        if (!Strings.isNullOrEmpty(secretKey)){
            body.put("secretKey",secretKey);
        }
        HttpEntity httpEntity = new HttpEntity(body,headers);
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softurl + "/enterpriseInfo/rwk", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoRWK end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoRWK end...");
        return null;
    }


    public JSONObject dataDownloadPublic(Map<String, String> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl dataDownloadPublic begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl dataDownloadPublic requestMap error");
            return null;
        }

        String enterpriseCode = requestMap.get("enterpriseCode");
        String itemIndex = requestMap.get("itemIndex");
        String softurl = requestMap.get("softurl");

        if (Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(itemIndex) || Strings.isNullOrEmpty(softurl)){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoRWK requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders();
        softurl = "direct://"+softurl;
        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        body.put("itemIndex",itemIndex);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softurl + "/dataDownload/public", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl dataDownloadPublic end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl dataDownloadPublic end...");
        return null;
    }

    public JSONObject enterpriseInfoApply(String request) {
        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoApply begin...");
        if (Strings.isNullOrEmpty(request)){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoApply requestMap error");
            return null;
        }

        JSONObject reqJson = JSON.parseObject(request);

        String softUrl = reqJson.getString("softurl");
        SysApplicationModelWithBLOBs applicationModelWithBLOBs
                = reqJson.getJSONObject("SysApplicationModel").toJavaObject(SysApplicationModelWithBLOBs.class);
        if (Strings.isNullOrEmpty(softUrl) || applicationModelWithBLOBs == null){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoApply requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders();
        HttpEntity httpEntity = new HttpEntity(applicationModelWithBLOBs,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/enterpriseInfo/apply", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoApply end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoApply end...");
        return null;
    }

    public JSONObject enterpriseInfoUpdate(JSONObject requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoUpdate begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoUpdate requestMap error");
            return null;
        }

        String softUrl = (String) requestMap.get("softurl");
        SysApplicationModelWithBLOBs applicationModelWithBLOBs = requestMap.getJSONObject("SysApplicationModel").toJavaObject(SysApplicationModelWithBLOBs.class);
        if (Strings.isNullOrEmpty(softUrl) || applicationModelWithBLOBs == null){
            LOGGER.error("YNEnterpriseServiceImpl enterpriseInfoUpdate requestMap error");
            return null;
        }

        HttpHeaders headers = GetYnHeaders.getYnHeaders();
        HttpEntity httpEntity = new HttpEntity(applicationModelWithBLOBs,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/enterpriseInfo/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl enterpriseInfoUpdate end...");
        return null;
    }

    public JSONObject requestAK(Map<String, String> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl requestAK begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl requestAK requestMap error");
            return null;
        }

        String softUrl = requestMap.get("softurl");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String password = requestMap.get("password");
        if (Strings.isNullOrEmpty(softUrl) || Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(password)){
            LOGGER.error("YNEnterpriseServiceImpl requestAK requestMap error");
            return null;
        }

        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        body.put("password",password);
        HttpHeaders headers = GetYnHeaders.getYnHeaders();
        HttpEntity httpEntity = new HttpEntity(body,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/requestAK", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl requestAK end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl requestAK end...");
        return null;
    }

    public JSONObject checkVersion(Map<String, String> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl checkVersion begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl checkVersion requestMap error");
            return null;
        }

        String softUrl = requestMap.get("softurl");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String token = requestMap.get("token");
        if (Strings.isNullOrEmpty(softUrl) || Strings.isNullOrEmpty(enterpriseCode)||Strings.isNullOrEmpty(token)){
            LOGGER.error("YNEnterpriseServiceImpl checkVersion requestMap error");
            return null;
        }

        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix,token);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/checkVersion", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl checkVersion end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl requestAK end...");
        return null;
    }

    public JSONObject platformAccessURL(Map<String, String> requestMap) {

        LOGGER.info("YNEnterpriseServiceImpl platformAccessURL begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl platformAccessURL requestMap error");
            return null;
        }

        String softUrl = requestMap.get("softurl");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String token = requestMap.get("token");
        if (Strings.isNullOrEmpty(softUrl) || Strings.isNullOrEmpty(enterpriseCode)||Strings.isNullOrEmpty(token)){
            LOGGER.error("YNEnterpriseServiceImpl platformAccessURL requestMap error");
            return null;
        }

        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix,token);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/platformAccessURL", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl platformAccessURL end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl platformAccessURL end...");
        return null;
    }

    public JSONObject dataDownload(Map<String, String> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl dataDownload begin...");
        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseServiceImpl dataDownload requestMap error");
            return null;
        }

        String softUrl = requestMap.get("softurl");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String itemIndex = requestMap.get("itemIndex");
        String token = requestMap.get("token");
        if (Strings.isNullOrEmpty(softUrl) || Strings.isNullOrEmpty(enterpriseCode)||Strings.isNullOrEmpty(token)
        ||Strings.isNullOrEmpty(itemIndex)){
            LOGGER.error("YNEnterpriseServiceImpl dataDownload requestMap error");
            return null;
        }

        Map<String,String> body = new HashMap<String, String>();
        body.put("enterpriseCode",enterpriseCode);
        body.put("itemIndex",itemIndex);
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix,token);
        HttpEntity httpEntity = new HttpEntity(body,headers);
        softUrl = "direct://"+softUrl;
        ResponseEntity<JSONObject> responseEntity =  genericRest.post(softUrl + "/dataDownload", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            LOGGER.info("YNEnterpriseServiceImpl dataDownload end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNEnterpriseServiceImpl platformAccessURL end...");
        return null;
    }


}
