package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import com.xl.ems.ynnhjc.model.MaterielProductModel;
import com.xl.ems.ynnhjc.service.YNCompanyMaterielProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//用能单位非能源产品信息Controller
@RestController
@RequestMapping(value = "/companyMaterielProduct")
public class YNCompanyMaterielProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyMaterielProductController.class);

    @Autowired
    YNCompanyMaterielProductService companyMaterielProductService;

    //非能源产品信息增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductAdd begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyMaterielProductController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyMaterielProductService.YNCompanyMaterielProductAdd(requestMap);
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductAdd end OK...");
        return RestResponse.success(result);
    }

    //非能源产品信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductUpdate begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyMaterielProductController YNCompanyMaterielProductUpdate requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyMaterielProductService.YNCompanyMaterielProductUpdate(requestMap);
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductUpdate end OK...");
        return RestResponse.success(result);
    }

    //非能源产品信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductDelete begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyMaterielProductController YNCompanyMaterielProductDelete requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyMaterielProductService.YNCompanyMaterielProductDelete(requestMap);
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductDelete end OK...");
        return RestResponse.success(result);
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<MaterielProductModel>> list(@RequestBody Map<String, String> requestMap){
        if (requestMap.size() == 0){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String code = requestMap.get("enterpriseCode");
        return companyMaterielProductService.getList(code);
    }
}
