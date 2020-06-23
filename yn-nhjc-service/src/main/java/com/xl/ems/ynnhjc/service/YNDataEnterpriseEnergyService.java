package com.xl.ems.ynnhjc.service;

import java.text.ParseException;
import java.util.*;

import com.xl.ems.ynnhjc.mapper.EnterpriseEnergyHistoryModelMapper;
import com.xl.ems.ynnhjc.model.EnterpriseEnergyHistoryModel;
import com.xl.ems.ynnhjc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.EnterpriseEnergyModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.EnterpriseEnergyModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;

@Service
public class YNDataEnterpriseEnergyService {
	
	@Autowired
	EnterpriseEnergyModelMapper enterpriseEnergyModelMapper;

	@Autowired
    EnterpriseEnergyHistoryModelMapper enterpriseEnergyHistoryModelMapper;
	
	@Autowired
	AKModelMapper akModelMapper;
	
	@Autowired
	YNEnterpriseServcie yNEnterpriseServcie;
	
	@Autowired
    GenericRest genericRest;
	
	@Autowired
    GetUrlUtils getUrlUtils;
	
	@Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDataEnterpriseEnergyService.class);
	
	/**
	 * 用能单位能源资源计量采集数据
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> dataEnterpriseEnergy(EnterpriseEnergyModel EEModel) {
		LOGGER.info("YNDataEnterpriseEnergyService add begin......");
        if (EEModel == null) {
            LOGGER.info("YNDataEnterpriseEnergyService add dataEnterpriseEnergyModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
            //设置统计时间
            EEModel.setStatdate(DateUtils.formatDateTime2(new Date()));
            enterpriseEnergyModelMapper.updateByPrimaryKeySelective(EEModel);
        } catch (Exception e) {
            LOGGER.info("YNDataCollectConfigureService add save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
        }
        LOGGER.info("YNDataEnterpriseEnergyService add end......");
        return RestResponse.success();
	}
	
	public List<EnterpriseEnergyModel> list(Map<String, String> requestMap){
		LOGGER.info("YNDataEnterpriseEnergyService list begin......");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String statType = requestMap.get("statType");
        //手工填报
        String inputType = requestMap.get("inputType");
		List<EnterpriseEnergyModel> list = enterpriseEnergyModelMapper.selectByEnterpriseCode(enterpriseCode);
		List<EnterpriseEnergyModel> result = new ArrayList<>();
		for (EnterpriseEnergyModel energyModel:list){
            //默认获取所有的数据
		    if (energyModel.getStattype().equals(statType) && energyModel.getInputtype().equals(inputType) ){
                    result.add(energyModel);
            }
        }
		LOGGER.info("YNDataEnterpriseEnergyService list end......");
		return result;
	}

    public List<EnterpriseEnergyHistoryModel> listHistory(Map<String, String> requestMap) {
        LOGGER.info("YNDataEnterpriseEnergyService listHistory begin......");
        String enterpriseCode = requestMap.get("enterpriseCode");
        String statType = requestMap.get("statType");
        //上传时间
        String date = requestMap.get("date");
        List<EnterpriseEnergyHistoryModel> list = enterpriseEnergyHistoryModelMapper.selectByEnterpriseCode(enterpriseCode);
        List<EnterpriseEnergyHistoryModel> result = new ArrayList<>();
        for (EnterpriseEnergyHistoryModel energyModel:list){
            String dateUpload = energyModel.getUploaddate();
            dateUpload = dateUpload.substring(0,dateUpload.lastIndexOf(" "));
            //默认获取所有的数据
            if (energyModel.getStattype().equals(statType) && dateUpload.equals(date)){
                result.add(energyModel);
            }
        }
        LOGGER.info("YNDataEnterpriseEnergyService listHistory end......");
        return result;
    }
}
