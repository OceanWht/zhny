package com.xl.ems.apigateway.service.ynservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.*;
import com.xl.ems.apigateway.service.ynservice.YNCompanyInfoService;
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

@Service
public class YNCompanyInfoServiceImpl implements YNCompanyInfoService {


    @Autowired
    GenericRest genericRest;

    @Value("${yn-nhjc.header-prefix}")
    String headrPrefix;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyInfoServiceImpl.class);

    public JSONObject companyContacterAdd(Map<String, Object> requestMap) {

        LOGGER.info("YNEnterpriseServiceImpl companyContacterAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CompanyContacterModel"));
        CompanyContacterModel companyContacterModel = JSONObject.toJavaObject(JSONObject.parseObject(data),CompanyContacterModel.class);


        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || companyContacterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterAdd requestMap params error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(companyContacterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyContacter/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyContacterAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyContacterAdd end...");
        return null;
    }

    public JSONObject companyContacterUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyContacterUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CompanyContacterModel"));
        CompanyContacterModel companyContacterModel = JSONObject.toJavaObject(JSONObject.parseObject(data),CompanyContacterModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || companyContacterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterUpdate requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(companyContacterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyContacter/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyContacterUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyContacterUpdate end...");
        return null;
    }

    public JSONObject companyContacterDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyContacterDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CompanyContacterModel"));
        CompanyContacterModel companyContacterModel = JSONObject.toJavaObject(JSONObject.parseObject(data),CompanyContacterModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || companyContacterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyContacterDelete requestMap error");
            return null;
        }

        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(companyContacterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyContacter/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyContacterDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyContacterDelete end...");
        return null;
    }

    public JSONObject companyEnergySysAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergySysAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("SysEnergyModel"));
        SysEnergyModel sysEnergyModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),SysEnergyModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || sysEnergyModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysAdd requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(sysEnergyModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergySys/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysAdd end...");
        return null;
    }

    public JSONObject companyEnergySysUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergySysUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("SysEnergyModel"));
        SysEnergyModel sysEnergyModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),SysEnergyModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || sysEnergyModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysUpdate requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(sysEnergyModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergySys/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysUpdate end...");
        return null;
    }

    public JSONObject companyEnergySysDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergySysDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("SysEnergyModel"));
        SysEnergyModel sysEnergyModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),SysEnergyModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || sysEnergyModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergySysDelete requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(sysEnergyModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergySys/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergySysDelete end...");
        return null;
    }

    public JSONObject companyEnergyManagerAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyManagerAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyManagerModel"));
        EnergyManagerModel energyManagerModel = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyManagerModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyManagerModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerAdd requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyManagerModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyManager/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerAdd end...");
        return null;
    }

    public JSONObject companyEnergyManagerUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyManagerUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyManagerModel"));
        EnergyManagerModel energyManagerModel = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyManagerModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyManagerModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerUpdate requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyManagerModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyManager/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerUpdate end...");
        return null;
    }

    public JSONObject companyEnergyManagerDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyManagerDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyManagerModel"));
        EnergyManagerModel energyManagerModel = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyManagerModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyManagerModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyManagerDelete requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyManagerModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyManager/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyManagerDelete end...");
        return null;
    }

    public JSONObject companyEnergyMonitorAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyMonitorAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyMonitorModel"));
        EnergyMonitorModel energyMonitorModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyMonitorModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyMonitorModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorAdd requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyMonitorModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyMonitor/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorAdd end...");
        return null;
    }

    public JSONObject companyEnergyMonitorUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyMonitorUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyMonitorModel"));
        EnergyMonitorModel energyMonitorModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyMonitorModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyMonitorModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorUpdate requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyMonitorModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyMonitor/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorUpdate end...");
        return null;
    }

    public JSONObject companyEnergyMonitorDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyMonitorDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyMonitorModel"));
        EnergyMonitorModel energyMonitorModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyMonitorModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || energyMonitorModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyMonitorDelete requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(energyMonitorModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyMonitor/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyMonitorDelete end...");
        return null;
    }

    public JSONObject companyCalculaterAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyCalculaterAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CalculaterModel"));
        CalculaterModel calculaterModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),CalculaterModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || calculaterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterAdd requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(calculaterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyCalculater/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterAdd end...");
        return null;
    }

    public JSONObject companyCalculaterUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyCalculaterUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CalculaterModel"));
        CalculaterModel calculaterModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),CalculaterModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || calculaterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterUpdate requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(calculaterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyCalculater/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterUpdate end...");
        return null;
    }

    public JSONObject companyCalculaterDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyCalculaterDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("CalculaterModel"));
        CalculaterModel calculaterModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),CalculaterModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || calculaterModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyCalculaterDelete requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(calculaterModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyCalculater/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyCalculaterDelete end...");
        return null;
    }

    public JSONObject companyEnergyCertificationAdd(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyCertificationAdd begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationAdd requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyCertificationModel"));
        EnergyCertificationModel certificationModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyCertificationModel.class);

        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || certificationModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationAdd requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(certificationModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyCertification/add", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationAdd end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationAdd end...");
        return null;
    }

    public JSONObject companyEnergyCertificationUpdate(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyCertificationUpdate begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationUpdate requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyCertificationModel"));
        EnergyCertificationModel certificationModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyCertificationModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || certificationModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationUpdate requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(certificationModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyCertification/update", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationUpdate end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationUpdate end...");
        return null;
    }

    public JSONObject companyEnergyCertificationDelete(Map<String, Object> requestMap) {
        LOGGER.info("YNEnterpriseServiceImpl companyEnergyCertificationDelete begin...");
        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationDelete requestMap error");
            return null;
        }
        String token = (String) requestMap.get("token");
        String data = JSONObject.toJSONString(requestMap.get("EnergyCertificationModel"));
        EnergyCertificationModel certificationModel  = JSONObject.toJavaObject(JSONObject.parseObject(data),EnergyCertificationModel.class);
        String softUrl = (String) requestMap.get("softurl");
        if (Strings.isNullOrEmpty(softUrl) || certificationModel == null || Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyInfoServiceImpl companyEnergyCertificationDelete requestMap error");
            return null;
        }


        //发送请求
        HttpHeaders headers = GetYnHeaders.getYnHeaders(headrPrefix, token);
        softUrl = "direct://" + softUrl;
        HttpEntity httpEntity = new HttpEntity(certificationModel, headers);
        ResponseEntity<JSONObject> responseEntity = genericRest.post(softUrl + "/companyEnergyCertification/delete", httpEntity, new ParameterizedTypeReference<JSONObject>() {
        });


        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationDelete end OK...");
            return responseEntity.getBody();
        }

        LOGGER.info("YNCompanyInfoServiceImpl companyEnergyCertificationDelete end...");
        return null;
    }
}
