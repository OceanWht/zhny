package com.xl.ems.ynnhjc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.xl.ems.ynnhjc.model.EnterpriseEnergyHistoryModel;
import com.xl.ems.ynnhjc.utils.DateUtils;
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
import com.xl.ems.ynnhjc.model.EnterpriseEnergyModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNDataEnterpriseEnergyService;

@RestController
public class YNDataEnterpriseEnergyController {
	
	@Autowired
	YNDataEnterpriseEnergyService yNDataEnterpriseEnergyService;
	
	@Autowired
	YNCompanyContacterService ynCompanyContacterService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataEnterpriseEnergyController.class);
	
	@RequestMapping(value = "/dataEnterpriseEnergy",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<JSONObject> DataEnterpriseEnergy(@RequestBody JSONObject request){
	    LOGGER.info("YNDataEnterpriseEnergyController add begin......");
        if (request == null){
            LOGGER.error("YNDataEnterpriseEnergyController add request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = null;
        try {
        	EnterpriseEnergyModel enterpriseEnergyModel = request.getJSONObject("data").toJavaObject(EnterpriseEnergyModel.class);
            res =  yNDataEnterpriseEnergyService.dataEnterpriseEnergy(enterpriseEnergyModel);
        }catch (Exception e){
            LOGGER.error("YNDataEnterpriseEnergyController add  has error");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNDataEnterpriseEnergyController add end......");
        return res;
	}
	
	@RequestMapping(value = "/dataEnterpriseEnergy/list",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<EnterpriseEnergyModel>> dataEnterpriseEnergy_list(@RequestBody Map<String, String> requestMap){
		LOGGER.info("YNDataEnterpriseEnergyController dataEnterpriseEnergy_list begin...");
		LOGGER.info("YNDataEnterpriseEnergyController dataEnterpriseEnergy_list end...");
		List<EnterpriseEnergyModel> result = yNDataEnterpriseEnergyService.list(requestMap);
	    return RestResponse.success(result);
	}


    @RequestMapping(value = "/dataEnterpriseEnergy/listHistory",method = {RequestMethod.POST})
    @ResponseBody
    public RestResponse<List<EnterpriseEnergyHistoryModel>> listHistory(@RequestBody Map<String, String> requestMap){
        LOGGER.info("YNDataEnterpriseEnergyController dataEnterpriseEnergy_list begin...");
        LOGGER.info("YNDataEnterpriseEnergyController dataEnterpriseEnergy_list end...");
        List<EnterpriseEnergyHistoryModel> result = yNDataEnterpriseEnergyService.listHistory(requestMap);
        return RestResponse.success(result);
    }
	
	@RequestMapping(value = "/dataEnterpriseEnergy/getVersion",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<VersionInfoModel> getVersion(@RequestBody Map<String,String> request){
        LOGGER.info("YNDataEnterpriseEnergyController getVersion begin......");
        String enterpriseCode = request.get("enterpriseCode");
        VersionInfoModel versionInfoModel = ynCompanyContacterService.getVersion(enterpriseCode);
        LOGGER.info("YNDataEnterpriseEnergyController getVersion end......");
        return RestResponse.success(versionInfoModel);
    }



}
