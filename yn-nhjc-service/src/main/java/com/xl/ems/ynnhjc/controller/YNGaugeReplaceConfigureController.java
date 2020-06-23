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
import com.xl.ems.ynnhjc.model.GaugeReplaceConfigure;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNDataCollectConfigureService;
import com.xl.ems.ynnhjc.service.YNGaugeReplaceConfigureService;

@RestController
public class YNGaugeReplaceConfigureController {
	
	@Autowired
	YNGaugeReplaceConfigureService yNGaugeReplaceConfigureService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataCollectConfigureController.class);
	
	@RequestMapping(value = "/gaugeReplaceConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeReplaceConfigure_add(@RequestBody JSONObject request){
	    LOGGER.info("YNGaugeReplaceConfigureController add begin......");
        if (request == null){
            LOGGER.error("YNGaugeReplaceConfigureController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeReplaceConfigure gaugeReplaceConfigure = request.getJSONObject("data").toJavaObject(GaugeReplaceConfigure.class);
            res =  yNGaugeReplaceConfigureService.gaugeReplaceConfigure_add(gaugeReplaceConfigure);
        }catch (Exception e){
            LOGGER.error("YNGaugeReplaceConfigureController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeReplaceConfigureController add end......");
        return res;
	}
	
	@RequestMapping(value = "/gaugeReplaceConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeReplaceConfigure_update(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeReplaceConfigureController update begin......");
        if (request == null){
            LOGGER.error("YNGaugeReplaceConfigureController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
        	GaugeReplaceConfigure gaugeReplaceConfigure = request.getJSONObject("data").toJavaObject(GaugeReplaceConfigure.class);
            res =  yNGaugeReplaceConfigureService.gaugeReplaceConfigure_update(gaugeReplaceConfigure);
        }catch (Exception e){
            LOGGER.error("YNGaugeReplaceConfigureController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNGaugeReplaceConfigureController update end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeReplaceConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeReplaceConfigure_delete(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeReplaceConfigureController delete begin......");
        if (request == null){
            LOGGER.error("YNGaugeReplaceConfigureController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeReplaceConfigure gaugeReplaceConfigure = request.getJSONObject("data").toJavaObject(GaugeReplaceConfigure.class);
            res =  yNGaugeReplaceConfigureService.gaugeReplaceConfigure_delete(gaugeReplaceConfigure);
        }catch (Exception e){
            LOGGER.error("YNGaugeReplaceConfigureController delete has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeReplaceConfigureController delete end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeReplaceConfigure/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<GaugeReplaceConfigure>> gaugeReplaceConfigure_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNGaugeReplaceConfigureController gaugeReplaceConfigure_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNGaugeReplaceConfigureController gaugeReplaceConfigure_list end...");
		List<GaugeReplaceConfigure> result = yNGaugeReplaceConfigureService.list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeReplaceConfigure/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNGaugeReplaceConfigureController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNGaugeReplaceConfigureController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }
}
