package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.controller.YNCompanyEnergyController;
import com.xl.ems.ynnhjc.mapper.*;
import com.xl.ems.ynnhjc.model.*;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyService;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class YNCompanyEnergyServiceImpl implements YNCompanyEnergyService {

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    @Autowired
    SysEnergyModelMapper sysEnergyModelMapper;

    @Autowired
    EnergyManagerModelMapper energyManagerModelMapper;

    @Autowired
    EnergyMonitorModelMapper energyMonitorModelMapper;

    @Autowired
    CalculaterModelMapper calculaterModelMapper;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    EnergyCertificationModelMapper energyCertificationModelMapper;


    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyServiceImpl.class);

    @Override
    public RestResponse<JSONObject> sysEnergyAdd(SysEnergyModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl SysEnergyAdd begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  SysEnergyAdd energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  SysEnergyAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<SysEnergyModel> sysEnergyModels = sysEnergyModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(sysEnergyModels)){
            for (SysEnergyModel sysEnergyModel:sysEnergyModels){
                if (sysEnergyModel.getEnergyoffice().equals(energyModel.getEnergyoffice()) &&
                sysEnergyModel.getEnergyofficial().equals(energyModel.getEnergyofficial()) &&
                sysEnergyModel.getEnergyofficialposition().equals(energyModel.getEnergyofficialposition())){
                    LOGGER.info("YNCompanyEnergyServiceImpl SysEnergyAdd energyModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl SysEnergyAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl SysEnergyAdd akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl SysEnergyAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("SysEnergyModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergySys/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                energyModel.setDataindex(dataIndex);
                try {
                    sysEnergyModelMapper.insertSelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl SysEnergyAdd save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.sysEnergyAdd(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl SysEnergyAdd res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl SysEnergyAdd end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> sysEnergyUpdate(SysEnergyModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyUpdate begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  sysEnergyUpdate energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  sysEnergyUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyUpdate akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("SysEnergyModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergySys/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    sysEnergyModelMapper.updateByPrimaryKeySelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyUpdate updateByPrimaryKeySelective has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.sysEnergyUpdate(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyUpdate res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyUpdate end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> sysEnergyDelete(SysEnergyModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyDelete begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  sysEnergyDelete energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  sysEnergyDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyDelete akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("SysEnergyModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergySys/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200){
                try {
                    sysEnergyModelMapper.deleteByPrimaryKey(energyModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyEnergyServiceImpl sysEnergyDelete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.sysEnergyDelete(energyModel);
            }else {
                LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyDelete res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyDelete end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<List<SysEnergyModel>> sysEnergyList(String enterpriseCode) {
        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyList begin....");
        List<SysEnergyModel> energyModels = sysEnergyModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyEnergyServiceImpl sysEnergyList end....");
        return RestResponse.success(energyModels);
    }

    @Override
    public RestResponse<JSONObject> energyManagerAdd(EnergyManagerModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerAdd begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerAdd energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<EnergyManagerModel> energyManagerModels = energyManagerModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(energyManagerModels)){
            for (EnergyManagerModel managerModel:energyManagerModels){
                if (managerModel.getManager().equals(energyModel.getManager()) &&
                        managerModel.getDept().equals(energyModel.getDept())){
                    LOGGER.info("YNCompanyEnergyServiceImpl energyManagerAdd energyModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerAdd akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyManagerModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyManager/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                energyModel.setDataindex(dataIndex);
                try {
                    energyManagerModelMapper.insertSelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyManagerAdd save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyManagerAdd(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyManagerAdd res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerAdd end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyManagerUpdate(EnergyManagerModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerUpdate begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerUpdate energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerUpdate akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyManagerModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyManager/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    energyManagerModelMapper.updateByPrimaryKeySelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyManagerUpdate updateByPrimaryKeySelective has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyManagerUpdate(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyManagerUpdate res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerUpdate end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyManagerDelete(EnergyManagerModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerDelete begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerDelete energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyManagerDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerDelete akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyManagerDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyManagerModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyManager/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200){
                try {
                    energyManagerModelMapper.deleteByPrimaryKey(energyModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyEnergyServiceImpl energyManagerDelete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyManagerDelete(energyModel);
            }else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyManagerDelete res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerDelete end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<List<EnergyManagerModel>> energyManagerList(String enterpriseCode) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerList begin....");
        List<EnergyManagerModel> energyModels = energyManagerModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyEnergyServiceImpl energyManagerList end....");
        return RestResponse.success(energyModels);
    }

    @Override
    public RestResponse<JSONObject> energyMonitorAdd(EnergyMonitorModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorAdd begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorAdd energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<EnergyMonitorModel> energyManagerModels = energyMonitorModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(energyManagerModels)){
            for (EnergyMonitorModel managerModel:energyManagerModels){
                if (managerModel.getMonitor().equals(energyModel.getMonitor()) &&
                        managerModel.getPhone().equals(energyModel.getPhone()) &&
                        managerModel.getDept().equals(energyModel.getDept()) &&
                        managerModel.getJob().equals(energyModel.getJob())){
                    LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorAdd energyModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorAdd akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyMonitorModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyMonitor/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                energyModel.setDataindex(dataIndex);
                try {
                    energyMonitorModelMapper.insertSelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorAdd save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyMonitorAdd(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorAdd res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorAdd end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyMonitorUpdate(EnergyMonitorModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorUpdate begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorUpdate energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorUpdate akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyMonitorModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyMonitor/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    energyMonitorModelMapper.updateByPrimaryKeySelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorUpdate updateByPrimaryKeySelective has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyMonitorUpdate(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorUpdate res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorUpdate end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyMonitorDelete(EnergyMonitorModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorDelete begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorDelete energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyMonitorDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorDelete akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyMonitorModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyMonitor/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200){
                try {
                    energyMonitorModelMapper.deleteByPrimaryKey(energyModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyEnergyServiceImpl energyMonitorDelete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyMonitorDelete(energyModel);
            }else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorDelete res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorDelete end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<List<EnergyMonitorModel>> energyMonitorList(String enterpriseCode) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorList begin....");
        List<EnergyMonitorModel> energyModels = energyMonitorModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyEnergyServiceImpl energyMonitorList end....");
        return RestResponse.success(energyModels);
    }

    @Override
    public RestResponse<JSONObject> calculaterAdd(CalculaterModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterAdd begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterAdd energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<CalculaterModel> energyManagerModels = calculaterModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(energyManagerModels)){
            for (CalculaterModel managerModel:energyManagerModels){
                if (managerModel.getCalculatername().equals(energyModel.getCalculatername()) &&
                        managerModel.getDept().equals(energyModel.getDept()) &&
                        managerModel.getJob().equals(energyModel.getJob())){
                    LOGGER.info("YNCompanyEnergyServiceImpl calculaterAdd energyModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterAdd akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("CalculaterModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyCalculater/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                energyModel.setDataindex(dataIndex);
                try {
                    calculaterModelMapper.insertSelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl calculaterAdd save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.calculaterAdd(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl calculaterAdd res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl calculaterAdd end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> calculaterUpdate(CalculaterModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterUpdate begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterUpdate energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterUpdate akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("CalculaterModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyCalculater/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    calculaterModelMapper.updateByPrimaryKeySelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl calculaterUpdate updateByPrimaryKeySelective has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.calculaterUpdate(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl calculaterUpdate res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl calculaterUpdate end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> calculaterDelete(CalculaterModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterDelete begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterDelete energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  calculaterDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterDelete akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl calculaterDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("CalculaterModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyCalculater/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200){
                try {
                    calculaterModelMapper.deleteByPrimaryKey(energyModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyEnergyServiceImpl calculaterDelete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.calculaterDelete(energyModel);
            }else {
                LOGGER.info("YNCompanyEnergyServiceImpl calculaterDelete res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl calculaterDelete end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<List<CalculaterModel>> calculaterList(String enterpriseCode) {
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterList begin....");
        List<CalculaterModel> energyModels = calculaterModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterList end....");
        return RestResponse.success(energyModels);
    }

    @Override
    public RestResponse<JSONObject> energyCertificationAdd(EnergyCertificationModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationAdd begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationAdd energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<EnergyCertificationModel> energyManagerModels = energyCertificationModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(energyManagerModels)){
            for (EnergyCertificationModel managerModel:energyManagerModels){
                if (managerModel.getEnergypass().equals(energyModel.getEnergypass()) &&
                        managerModel.getPassorg().equals(energyModel.getPassorg())){
                    LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationAdd energyModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationAdd akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyCertificationModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyCertification/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                energyModel.setDataindex(dataIndex);
                try {
                    energyCertificationModelMapper.insertSelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationAdd save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyCertificationAdd(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationAdd res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationAdd end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyCertificationUpdate(EnergyCertificationModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationUpdate begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationUpdate energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationUpdate akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyCertificationModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyCertification/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    energyCertificationModelMapper.updateByPrimaryKeySelective(energyModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationUpdate updateByPrimaryKeySelective has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyCertificationUpdate(energyModel);
            }
            else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationUpdate res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationUpdate end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> energyCertificationDelete(EnergyCertificationModel energyModel) {
        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationDelete begin....");
        if (energyModel == null){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationDelete energyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = energyModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)){
            LOGGER.info("YNCompanyEnergyServiceImpl  energyCertificationDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationDelete akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("EnergyCertificationModel", energyModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyEnergyCertification/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200){
                try {
                    energyCertificationModelMapper.deleteByPrimaryKey(energyModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyEnergyServiceImpl energyCertificationDelete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.energyCertificationDelete(energyModel);
            }else {
                LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationDelete res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyEnergyServiceImpl energyCertificationDelete end....");
        return RestResponse.success();
    }

    @Override
    public RestResponse<List<EnergyCertificationModel>> energyCertificationList(String enterpriseCode) {
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterList begin....");
        List<EnergyCertificationModel> energyModels = energyCertificationModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyEnergyServiceImpl calculaterList end....");
        return RestResponse.success(energyModels);
    }
}
