package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.UnitConfigureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.UnitConfigureModel;
import com.xl.ems.ynnhjc.service.YNUnitConfigureService;
import com.xl.ems.ynnhjc.utils.DateUtils;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

//用能单位工序单元信息接口实现类
@Service
public class YNUnitConfigureServiceImpl implements YNUnitConfigureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNUnitConfigureServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;


    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    UnitConfigureModelMapper unitConfigureModelMapper;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    //工序单元增加
    public JSONObject YNUnitConfigureAdd(Map<String, String> requestMap) {
        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String unitCode = requestMap.get("unitCode");
        String unitName = requestMap.get("unitName");
        String processCode = requestMap.get("processCode");
        String commDate = requestMap.get("commDate");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(unitCode) || Strings.isNullOrEmpty(unitName)){

            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureAdd requestMap error");
            return null;
        }

        UnitConfigureModel unitConfigureModel = new UnitConfigureModel();
        unitConfigureModel.setRegversion(regVersion);
        unitConfigureModel.setDicversion(dicVersion);
        unitConfigureModel.setEnterprisecode(enterpriseCode);
        unitConfigureModel.setUnitcode(unitCode);
        unitConfigureModel.setUnitname(unitName);
        unitConfigureModel.setProcesscode(processCode);
        unitConfigureModel.setDesignedcapacity(requestMap.get("designedCapacity"));
        unitConfigureModel.setRemark(requestMap.get("remark"));
        if(!Strings.isNullOrEmpty(commDate)){

            Date date = DateUtils.parseDate(commDate);
            unitConfigureModel.setCommdate(date);
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
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureAdd akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureAdd token is null......");
            return null;
        }

        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/unitConfigure/add", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd error...");
                return result;
            }
            Map<String,String> data = (Map<String, String>) json.get("data");
            String dataIndex = data.get("dataIndex");
            unitConfigureModel.setDataindex(dataIndex);
            try {
                unitConfigureModelMapper.insertSelective(unitConfigureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureAdd error...");
                return result;
            }
        }
        return null;
    }

    //工序单元修改
    public JSONObject YNUnitConfigureUpdate(Map<String, String> requestMap) {
        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String unitCode = requestMap.get("unitCode");
        String unitName = requestMap.get("unitName");
        String processCode = requestMap.get("processCode");
        String commDate = requestMap.get("commDate");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(unitCode) || Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(unitName) ||
                Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureUpdate requestMap error");
            return null;
        }

        UnitConfigureModel unitConfigureModel = new UnitConfigureModel();
        unitConfigureModel.setDataindex(dataIndex);
        unitConfigureModel.setRegversion(regVersion);
        unitConfigureModel.setDicversion(dicVersion);
        unitConfigureModel.setEnterprisecode(enterpriseCode);
        unitConfigureModel.setUnitcode(unitCode);
        unitConfigureModel.setUnitname(unitName);
        unitConfigureModel.setProcesscode(processCode);
        unitConfigureModel.setDesignedcapacity(requestMap.get("designedCapacity"));
        unitConfigureModel.setRemark(requestMap.get("remark"));
        if(!Strings.isNullOrEmpty(commDate)){

            Date date = DateUtils.parseDate(commDate);
            unitConfigureModel.setCommdate(date);
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
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureUpdate akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureUpdate token is null......");
            return null;
        }

        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/unitConfigure/update", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate error...");
                return result;
            }
            try {
                unitConfigureModelMapper.updateByPrimaryKeySelective(unitConfigureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureUpdate error...");
                return result;
            }
        }
        return null;
    }

    //工序单元删除
    public JSONObject YNUnitConfigureDelete(Map<String, String> requestMap) {
        LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete begin...");

        String enterpriseCode = requestMap.get("enterpriseCode");
        String dataIndex = requestMap.get("dataIndex");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureDelete requestMap error");
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
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureDelete akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNUnitConfigureServiceImpl YNUnitConfigureDelete token is null......");
            return null;
        }

        requestMap.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/unitConfigure/delete", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete error...");
                return result;
            }
            try {
                unitConfigureModelMapper.deleteByPrimaryKey(dataIndex);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNUnitConfigureServiceImpl YNUnitConfigureDelete error...");
                return result;
            }
        }
        return null;
    }

    @Override
    public List<UnitConfigureModel> list(String code) {
        return unitConfigureModelMapper.getList(code);
    }
}
