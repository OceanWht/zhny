package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//用能单位水、电、燃气户号信息Controller
@RestController
@RequestMapping(value = "/companyEnergyAccount")
public class YNCompanyEnergyAccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyAccountController.class);

    @Autowired
    YNCompanyEnergyAccountService companyEnergyAccountService;

    //能源账号信息增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountAdd begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyAccountService.YNCompanyEnergyAccountAdd(requestMap);
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountAdd end OK...");
        return RestResponse.success(result);
    }

    //能源账号信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountUpdate begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyAccountService.YNCompanyEnergyAccountUpdate(requestMap);
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountUpdate end OK...");
        return RestResponse.success(result);
    }

    //能源账号信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountDelete begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyAccountService.YNCompanyEnergyAccountDelete(requestMap);
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountDelete end OK...");
        return RestResponse.success(result);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EnergyAccountModel>> list(@RequestBody Map<String, String> requestMap){
        if (requestMap.size() == 0){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String code = requestMap.get("enterpriseCode");
        return companyEnergyAccountService.getList(code);
    }
}