package com.xl.ems.userservice.controller;


import com.google.common.base.Strings;
import com.xl.ems.userservice.bean.UnitGroupMounthDataBean;
import com.xl.ems.userservice.common.RestCode;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.model.XlUnitCalcGroupModel;
import com.xl.ems.userservice.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    HomeService homeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);



    @RequestMapping(value = "/getAllEnergyType",method = RequestMethod.POST)
    @ResponseBody
    public  RestResponse<List<XlUnitCalcGroupModel>> getAllEnergyType(HttpServletRequest request, @RequestBody Map<String,String> requestMap){
        LOGGER.info("HomeController getAllEnergyType......begin");
        String token = request.getHeader("token");
        String uid = requestMap.get("uid");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(uid)){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        //TODO 验证token 有效性 后面再补

        List<XlUnitCalcGroupModel> calcGroupModels = homeService.getAllEnergyType(uid);
        if (CollectionUtils.isEmpty(calcGroupModels)){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        LOGGER.info("HomeController getAllEnergyType......end");
        return RestResponse.success(calcGroupModels);
    }


    @RequestMapping(value = "/getEnergyMonthData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupMounthDataBean>> getEnergyMonthData(HttpServletRequest request, @RequestBody Map<String,String> requestMap){
        LOGGER.info("HomeController getEnergyMonthData......begin");
        String token = request.getHeader("token");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String dataid = requestMap.get("dataid");
        String uid = requestMap.get("uid");

        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(sdt)
        ||Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(uid)){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);


        List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = homeService.getEnergyMonthData(requestMap);
        if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)){
            LOGGER.error("HomeController getEnergyMonthData......unitGroupMounthDataBeans error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        LOGGER.info("HomeController getEnergyMonthData......end");

        return RestResponse.success(unitGroupMounthDataBeans);
    }

    @RequestMapping(value = "/getEnergyDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupMounthDataBean>> getEnergyDayData(HttpServletRequest request, @RequestBody Map<String,String> requestMap) {
        LOGGER.info("HomeController getEnergyDayData......begin");
        String token = request.getHeader("token");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String dataid = requestMap.get("dataid");
        String uid = requestMap.get("uid");
        String io = requestMap.get("io");

        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(sdt)
                ||Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(uid)
                ||Strings.isNullOrEmpty(io)){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        requestMap.put("token",token);
        //上月到 本月本日的数据
        List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = homeService.getEnergyDayData(requestMap);
        if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)){
            LOGGER.error("HomeController getEnergyDayData......unitGroupMounthDataBeans error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        LOGGER.info("HomeController getEnergyDayData......end");

        return RestResponse.success(unitGroupMounthDataBeans);

    }
}
