package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.EnergyAccountModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyAccountService;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//用能单位水、电、燃气户号信息接口实现类
@Service
public class YNCompanyEnergyAccountServiceImpl implements YNCompanyEnergyAccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyAccountServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;


    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    EnergyAccountModelMapper energyAccountModelMapper;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    //能源账号信息增加
    public JSONObject YNCompanyEnergyAccountAdd(Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String accountType = requestMap.get("accountType");
        String accountNo = requestMap.get("accountNo");
        String accountName = requestMap.get("accountName");
        String provider = requestMap.get("provider");

        EnergyAccountModel energyAccountModel = new EnergyAccountModel();
        energyAccountModel.setRegversion(regVersion);
        energyAccountModel.setDicversion(dicVersion);
        energyAccountModel.setEnterprisecode(enterpriseCode);
        energyAccountModel.setAccounttype(accountType);
        energyAccountModel.setAccountno(accountNo);
        energyAccountModel.setAccountname(accountName);
        energyAccountModel.setProvider(provider);
        energyAccountModel.setRemark(requestMap.get("remark"));
        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(accountType) || Strings.isNullOrEmpty(accountNo) || Strings.isNullOrEmpty(accountName) ||
                Strings.isNullOrEmpty(provider)){

            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd requestMap error");
            return null;
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return null;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return null;
        }else{
            requestMap.put("softUrl", softUrl);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd token is null......");
            return null;
        }

        requestMap.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyAccount/add", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = new JSONObject();
            RestResponse<JSONObject> body = responseEntity.getBody();
            JSONObject json = body.getResult();
            Integer responseCode = (Integer) json.get("responseCode");
            String responseMessage = (String) json.get("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd error...");
                return result;
            }
            Map<String,String> data = (Map<String, String>) json.get("data");
            String dataIndex = data.get("dataIndex");
            energyAccountModel.setDataindex(dataIndex);
            try {
                energyAccountModelMapper.insertSelective(energyAccountModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountAdd error...");
                return result;
            }
        }
        return null;
    }

    //能源账号信息修改
    public JSONObject YNCompanyEnergyAccountUpdate(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String accountType = requestMap.get("accountType");
        String accountNo = requestMap.get("accountNo");
        String accountName = requestMap.get("accountName");
        String provider = requestMap.get("provider");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(accountType) || Strings.isNullOrEmpty(accountNo) || Strings.isNullOrEmpty(accountName) ||
                Strings.isNullOrEmpty(provider) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyAccountController YNCompanyEnergyAccountUpdate requestMap error");
            return null;
        }
        EnergyAccountModel energyAccountModel = new EnergyAccountModel();
        energyAccountModel.setDataindex(dataIndex);
        energyAccountModel.setRegversion(regVersion);
        energyAccountModel.setDicversion(dicVersion);
        energyAccountModel.setEnterprisecode(enterpriseCode);
        energyAccountModel.setAccounttype(accountType);
        energyAccountModel.setAccountno(accountNo);
        energyAccountModel.setAccountname(accountName);
        energyAccountModel.setProvider(provider);
        energyAccountModel.setRemark(requestMap.get("remark"));
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return null;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return null;
        }else{
            requestMap.put("softUrl", softUrl);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate token is null......");
            return null;
        }
        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyAccount/update", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = new JSONObject();
            RestResponse<JSONObject> body = responseEntity.getBody();
            JSONObject json = body.getResult();
            Integer responseCode = (Integer) json.get("responseCode");
            String responseMessage = (String) json.get("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate error...");
                return result;
            }
            try {
                energyAccountModelMapper.updateByPrimaryKeySelective(energyAccountModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountUpdate error...");
                return result;
            }
        }
        return null;
    }

    //能源账号信息删除
    public JSONObject YNCompanyEnergyAccountDelete(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete begin...");

        String enterpriseCode = requestMap.get("enterpriseCode");
        String dataIndex = requestMap.get("dataIndex");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete requestMap error");
            return null;
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            return null;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            return null;
        }else{
            requestMap.put("softUrl", softUrl);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete token is null......");
            return null;
        }
        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyAccount/delete", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = new JSONObject();
            RestResponse<JSONObject> body = responseEntity.getBody();
            JSONObject json = body.getResult();
            Integer responseCode = (Integer) json.get("responseCode");
            String responseMessage = (String) json.get("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete error...");
                return result;
            }
            try {
                energyAccountModelMapper.deleteByPrimaryKey(dataIndex);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyAccountServiceImpl YNCompanyEnergyAccountDelete error...");
                return result;
            }
        }
        return null;
    }

    @Override
    public RestResponse<List<EnergyAccountModel>> getList(String code) {
        List<EnergyAccountModel> accountModels = energyAccountModelMapper.getAll(code);
        return RestResponse.success(accountModels);
    }
}
