package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.ProductStructureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.ProductStructureModel;
import com.xl.ems.ynnhjc.service.YNCompanyProductStructureService;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class YNCompanyProductStructureServiceImpl implements YNCompanyProductStructureService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyProductStructureServiceImpl.class);

    @Autowired
    ProductStructureModelMapper productStructureModelMapper;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    GenericRest genericRest;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    //产品结构信息增加
    @Override
    public RestResponse<JSONObject> companyProductStructureAdd(ProductStructureModel productStructureModel) {
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd begin...");

        if (productStructureModel == null) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd productStructureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productStructureModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyProductStructureServiceImpl companyProductStructureAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productStructureModel", productStructureModel);
        body.put("softUrl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyProductStructure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd error...");
                return RestResponse.error(result);
            }
            try {
                Map<String,String> data = (Map<String, String>) result.get("data");
                String dataIndex = data.get("dataIndex");
                productStructureModel.setDataindex(dataIndex);
                productStructureModelMapper.insertSelective(productStructureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureAdd fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //产品结构信息修改
    @Override
    public RestResponse<JSONObject> companyProductStructureUpdate(ProductStructureModel productStructureModel){
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate begin...");

        if (productStructureModel == null) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate productStructureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productStructureModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyProductStructureServiceImpl companyProductStructureUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productStructureModel", productStructureModel);
        body.put("softUrl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyProductStructure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate error...");
                return RestResponse.error(result);
            }
            try {
                productStructureModelMapper.updateByPrimaryKeySelective(productStructureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureUpdate fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //产品结构信息删除
    @Override
    public RestResponse<JSONObject> companyProductStructureDelete(ProductStructureModel productStructureModel){
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete begin...");

        if (productStructureModel == null) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete productStructureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productStructureModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyProductStructureServiceImpl companyProductStructureDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productStructureModel", productStructureModel);
        body.put("softUrl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyProductStructure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete error...");
                return RestResponse.error(result);
            }
            try {
                productStructureModelMapper.deleteByPrimaryKey(productStructureModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureDelete fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //产品结构信息查询
    @Override
    public List<ProductStructureModel> companyProductStructureSelect(String enterpriseCode) {
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureSelect begin......");
        List<ProductStructureModel> productStructureModels = productStructureModelMapper.selectByPrimaryKey(enterpriseCode);
        LOGGER.info("YNCompanyProductStructureServiceImpl companyProductStructureSelect end......");
        return productStructureModels;
    }
}
