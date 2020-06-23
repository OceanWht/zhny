package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.MaterielProductModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import com.xl.ems.ynnhjc.model.MaterielProductModel;
import com.xl.ems.ynnhjc.service.YNCompanyMaterielProductService;
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

//用能单位非能源产品信息接口实现类
@Service
public class YNCompanyMaterielProductServiceImpl implements YNCompanyMaterielProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyMaterielProductServiceImpl.class);

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    MaterielProductModelMapper materielProductModelMapper;


    @Autowired
    AKModelMapper akModelMapper;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    //非能源产品信息增加
    public JSONObject YNCompanyMaterielProductAdd(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String produceName = requestMap.get("produceName");
        String produceNo = requestMap.get("produceNo");
        String produceType = requestMap.get("produceType");
        String produceUnit = requestMap.get("produceUnit");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(produceName) || Strings.isNullOrEmpty(produceNo) || Strings.isNullOrEmpty(produceUnit)){

            LOGGER.error("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd requestMap error");
            return null;
        }

        MaterielProductModel materielProductModel = new MaterielProductModel();
        materielProductModel.setRegversion(regVersion);
        materielProductModel.setDicversion(dicVersion);
        materielProductModel.setEnterprisecode(enterpriseCode);
        materielProductModel.setProducename(produceName);
        materielProductModel.setProduceno(produceNo);
        materielProductModel.setProducetype(produceType);
        materielProductModel.setProduceunit(produceUnit);
        materielProductModel.setRemark(requestMap.get("remark"));

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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyMaterielProduct/add", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd error...");
                return result;
            }
            Map<String,String> data = (Map<String, String>) json.get("data");
            String dataIndex = data.get("dataIndex");
            materielProductModel.setDataindex(dataIndex);
            try {
                materielProductModelMapper.insertSelective(materielProductModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductAdd error...");
                return result;
            }
        }
        return null;
    }

    //非能源产品信息修改
    public JSONObject YNCompanyMaterielProductUpdate(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String produceName = requestMap.get("produceName");
        String produceNo = requestMap.get("produceNo");
        String produceType = requestMap.get("produceType");
        String produceUnit = requestMap.get("produceUnit");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(produceName) || Strings.isNullOrEmpty(produceNo) ||
                Strings.isNullOrEmpty(produceUnit) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate requestMap error");
            return null;
        }

        MaterielProductModel materielProductModel = new MaterielProductModel();
        materielProductModel.setDataindex(dataIndex);
        materielProductModel.setRegversion(regVersion);
        materielProductModel.setDicversion(dicVersion);
        materielProductModel.setEnterprisecode(enterpriseCode);
        materielProductModel.setProducename(produceName);
        materielProductModel.setProduceno(produceNo);
        materielProductModel.setProducetype(produceType);
        materielProductModel.setProduceunit(produceUnit);
        materielProductModel.setRemark(requestMap.get("remark"));

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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyMaterielProduct/update", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate error...");
                return result;
            }
            try {
                materielProductModelMapper.updateByPrimaryKeySelective(materielProductModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductUpdate error...");
                return result;
            }
        }
        return null;
    }

    //非能源产品信息删除
    public JSONObject YNCompanyMaterielProductDelete(Map<String, String> requestMap) {
        LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete begin...");

        String enterpriseCode = requestMap.get("enterpriseCode");
        String dataIndex = requestMap.get("dataIndex");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete requestMap error");
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
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyMaterielProduct/delete", requestMap, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
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
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete error...");
                return result;
            }
            try {
                materielProductModelMapper.deleteByPrimaryKey(dataIndex);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete end SUCCESS...");
                return result;
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyMaterielProductServiceImpl YNCompanyMaterielProductDelete error...");
                return result;
            }
        }
        return null;
    }

    @Override
    public RestResponse<List<MaterielProductModel>> getList(String code) {
        List<MaterielProductModel> list = materielProductModelMapper.getList(code);
        return RestResponse.success(list);
    }
}
