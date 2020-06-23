package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ProcessConfigureModel;
import com.xl.ems.ynnhjc.service.YNProcessConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        if (0 == requestMap.size()){
            LOGGER.error("YNProcessConfigureController YNProcessConfigureAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
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

        if (0 == requestMap.size()){
            LOGGER.error("YNProcessConfigureController YNProcessConfigureUpdate requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
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

        if (0 == requestMap.size()){
            LOGGER.error("YNProcessConfigureController YNProcessConfigureDelete requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = processConfigureService.YNProcessConfigureDelete(requestMap);
        LOGGER.info("YNProcessConfigureController YNProcessConfigureDelete end OK...");
        return RestResponse.success(result);
    }

    //生产工序删除
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<ProcessConfigureModel>> list(@RequestBody Map<String, String> requestMap ){
        LOGGER.info("YNProcessConfigureController list begin...");
        if (requestMap.size() == 0){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String code = requestMap.get("enterpriseCode");
        List<ProcessConfigureModel> result = processConfigureService.list(code);
        LOGGER.info("YNProcessConfigureController list end OK...");
        return RestResponse.success(result);
    }
}
