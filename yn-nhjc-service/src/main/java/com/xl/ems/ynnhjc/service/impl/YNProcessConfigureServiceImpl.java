package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.ProcessConfigureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.ProcessConfigureModel;
import com.xl.ems.ynnhjc.service.YNProcessConfigureService;
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

//用能单位生产工序信息接口实现类
@Service
public class YNProcessConfigureServiceImpl implements YNProcessConfigureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNProcessConfigureServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    ProcessConfigureModelMapper processConfigureModelMapper;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;


    @Autowired
    AKModelMapper akModelMapper;

    //生产工序增加
    public JSONObject YNProcessConfigureAdd(Map<String, String> requestMap) {
        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String processCode = requestMap.get("processCode");
        String processName = requestMap.get("processName");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(processName)){

            LOGGER.error("YNProcessConfigureServiceImpl YNProcessConfigureAdd requestMap error");
            return null;
        }

        ProcessConfigureModel processConfigureModel = new ProcessConfigureModel();
        processConfigureModel.setRegversion(regVersion);
        processConfigureModel.setDicversion(dicVersion);
        processConfigureModel.setEnterprisecode(enterpriseCode);
        processConfigureModel.setProcesscode(processCode);
        processConfigureModel.setProcessname(processName);
        processConfigureModel.setRemark(requestMap.get("remark"));

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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/processConfigure/add", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd error...");
                return result;
            }
            Map<String,String> data = (Map<String, String>) json.get("data");
            String dataIndex = data.get("dataIndex");
            processConfigureModel.setDataindex(dataIndex);
            try {
                processConfigureModelMapper.insertSelective(processConfigureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureAdd error...");
                return result;
            }
        }
        return null;
    }

    //生产工序修改
    public JSONObject YNProcessConfigureUpdate(Map<String, String> requestMap) {
        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String processCode = requestMap.get("processCode");
        String processName = requestMap.get("processName");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(processName) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNProcessConfigureServiceImpl YNProcessConfigureUpdate requestMap error");
            return null;
        }

        ProcessConfigureModel processConfigureModel = new ProcessConfigureModel();
        processConfigureModel.setDataindex(dataIndex);
        processConfigureModel.setRegversion(regVersion);
        processConfigureModel.setDicversion(dicVersion);
        processConfigureModel.setEnterprisecode(enterpriseCode);
        processConfigureModel.setProcesscode(processCode);
        processConfigureModel.setProcessname(processName);
        processConfigureModel.setRemark(requestMap.get("remark"));

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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/processConfigure/update", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate error...");
                return result;
            }
            try {
                processConfigureModelMapper.updateByPrimaryKeySelective(processConfigureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureUpdate error...");
                return result;
            }
        }
        return null;
    }

    //生产工序删除
    public JSONObject YNProcessConfigureDelete(Map<String, String> requestMap) {
        LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete begin...");

        String enterpriseCode = requestMap.get("enterpriseCode");
        String dataIndex = requestMap.get("dataIndex");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNProcessConfigureServiceImpl YNProcessConfigureDelete requestMap error");
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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/processConfigure/delete", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete error...");
                return result;
            }
            try {
                processConfigureModelMapper.deleteByPrimaryKey(dataIndex);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNProcessConfigureServiceImpl YNProcessConfigureDelete error...");
                return result;
            }
        }
        return null;
    }

    @Override
    public List<ProcessConfigureModel> list(String code) {
        return processConfigureModelMapper.getList(code);
    }
}
