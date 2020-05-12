package com.xl.ems.userservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.common.RestCode;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.service.EnergyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/EnergyController")
public class EnergyController {


    @Autowired
    EnergyService energyService;

    @RequestMapping(value = "/overview",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> overview (HttpServletRequest request, @RequestBody Map<String,String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyService.overview(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);
    }

    /**
     * 获取企业用能单元树
     * @param request
     * @param requestMap
     * @return
     */
    @RequestMapping(value = "/unitTree",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> unitTree(HttpServletRequest request, @RequestBody Map<String,String> requestMap){

        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyService.unitTree(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);
    }

    @RequestMapping(value = "/getAllData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getAllData(HttpServletRequest request, @RequestBody Map<String,String> requestMap){
        String token = request.getHeader("token");
        if (Strings.isNullOrEmpty(token) || requestMap == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);

        JSONObject response = energyService.getAllData(requestMap);
        if (response == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        return RestResponse.success(response);
    }
}
