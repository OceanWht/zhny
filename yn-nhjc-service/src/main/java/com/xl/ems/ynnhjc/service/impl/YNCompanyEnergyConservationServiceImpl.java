package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.EnergyConservationModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.EnergyConservationModel;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyConservationService;
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

//用能单位节能项目情况信息接口实现类
@Service
public class YNCompanyEnergyConservationServiceImpl implements YNCompanyEnergyConservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyConservationServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    EnergyConservationModelMapper energyConservationModelMapper;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    //节能项目情况增加
    public JSONObject YNCompanyEnergyConservationAdd(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd begin...");

        String regVersion = requestMap.get("regversion");
        String dicVersion = requestMap.get("dicversion");
        String enterpriseCode = requestMap.get("enterprisecode");
        String projectType = requestMap.get("projecttype");
        String projectName = requestMap.get("projectname");
        String improveMeasure = requestMap.get("improvemeasure");
        String investmentAmount = requestMap.get("investmentamount");
        String projectTimeline = requestMap.get("projecttimeline");
        String energySavingAmount = requestMap.get("energysavingamount");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(projectType) || Strings.isNullOrEmpty(projectName) || Strings.isNullOrEmpty(improveMeasure) ){

            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd requestMap error");
            return null;
        }

        EnergyConservationModel energyConservationModel = new EnergyConservationModel();
        energyConservationModel.setRegversion(regVersion);
        energyConservationModel.setDicversion(dicVersion);
        energyConservationModel.setEnterprisecode(enterpriseCode);
        energyConservationModel.setProjecttype(projectType);
        energyConservationModel.setProjectname(projectName);
        energyConservationModel.setImprovemeasure(improveMeasure);
        energyConservationModel.setInvestmentamount(investmentAmount);
        energyConservationModel.setProjecttimeline(projectTimeline);
        energyConservationModel.setEnergysavingamount(energySavingAmount);
        energyConservationModel.setRemark(requestMap.get("remark"));
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
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd token is null......");
            return null;
        }

        requestMap.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyConservation/add", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd error...");
                return result;
            }
            Map<String,String> data = (Map<String, String>) json.get("data");
            String dataIndex = data.get("dataIndex");
            energyConservationModel.setDataindex(dataIndex);
            try {
                energyConservationModelMapper.insertSelective(energyConservationModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationAdd error...");
                return result;
            }
        }
        return null;
    }

    //节能项目情况修改
    public JSONObject YNCompanyEnergyConservationUpdate(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate begin...");

        String dataIndex = requestMap.get("dataindex");
        String regVersion = requestMap.get("regversion");
        String dicVersion = requestMap.get("dicversion");
        String enterpriseCode = requestMap.get("enterprisecode");
        String projectType = requestMap.get("projecttype");
        String projectName = requestMap.get("projectname");
        String improveMeasure = requestMap.get("improvemeasure");
        String investmentAmount = requestMap.get("investmentamount");
        String projectTimeline = requestMap.get("projecttimeline");
        String energySavingAmount = requestMap.get("energysavingamount");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(projectType) || Strings.isNullOrEmpty(projectName) || Strings.isNullOrEmpty(improveMeasure)  ||
                Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate requestMap error");
            return null;
        }

        EnergyConservationModel energyConservationModel = new EnergyConservationModel();
        energyConservationModel.setDataindex(dataIndex);
        energyConservationModel.setRegversion(regVersion);
        energyConservationModel.setDicversion(dicVersion);
        energyConservationModel.setEnterprisecode(enterpriseCode);
        energyConservationModel.setProjecttype(projectType);
        energyConservationModel.setProjectname(projectName);
        energyConservationModel.setImprovemeasure(improveMeasure);
        energyConservationModel.setInvestmentamount(investmentAmount);
        energyConservationModel.setProjecttimeline(projectTimeline);
        energyConservationModel.setEnergysavingamount(energySavingAmount);
        energyConservationModel.setRemark(requestMap.get("remark"));
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
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate token is null......");
            return null;
        }

        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyConservation/update", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate error...");
                return result;
            }
            try {
                energyConservationModelMapper.updateByPrimaryKeySelective(energyConservationModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationUpdate error...");
                return result;
            }
        }
        return null;
    }

    //节能项目情况删除
    public JSONObject YNCompanyEnergyConservationDelete(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete begin...");

        String enterpriseCode = requestMap.get("enterpriseCode");
        String dataIndex = requestMap.get("dataIndex");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete requestMap error");
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
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete token is null......");
            return null;
        }

        requestMap.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyEnergyConservation/delete", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete error...");
                return result;
            }
            try {
                energyConservationModelMapper.deleteByPrimaryKey(dataIndex);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyEnergyConservationServiceImpl YNCompanyEnergyConservationDelete error...");
                return result;
            }
        }
        return null;
    }

    @Override
    public List<EnergyConservationModel> list(Map<String, String> requestMap) {
        String code = requestMap.get("enterpriseCode");
        return energyConservationModelMapper.selectByCode(code);
    }
}
