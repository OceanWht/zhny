package com.xl.ems.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.common.RestCode;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.service.impl.EnergyAnalysServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/EnergyAnalysController")
public class EnergyAnalysController {

    @Autowired
    EnergyAnalysServiceImpl energyAnalysService;


    @RequestMapping(value = "/unitTreeTWO",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> unitTreeTWO(HttpServletRequest request,@RequestBody Map<String, String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyAnalysService.unitTreeTWO(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);

    }


    @RequestMapping(value = "/getAnalogDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getAnalogDayData(HttpServletRequest request,@RequestBody Map<String, String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyAnalysService.getAnalogDayData(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);

    }

    @RequestMapping(value = "/getUnitDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getUnitDayData(HttpServletRequest request,@RequestBody Map<String, String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyAnalysService.getUnitDayData(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);

    }


    @RequestMapping(value = "/getAnalogMonthData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getAnalogMonthData(HttpServletRequest request,@RequestBody Map<String, String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyAnalysService.getAnalogMonthData(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);

    }
}
