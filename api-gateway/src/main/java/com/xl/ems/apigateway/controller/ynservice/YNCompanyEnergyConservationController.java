package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNCompanyEnergyConservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//用能单位节能项目情况信息Controller
@RestController
@RequestMapping(value = "/companyEnergyConservation")
public class YNCompanyEnergyConservationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyConservationController.class);

    @Autowired
    YNCompanyEnergyConservationService companyEnergyConservationService;

    //节能项目情况增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd begin...");

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
                Strings.isNullOrEmpty(projectType) || Strings.isNullOrEmpty(projectName) || Strings.isNullOrEmpty(improveMeasure)){

            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationAdd(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd end OK...");
        return RestResponse.success(result);
    }

    //节能项目情况修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate begin...");

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
                Strings.isNullOrEmpty(projectType) || Strings.isNullOrEmpty(projectName) || Strings.isNullOrEmpty(improveMeasure) ||
                Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationUpdate(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate end OK...");
        return RestResponse.success(result);
    }

    //节能项目情况删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete begin...");

        String dataIndex = requestMap.get("dataIndex");
        String enterpriseCode = requestMap.get("enterpriseCode");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationDelete(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete end OK...");
        return RestResponse.success(result);
    }
}
