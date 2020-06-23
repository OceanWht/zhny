package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPathException;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.CompanyContacterModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/CompanyContacter")
public class YNCompanyContacterController {

    @Autowired
    YNCompanyContacterService ynCompanyContacterService;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyContacterController.class);

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> add(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyContacterController add begin......");
        if (request == null){
            LOGGER.error("YNCompanyContacterController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
            CompanyContacterModel companyContacterModel = request.getJSONObject("data").toJavaObject(CompanyContacterModel.class);
            res =  ynCompanyContacterService.add(companyContacterModel);
        }catch (Exception e){
            LOGGER.error("YNCompanyContacterController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNCompanyContacterController add end......");
        return res;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyContacterController update begin......");
        if (request == null){
            LOGGER.error("YNCompanyContacterController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
            CompanyContacterModel companyContacterModel = request.getJSONObject("data").toJavaObject(CompanyContacterModel.class);
            res =  ynCompanyContacterService.update(companyContacterModel);
        }catch (Exception e){
            LOGGER.error("YNCompanyContacterController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNCompanyContacterController update end......");
        return res;
    }


    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody JSONObject request){
        LOGGER.info("YNCompanyContacterController delete begin......");
        if (request == null){
            LOGGER.error("YNCompanyContacterController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
            CompanyContacterModel companyContacterModel = request.getJSONObject("data").toJavaObject(CompanyContacterModel.class);
            res =  ynCompanyContacterService.delete(companyContacterModel);
        }catch (Exception e){
            LOGGER.error("YNCompanyContacterController delete ynCompanyContacterService delete has error");
            e.printStackTrace();
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNCompanyContacterController delete end......");
        return res;
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<CompanyContacterModel>> list(@RequestBody Map<String,String> request){
        LOGGER.info("YNCompanyContacterController list begin......");
        String enterpriseCode = request.get("enterpriseCode");
        List<CompanyContacterModel> companyContacterModels = ynCompanyContacterService.list(enterpriseCode);
        LOGGER.info("YNCompanyContacterController list end......");
        return RestResponse.success(companyContacterModels);
    }

    @RequestMapping(value = "/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNCompanyContacterController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNCompanyContacterController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }
}
