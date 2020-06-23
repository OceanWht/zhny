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
import com.xl.ems.ynnhjc.model.DeviceConfigureModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNDeviceConfigureService;

@RestController
public class YNDeviceConfigureController {
	
	@Autowired
	YNDeviceConfigureService yNDeviceConfigureService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDeviceConfigureController.class);
	
	@RequestMapping(value = "/deviceConfigure/add",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DeviceConfigure_add(@RequestBody JSONObject request){
	    LOGGER.info("YNDeviceConfigureController add begin......");
        if (request == null){
            LOGGER.error("YNDeviceConfigureController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	DeviceConfigureModel deviceConfigureModel = request.getJSONObject("data").toJavaObject(DeviceConfigureModel.class);
            res =  yNDeviceConfigureService.DeviceConfigure_add(deviceConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDeviceConfigureController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNDeviceConfigureController add end......");
        return res;
	}
	
	@RequestMapping(value = "/deviceConfigure/update",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody JSONObject request){
        LOGGER.info("YNDeviceConfigureController update begin......");
        if (request == null){
            LOGGER.error("YNDeviceConfigureController update request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;

        try {
        	DeviceConfigureModel deviceConfigureModel = request.getJSONObject("data").toJavaObject(DeviceConfigureModel.class);
            res =  yNDeviceConfigureService.DeviceConfigure_update(deviceConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDeviceConfigureController update  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }


        LOGGER.info("YNDeviceConfigureController update end......");
        return res;
    }
	
	@RequestMapping(value = "/deviceConfigure/delete",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> delete(@RequestBody JSONObject request){
        LOGGER.info("YNDeviceConfigureController delete begin......");
        if (request == null){
            LOGGER.error("YNDeviceConfigureController delete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	DeviceConfigureModel deviceConfigureModel = request.getJSONObject("data").toJavaObject(DeviceConfigureModel.class);
            res =  yNDeviceConfigureService.DeviceConfigure_delete(deviceConfigureModel);
        }catch (Exception e){
            LOGGER.error("YNDeviceConfigureController delete yNDeviceConfigureService delete has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNDeviceConfigureController delete end......");
        return res;
    }
	
	@RequestMapping(value = "/deviceConfigure/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<DeviceConfigureModel>> DeviceConfigure_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_list begin...");
		if(requestMap == null || requestMap.size() == 0){
			LOGGER.error("YNDeviceConfigureController DeviceConfigure_list requestMap error");
			/*return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);*/
		}
		String enterpriseCode = requestMap.get("enterpriseCode");
		LOGGER.info("YNDeviceConfigureController DeviceConfigure_list end...");
		List<DeviceConfigureModel> result = yNDeviceConfigureService.list(enterpriseCode);
	    return RestResponse.success(result);
	}
	
	@RequestMapping(value = "/deviceConfigure/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNCompanyContacterController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNCompanyContacterController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }
}
