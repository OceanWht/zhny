package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNCompanyOutputValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//用能单位产值、增加值、销售收入信息Controller
@RestController
@RequestMapping(value = "/companyOutputValue")
public class YNCompanyOutputValueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyOutputValueController.class);

    @Autowired
    YNCompanyOutputValueService companyOutputValueService;

    //销售收入信息增加
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueAdd begin...");

        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyOutputValueService.YNCompanyOutputValueAdd(requestMap);
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueAdd end OK...");
        return RestResponse.success(result);
    }

    //销售收入信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueUpdate begin...");

        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyOutputValueService.YNCompanyOutputValueUpdate(requestMap);
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueUpdate end OK...");
        return RestResponse.success(result);
    }

    //销售收入信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody Map<String, Object> requestMap){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueDelete begin...");

        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyOutputValueService.YNCompanyOutputValueDelete(requestMap);
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueDelete end OK...");
        return RestResponse.success(result);
    }
}
