package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.EnergyConservationModel;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyConservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
       // requestMap.put("token", token);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationAdd(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationAdd end OK...");
        return RestResponse.success(result);
    }

    //节能项目情况修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, String> requestMap){
     //   requestMap.put("token", token);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationUpdate(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationUpdate end OK...");
        return RestResponse.success(result);
    }

    //节能项目情况删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, String> requestMap){
      //  requestMap.put("token", token);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject result = companyEnergyConservationService.YNCompanyEnergyConservationDelete(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete end OK...");
        return RestResponse.success(result);
    }

    //节能项目情况删除
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EnergyConservationModel>> list(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete begin...");

        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<EnergyConservationModel> result = companyEnergyConservationService.list(requestMap);
        LOGGER.info("YNCompanyEnergyConservationController YNCompanyEnergyConservationDelete end OK...");
        return RestResponse.success(result);
    }
}
