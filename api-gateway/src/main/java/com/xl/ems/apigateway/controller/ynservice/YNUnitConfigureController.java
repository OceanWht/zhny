package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNUnitConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//用能单位工序单元信息Controller
@RestController
@RequestMapping(value = "/unitConfigure")
public class YNUnitConfigureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNUnitConfigureController.class);

    @Autowired
    YNUnitConfigureService unitConfigureService;

    //工序单元增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNUnitConfigureController YNUnitConfigureAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String unitCode = requestMap.get("unitCode");
        String unitName = requestMap.get("unitName");
        String processCode = requestMap.get("processCode");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(unitCode) || Strings.isNullOrEmpty(unitName)){

            LOGGER.error("YNUnitConfigureController YNUnitConfigureAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = unitConfigureService.YNUnitConfigureAdd(requestMap);
        LOGGER.info("YNUnitConfigureController YNUnitConfigureAdd end OK...");
        return RestResponse.success(result);
    }

    //工序单元修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNUnitConfigureController YNUnitConfigureUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String unitCode = requestMap.get("unitCode");
        String unitName = requestMap.get("unitName");
        String processCode = requestMap.get("processCode");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(unitCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(unitName) || Strings.isNullOrEmpty(dataIndex) ||
                Strings.isNullOrEmpty(enterpriseCode)){

            LOGGER.error("YNUnitConfigureController YNUnitConfigureUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = unitConfigureService.YNUnitConfigureUpdate(requestMap);
        LOGGER.info("YNUnitConfigureController YNUnitConfigureUpdate end OK...");
        return RestResponse.success(result);
    }

    //工序单元删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNUnitConfigureController YNUnitConfigureDelete begin...");

        String dataIndex = requestMap.get("dataIndex");
        String enterpriseCode = requestMap.get("enterpriseCode");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNUnitConfigureController YNUnitConfigureDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = unitConfigureService.YNUnitConfigureDelete(requestMap);
        LOGGER.info("YNUnitConfigureController YNUnitConfigureDelete end OK...");
        return RestResponse.success(result);
    }
}
