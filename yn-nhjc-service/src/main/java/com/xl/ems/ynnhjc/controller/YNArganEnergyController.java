package com.xl.ems.ynnhjc.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.ArganEnergyConsumeModel;
import com.xl.ems.ynnhjc.model.ArganEnergyIdcModel;
import com.xl.ems.ynnhjc.model.ArganEnergyWarmModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNArganEnergyService;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;

@RestController
public class YNArganEnergyController {

	@Autowired
	YNArganEnergyService yNArganEnergyService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNArganEnergyController.class);
	
	@RequestMapping(value = "/arganEnergy/addEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addEnergy(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController add begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyConsumeModel arganEnergyConsumeModel = request.getJSONObject("data").toJavaObject(ArganEnergyConsumeModel.class);
            res =  yNArganEnergyService.arganEnergy_addEnergy(arganEnergyConsumeModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController add end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/updateEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_updateEnergy(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController update begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyConsumeModel arganEnergyConsumeModel = request.getJSONObject("data").toJavaObject(ArganEnergyConsumeModel.class);
            res =  yNArganEnergyService.arganEnergy_updateEnergy(arganEnergyConsumeModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController update end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/deleteEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_deleteEnergy(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController delete begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyConsumeModel arganEnergyConsumeModel = request.getJSONObject("data").toJavaObject(ArganEnergyConsumeModel.class);
            res =  yNArganEnergyService.arganEnergy_deleteEnergy(arganEnergyConsumeModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController delete  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController delete end......");
        return res;
	}
	
	
	@RequestMapping(value = "/arganEnergy/addIDC",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addIDC(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController add begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyIdcModel arganEnergyIdcModel = request.getJSONObject("data").toJavaObject(ArganEnergyIdcModel.class);
            res =  yNArganEnergyService.arganEnergy_addIDC(arganEnergyIdcModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController add end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/updateIDC",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_updateIDC(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController update begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyIdcModel arganEnergyIdcModel = request.getJSONObject("data").toJavaObject(ArganEnergyIdcModel.class);
            res =  yNArganEnergyService.arganEnergy_updateIDC(arganEnergyIdcModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController update end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/deleteIDC",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_deleteIDC(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController delete begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyIdcModel arganEnergyIdcModel = request.getJSONObject("data").toJavaObject(ArganEnergyIdcModel.class);
            res =  yNArganEnergyService.arganEnergy_deleteIDC(arganEnergyIdcModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController delete  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController delete end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/addWarm",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_addWarm(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController add begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyWarmModel arganEnergyWarmModel = request.getJSONObject("data").toJavaObject(ArganEnergyWarmModel.class);
            res =  yNArganEnergyService.arganEnergy_addWarm(arganEnergyWarmModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController add end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/updateWarm",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_updateWarm(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController update begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyWarmModel arganEnergyWarmModel = request.getJSONObject("data").toJavaObject(ArganEnergyWarmModel.class);
            res =  yNArganEnergyService.arganEnergy_updateWarm(arganEnergyWarmModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController update end......");
        return res;
	}
	
	@RequestMapping(value = "/arganEnergy/deleteWarm",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> ArganEnergy_deleteWarm(@RequestBody JSONObject request){
	    LOGGER.info("YNArganEnergyController delete begin......");
        if (request == null){
            LOGGER.error("YNArganEnergyController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	ArganEnergyWarmModel arganEnergyWarmModel = request.getJSONObject("data").toJavaObject(ArganEnergyWarmModel.class);
            res =  yNArganEnergyService.arganEnergy_deleteWarm(arganEnergyWarmModel);
        }catch (Exception e){
            LOGGER.error("YNArganEnergyController delete  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNArganEnergyController delete end......");
        return res;
	}
	
	@RequestMapping(value = "/addEnergy/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<ArganEnergyConsumeModel>> addEnergy_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNArganEnergyController addEnergy_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNArganEnergyController addEnergy_list end...");
		List<ArganEnergyConsumeModel> result = yNArganEnergyService.addEnergy_list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/addIDC/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<ArganEnergyIdcModel>> addIDC_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNArganEnergyController addIDC_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNArganEnergyController addIDC_list end...");
		List<ArganEnergyIdcModel> result = yNArganEnergyService.addIDC_list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/addWarm/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<ArganEnergyWarmModel>> addWarm_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNArganEnergyController addWarm_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNArganEnergyController addWarm_list end...");
		List<ArganEnergyWarmModel> result = yNArganEnergyService.addWarm_list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/arganEnergy/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNArganEnergyController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNArganEnergyController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }
}
