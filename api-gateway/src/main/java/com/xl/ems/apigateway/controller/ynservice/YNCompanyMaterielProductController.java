package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.service.ynservice.YNCompanyMaterielProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String produceName = requestMap.get("produceName");
        String produceNo = requestMap.get("produceNo");
        String produceType = requestMap.get("produceType");
        String produceUnit = requestMap.get("produceUnit");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(produceName) || Strings.isNullOrEmpty(produceNo) || Strings.isNullOrEmpty(produceUnit) ){

            LOGGER.error("YNCompanyMaterielProductController YNCompanyMaterielProductAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
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

        String dataIndex = requestMap.get("dataIndex");
        String regVersion = requestMap.get("regVersion");
        String dicVersion = requestMap.get("dicVersion");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String produceName = requestMap.get("produceName");
        String produceNo = requestMap.get("produceNo");
        String produceType = requestMap.get("produceType");
        String produceUnit = requestMap.get("produceUnit");

        if(Strings.isNullOrEmpty(regVersion) || Strings.isNullOrEmpty(dicVersion) || Strings.isNullOrEmpty(enterpriseCode) ||
                Strings.isNullOrEmpty(produceName) || Strings.isNullOrEmpty(produceNo) ||
                Strings.isNullOrEmpty(produceUnit) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyMaterielProductController YNCompanyMaterielProductUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
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

        String dataIndex = requestMap.get("dataIndex");
        String enterpriseCode = requestMap.get("enterpriseCode");

        if(Strings.isNullOrEmpty(enterpriseCode) || Strings.isNullOrEmpty(dataIndex)){

            LOGGER.error("YNCompanyMaterielProductController YNCompanyMaterielProductDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = companyMaterielProductService.YNCompanyMaterielProductDelete(requestMap);
        LOGGER.info("YNCompanyMaterielProductController YNCompanyMaterielProductDelete end OK...");
        return RestResponse.success(result);
    }
}
