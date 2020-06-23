package com.xl.ems.ynnhjc.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.DataCollectConfigureModel;
import com.xl.ems.ynnhjc.model.GaugeVerifyConfigureModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNGaugeVerifyConfigureService;

@RestController
public class YNGaugeVerifyConfigureController {
	
	@Autowired
	YNGaugeVerifyConfigureService yNGaugeVerifyConfigureService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeVerifyConfigureController.class);
	
	@RequestMapping(value = "/gaugeVerifyConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeVerifyConfigure_add(@RequestBody JSONObject request){
	    LOGGER.info("YNGaugeVerifyConfigureController add begin......");
        if (request == null){
            LOGGER.error("YNGaugeVerifyConfigureController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeVerifyConfigureModel gaugeVerifyConfigureModel = request.getJSONObject("data").toJavaObject(GaugeVerifyConfigureModel.class);
            res =  yNGaugeVerifyConfigureService.gaugeVerifyConfigure_add(gaugeVerifyConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeVerifyConfigureController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeVerifyConfigureController add end......");
        return res;
	}
	
	@RequestMapping(value = "/gaugeVerifyConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeVerifyConfigure_update(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeVerifyConfigureController update begin......");
        if (request == null){
            LOGGER.error("YNGaugeVerifyConfigureController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
        	GaugeVerifyConfigureModel gaugeVerifyConfigureModel = request.getJSONObject("data").toJavaObject(GaugeVerifyConfigureModel.class);
            res =  yNGaugeVerifyConfigureService.gaugeVerifyConfigure_update(gaugeVerifyConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeVerifyConfigureController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNGaugeVerifyConfigureController update end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeVerifyConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeVerifyConfigure_delete(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeVerifyConfigureController delete begin......");
        if (request == null){
            LOGGER.error("YNGaugeVerifyConfigureController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeVerifyConfigureModel gaugeVerifyConfigureModel = request.getJSONObject("data").toJavaObject(GaugeVerifyConfigureModel.class);
            res =  yNGaugeVerifyConfigureService.gaugeVerifyConfigure_delete(gaugeVerifyConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeVerifyConfigureController delete has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeVerifyConfigureController delete end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeVerifyConfigure/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<GaugeVerifyConfigureModel>> gaugeVerifyConfigure_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNGaugeVerifyConfigureController gaugeVerifyConfigure_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNGaugeVerifyConfigureController gaugeVerifyConfigure_list end...");
		List<GaugeVerifyConfigureModel> result = yNGaugeVerifyConfigureService.list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeVerifyConfigure/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNGaugeVerifyConfigureController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNGaugeVerifyConfigureController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }
}
