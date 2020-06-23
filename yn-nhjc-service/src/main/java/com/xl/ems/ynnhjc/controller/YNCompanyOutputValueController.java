package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ProductOutputValueModel;
import com.xl.ems.ynnhjc.service.YNCompanyOutputValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public RestResponse<JSONObject> add(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueAdd begin...");
        if (null == request){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueAdd request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductOutputValueModel productOutputValueModel = request.getJSONObject("data").toJavaObject(ProductOutputValueModel.class);
            result = companyOutputValueService.YNCompanyOutputValueAdd(productOutputValueModel);
            LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueAdd end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueAdd error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //销售收入信息修改
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueUpdate begin...");

        if (null == request){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueUpdate request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductOutputValueModel productOutputValueModel = request.getJSONObject("data").toJavaObject(ProductOutputValueModel.class);
            result = companyOutputValueService.YNCompanyOutputValueUpdate(productOutputValueModel);
            LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueUpdate end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueUpdate error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //销售收入信息删除
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueDelete begin...");

        if (null == request){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueDelete request error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> result = null;
        try {
            ProductOutputValueModel productOutputValueModel = request.getJSONObject("data").toJavaObject(ProductOutputValueModel.class);
            result = companyOutputValueService.YNCompanyOutputValueDelete(productOutputValueModel);
            LOGGER.info("YNCompanyOutputValueController YNCompanyOutputValueDelete end OK...");
        }catch (Exception e){
            LOGGER.error("YNCompanyOutputValueController YNCompanyOutputValueDelete error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        return result;
    }

    //销售收入信息查询
    @RequestMapping(value = "/select",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<ProductOutputValueModel>> select(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNCompanyOutputValueController companyOutputValueSelect begin...");
        if (0 == requestMap.size()){
            LOGGER.error("YNCompanyOutputValueController companyOutputValueSelect requestMap error");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = requestMap.get("enterpriseCode");
        List<ProductOutputValueModel> productOutputValueModels = companyOutputValueService.companyOutputValueSelect(enterpriseCode);
        LOGGER.info("YNCompanyOutputValueController companyOutputValueSelect end OK...");
        return RestResponse.success(productOutputValueModels);
    }
}
