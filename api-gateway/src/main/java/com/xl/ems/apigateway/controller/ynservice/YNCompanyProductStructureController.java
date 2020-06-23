package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNCompanyProductService;
import com.xl.ems.apigateway.service.ynservice.impl.YNEnterpriseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//用能单位产品结构信息Controller
@RestController
@RequestMapping(value = "/companyProductStructure")
public class YNCompanyProductStructureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseServiceImpl.class);

    @Autowired
    YNCompanyProductService companyProductService;

    //产品结构信息增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureAdd begin...");
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        JSONObject result = companyProductService.companyProductStructureAdd(requestMap);
        LOGGER.info("YNCompanyProductStructureController companyProductStructureAdd end OK...");
        return RestResponse.success(result);
    }

    //产品结构信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureUpdate begin...");
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        JSONObject result = companyProductService.companyProductStructureUpdate(requestMap);
        LOGGER.info("YNCompanyProductStructureController companyProductStructureUpdate end OK...");
        return RestResponse.success(result);
    }

    //产品结构信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyProductStructureController companyProductStructureDelete begin...");
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyProductStructureController companyProductStructureDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        JSONObject result = companyProductService.companyProductStructureDelete(requestMap);
        LOGGER.info("YNCompanyProductStructureController companyProductStructureDelete end OK...");
        return RestResponse.success(result);
    }
}