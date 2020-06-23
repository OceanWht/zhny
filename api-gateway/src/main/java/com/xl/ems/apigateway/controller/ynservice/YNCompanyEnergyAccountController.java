package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNCompanyEnergyAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String accountType = requestMap.get("accountType");
        String accountNo = requestMap.get("accountNo");
        String accountName = requestMap.get("accountName");
        String provider = requestMap.get("provider");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(accountType) || Strings.isNullOrEmpty(accountNo) || Strings.isNullOrEmpty(accountName) ||
                Strings.isNullOrEmpty(provider)){

            LOGGER.error("YNCompanyEnergyAccountController YNCompanyEnergyAccountAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
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
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
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

        String dataIndex = requestMap.get("dataIndex");
        String enterpriseCode = requestMap.get("enterpriseCode");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyAccountController YNCompanyEnergyAccountDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyEnergyAccountService.YNCompanyEnergyAccountDelete(requestMap);
        LOGGER.info("YNCompanyEnergyAccountController YNCompanyEnergyAccountDelete end OK...");
        return RestResponse.success(result);
    }
}