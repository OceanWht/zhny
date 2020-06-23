package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.ProductOutputValueModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.ProductOutputValueModel;
import com.xl.ems.ynnhjc.service.YNCompanyOutputValueService;
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

//用能单位产值、增加值、销售收入信息接口实现类
@Service
public class YNCompanyOutputValueServiceImpl implements YNCompanyOutputValueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyOutputValueServiceImpl.class);

    @Autowired
    ProductOutputValueModelMapper productOutputValueModelMapper;

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

    //销售收入信息增加
    @Override
    public RestResponse<JSONObject> YNCompanyOutputValueAdd(ProductOutputValueModel productOutputValueModel) {
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd begin...");

        if (productOutputValueModel == null) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd productOutputValueModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productOutputValueModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productOutputValueModel", productOutputValueModel);
        body.put("softUrl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyOutputValue/add", body, new ParameterizedTypeReference<RestResponse<JSONObject>>() {
        });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd error...");
                return RestResponse.error(result);
            }
            try {
                Map<String,String> data = (Map<String, String>) result.get("data");
                String dataIndex = data.get("dataIndex");
                productOutputValueModel.setDataindex(dataIndex);
                productOutputValueModelMapper.insertSelective(productOutputValueModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueAdd fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //销售收入信息修改
    @Override
    public RestResponse<JSONObject> YNCompanyOutputValueUpdate(ProductOutputValueModel productOutputValueModel) {
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate begin...");

        if (productOutputValueModel == null) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate productOutputValueModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productOutputValueModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productOutputValueModel", productOutputValueModel);
        body.put("softUrl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyOutputValue/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate error...");
                return RestResponse.error(result);
            }
            try {
                productOutputValueModelMapper.updateByPrimaryKeySelective(productOutputValueModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueUpdate fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //销售收入信息删除
    @Override
    public RestResponse<JSONObject> YNCompanyOutputValueDelete(ProductOutputValueModel productOutputValueModel) {
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete begin...");

        if (productOutputValueModel == null) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete productOutputValueModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = productOutputValueModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("productOutputValueModel", productOutputValueModel);
        body.put("softUrl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> responseEntity = genericRest.post(apiUrl + "/companyOutputValue/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if(responseEntity.getStatusCode().equals(HttpStatus.OK)){
            JSONObject result = responseEntity.getBody().getResult();
            Integer responseCode = result.getInteger("responseCode");
            String responseMessage = result.getString("responseMessage");
            if(200 != responseCode){
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete error...");
                return RestResponse.error(result);
            }
            try {
                productOutputValueModelMapper.deleteByPrimaryKey(productOutputValueModel);
                result.put("responseCode",responseCode);
                result.put("responseMessage",responseMessage);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete end SUCCESS...");
                return RestResponse.success(result);
            }catch (Exception e){
                e.printStackTrace();
                String message = e.getMessage();
                result.put("responseCode",responseCode);
                result.put("responseMessage",message);
                LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete error...");
                return RestResponse.error(result);
            }
        }
        LOGGER.info("YNCompanyOutputValueServiceImpl YNCompanyOutputValueDelete fail...");
        return RestResponse.error(RestCode.UPDATE_FAIL);
    }

    //销售收入信息查询
    @Override
    public List<ProductOutputValueModel> companyOutputValueSelect(String enterpriseCode) {
        LOGGER.info("YNCompanyOutputValueServiceImpl companyOutputValueSelect begin......");
        List<ProductOutputValueModel> productOutputValueModels = productOutputValueModelMapper.selectByPrimaryKey(enterpriseCode);
        LOGGER.info("YNCompanyOutputValueServiceImpl companyOutputValueSelect end......");
        return productOutputValueModels;
    }
}
