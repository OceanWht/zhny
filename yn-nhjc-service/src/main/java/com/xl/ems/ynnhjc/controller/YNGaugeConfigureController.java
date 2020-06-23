package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.GaugeConfigureModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNGaugeConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class YNGaugeConfigureController {
	
	@Autowired
	YNGaugeConfigureService yNGaugeConfigureService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeConfigureController.class);
	
	@RequestMapping(value = "/gaugeConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeConfigure_add(@RequestBody JSONObject request){
	    LOGGER.info("YNGaugeConfigureController add begin......");
        if (request == null){
            LOGGER.error("YNGaugeConfigureController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeConfigureModel gaugeConfigureModel = request.getJSONObject("data").toJavaObject(GaugeConfigureModel.class);
            res =  yNGaugeConfigureService.gaugeConfigure_add(gaugeConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeConfigureController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeConfigureController add end......");
        return res;
	}
	
	@RequestMapping(value = "/gaugeConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeConfigure_update(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeConfigureController update begin......");
        if (request == null){
            LOGGER.error("YNGaugeConfigureController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
        	GaugeConfigureModel gaugeConfigureModel = request.getJSONObject("data").toJavaObject(GaugeConfigureModel.class);
            res =  yNGaugeConfigureService.gaugeConfigure_update(gaugeConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeConfigureController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeConfigureController update end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> gaugeConfigure_delete(@RequestBody JSONObject request){
        LOGGER.info("YNGaugeConfigureController delete begin......");
        if (request == null){
            LOGGER.error("YNGaugeConfigureController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	GaugeConfigureModel gaugeConfigureModel = request.getJSONObject("data").toJavaObject(GaugeConfigureModel.class);
            res =  yNGaugeConfigureService.gaugeConfigure_delete(gaugeConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNGaugeConfigureController delete has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNGaugeConfigureController delete end......");
        return res;
    }
	
	@RequestMapping(value = "/gaugeConfigure/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<GaugeConfigureModel>> gaugeConfigure_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNGaugeConfigureController dataCollectConfigure_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNGaugeConfigureController dataCollectConfigure_list end...");
		List<GaugeConfigureModel> result = yNGaugeConfigureService.list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/gaugeConfigure/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNGaugeConfigureController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNGaugeConfigureController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }

    @RequestMapping(value = "/gaugeConfigure/getDataCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<Map<String, Object>>> getDataCode(@RequestBody Map<String,String> request){
        LOGGER.info("YNGaugeConfigureController getDataCode begin......");
        String enterpriseCode = request.get("enterpriseCode");
        List<Map<String, Object>> dataCode = ynCompanyContacterService.getDataCode(enterpriseCode);
        LOGGER.info("YNGaugeConfigureController getDataCode end......");
        return RestResponse.success(dataCode);
    }
}
