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
import com.xl.ems.ynnhjc.model.DataCollectConfigureModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNDataCollectConfigureService;

@RestController
public class YNDataCollectConfigureController {
	
	@Autowired
	YNDataCollectConfigureService yNDataCollectConfigureService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataCollectConfigureController.class);
	
	@RequestMapping(value = "/dataCollectConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> dataCollectConfigure_add(@RequestBody JSONObject request){
	    LOGGER.info("YNDataCollectConfigureController add begin......");
        if (request == null){
            LOGGER.error("YNDataCollectConfigureController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	DataCollectConfigureModel dataCollectConfigureModel = request.getJSONObject("data").toJavaObject(DataCollectConfigureModel.class);
            res =  yNDataCollectConfigureService.dataCollectConfigure_add(dataCollectConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDataCollectConfigureController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNDataCollectConfigureController add end......");
        return res;
	}
	
	@RequestMapping(value = "/dataCollectConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> dataCollectConfigure_update(@RequestBody JSONObject request){
        LOGGER.info("YNDataCollectConfigureController update begin......");
        if (request == null){
            LOGGER.error("YNDataCollectConfigureController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
        	DataCollectConfigureModel dataCollectConfigureModel = request.getJSONObject("data").toJavaObject(DataCollectConfigureModel.class);
            res =  yNDataCollectConfigureService.dataCollectConfigure_update(dataCollectConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDataCollectConfigureController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNDataCollectConfigureController update end......");
        return res;
    }
	
	@RequestMapping(value = "/dataCollectConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> dataCollectConfigure_delete(@RequestBody JSONObject request){
        LOGGER.info("YNDataCollectConfigureController delete begin......");
        if (request == null){
            LOGGER.error("YNDataCollectConfigureController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	DataCollectConfigureModel dataCollectConfigureModel = request.getJSONObject("data").toJavaObject(DataCollectConfigureModel.class);
            res =  yNDataCollectConfigureService.dataCollectConfigure_delete(dataCollectConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDataCollectConfigureController delete has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNDataCollectConfigureController delete end......");
        return res;
    }
	
	@RequestMapping(value = "/dataCollectConfigure/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<DataCollectConfigureModel>> dataCollectConfigure_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNDataCollectConfigureController dataCollectConfigure_list begin...");
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNDataCollectConfigureController dataCollectConfigure_list end...");
		List<DataCollectConfigureModel> result = yNDataCollectConfigureService.list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/dataCollectConfigure/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNDataCollectConfigureController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNDataCollectConfigureController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }


    @RequestMapping(value = "/dataCollectConfigure/getBasicData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getBasicData(@RequestBody Map<String,String> request){
        LOGGER.info("YNDataCollectConfigureController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        String token = request.get("token");
        String uid = request.get("uid");
        JSONObject res = yNDataCollectConfigureService.getBasicData(enterpriseCode,token,uid);
        LOGGER.info("YNDataCollectConfigureController getVersion end......");
        return RestResponse.success(res);
    }
}
