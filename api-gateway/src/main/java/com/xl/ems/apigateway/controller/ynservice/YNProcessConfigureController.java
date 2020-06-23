package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNProcessConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//用能单位生产工序信息Controller
@RestController
@RequestMapping(value = "/processConfigure")
public class YNProcessConfigureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNProcessConfigureController.class);

    @Autowired
    YNProcessConfigureService processConfigureService;

    //生产工序增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNProcessConfigureController YNProcessConfigureAdd begin...");

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String processCode = requestMap.get("processCode");
        String processName = requestMap.get("processName");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(processName)){

            LOGGER.error("YNProcessConfigureController YNProcessConfigureAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = processConfigureService.YNProcessConfigureAdd(requestMap);
        LOGGER.info("YNProcessConfigureController YNProcessConfigureAdd end OK...");
        return RestResponse.success(result);
    }

    //生产工序修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNProcessConfigureController YNProcessConfigureUpdate begin...");

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String processCode = requestMap.get("processCode");
        String processName = requestMap.get("processName");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(processCode) || Strings.isNullOrEmpty(processName) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNProcessConfigureController YNProcessConfigureUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = processConfigureService.YNProcessConfigureUpdate(requestMap);
        LOGGER.info("YNProcessConfigureController YNProcessConfigureUpdate end OK...");
        return RestResponse.success(result);
    }

    //生产工序删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNProcessConfigureController YNProcessConfigureDelete begin...");

        String dataIndex = requestMap.get("dataIndex");
        String enterpriseCode = requestMap.get("enterpriseCode");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNProcessConfigureController YNProcessConfigureDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = processConfigureService.YNProcessConfigureDelete(requestMap);
        LOGGER.info("YNProcessConfigureController YNProcessConfigureDelete end OK...");
        return RestResponse.success(result);
    }
}
