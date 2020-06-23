package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.service.YNJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName YNJsonController
 * @Description TODO
 * @Author wht
 * @Date 14:57
 **/
@Controller
@RequestMapping("/regionData")
public class YNJsonController {

    @Autowired
    YNJsonService ynJsonService;

    @RequestMapping(value = "/regionList",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject regionList(){
        return  ynJsonService.regionList();
    }

    @RequestMapping(value = "/EnergyTypeUnitCollectCodes",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject EnergyTypeUnitCollectCodes(){
        return  ynJsonService.EnergyTypeUnitCollectCodes();
    }

    @RequestMapping(value = "/MetertypeJsonCodes",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject MetertypeJsonCodes(){
        return  ynJsonService.MetertypeJsonCodes();
    }
}
