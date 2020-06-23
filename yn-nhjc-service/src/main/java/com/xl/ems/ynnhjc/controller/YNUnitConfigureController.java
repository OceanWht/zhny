package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.UnitConfigureModel;
import com.xl.ems.ynnhjc.service.YNUnitConfigureService;
import com.xl.ems.ynnhjc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

        if (0 == requestMap.size()){
            LOGGER.error("YNUnitConfigureController YNUnitConfigureAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
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

        if (0 == requestMap.size()){
            LOGGER.error("YNUnitConfigureController YNUnitConfigureUpdate requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
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

        if (0 == requestMap.size()){
            LOGGER.error("YNUnitConfigureController YNUnitConfigureDelete requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = unitConfigureService.YNUnitConfigureDelete(requestMap);
        LOGGER.info("YNUnitConfigureController YNUnitConfigureDelete end OK...");
        return RestResponse.success(result);
    }

    //工序单元删除
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitConfigureModel>> list(@RequestBody Map<String, String> requestMap ){
        LOGGER.info("YNUnitConfigureController YNUnitConfigureDelete begin...");
        if (requestMap.size() == 0){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String code = requestMap.get("enterpriseCode");
        List<UnitConfigureModel> result = unitConfigureService.list(code);
        LOGGER.info("YNUnitConfigureController YNUnitConfigureDelete end OK...");
        return RestResponse.success(result);
    }
}
