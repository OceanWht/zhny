package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ProductStructureModel;
import com.xl.ems.ynnhjc.service.impl.YNCompanyProductStructureServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/companyProductStructure")
public class YNCompanyProductStructureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyProductStructureController.class);

    @Autowired
    YNCompanyProductStructureServiceImpl companyProductStructureService;

    //产品结构信息增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureAdd begin...");
        if (null == request){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureAdd request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductStructureModel productStructureModel = request.getJSONObject("data").toJavaObject(ProductStructureModel.class);
            result = companyProductStructureService.companyProductStructureAdd(productStructureModel);
            LOGGER.info("YNCompanyProductStructureController companyProductStructureAdd end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureAdd error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //产品结构信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureUpdate begin...");
        if (null == request){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureUpdate request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductStructureModel productStructureModel = request.getJSONObject("data").toJavaObject(ProductStructureModel.class);
            result = companyProductStructureService.companyProductStructureUpdate(productStructureModel);
            LOGGER.info("YNCompanyProductStructureController companyProductStructureUpdate end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureUpdate error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //产品结构信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureDelete begin...");
        if (null == request){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureDelete request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductStructureModel productStructureModel = request.getJSONObject("data").toJavaObject(ProductStructureModel.class);
            result = companyProductStructureService.companyProductStructureDelete(productStructureModel);
            LOGGER.info("YNCompanyProductStructureController companyProductStructureDelete end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureDelete error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //产品结构信息查询
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<ProductStructureModel>> select(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureSelect begin...");
        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureSelect requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = requestMap.get("enterpriseCode");
        List<ProductStructureModel> productStructureModels = companyProductStructureService.companyProductStructureSelect(enterpriseCode);
        LOGGER.info("YNCompanyProductStructureController companyProductStructureSelect end OK...");
        return RestResponse.success(productStructureModels);
    }
}
