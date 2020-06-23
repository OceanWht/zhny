package com.xl.ems.ynnhjc.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyConsumeModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyIdcModelMapper;
import com.xl.ems.ynnhjc.mapper.ArganEnergyWarmModelMapper;
import com.xl.ems.ynnhjc.model.ArganEnergyConsumeModel;
import com.xl.ems.ynnhjc.model.ArganEnergyIdcModel;
import com.xl.ems.ynnhjc.model.ArganEnergyWarmModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;

@Service
public class YNArganEnergyService {
	
	@Autowired
	ArganEnergyConsumeModelMapper arganEnergyConsumeModelMapper;
	
	@Autowired
	ArganEnergyIdcModelMapper arganEnergyIdcModelMapper;
	
	@Autowired
	ArganEnergyWarmModelMapper arganEnergyWarmModelMapper;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNArganEnergyService.class);
	
	/**
	 * 公共机构能源资源消费信息 add
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_addEnergy(ArganEnergyConsumeModel AECModel) {
		LOGGER.info("YNArganEnergyService add begin......");
        if (AECModel == null) {
            LOGGER.info("YNArganEnergyService add ArganEnergyConsumeModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	AECModel.setPrepardate(new Date());
        	AECModel.setReportdate(new Date());
        	AECModel.setI0("2");
			arganEnergyConsumeModelMapper.insert(AECModel);
		} catch (Exception e) {
			LOGGER.info("YNArganEnergyService add save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService add end......");
        return RestResponse.success();
        /*String enterpriseCode = AECModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNArganEnergyService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNArganEnergyService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNArganEnergyService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("ArganEnergyConsumeModel", AECModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addEnergy", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                AECModel.setDataindex(dataIndex);
                try {
                	arganEnergyConsumeModelMapper.insert(AECModel);
                } catch (Exception e) {
                    LOGGER.info("YNArganEnergyService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNArganEnergyService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }*/
	}
	/**
	 * 公共机构能源资源消费信息 update
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_updateEnergy(ArganEnergyConsumeModel AECModel){
		LOGGER.info("YNArganEnergyService update begin......");
        if (AECModel == null) {
            LOGGER.info("YNArganEnergyService update ArganEnergyConsumeModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
			arganEnergyConsumeModelMapper.updateByPrimaryKey(AECModel);
		} catch (Exception e) {
			LOGGER.info("YNArganEnergyService update save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService update end......");
        return RestResponse.success();
	}
	
	/**
	 * 公共机构能源资源消费信息 delete
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_deleteEnergy(ArganEnergyConsumeModel AECModel){
		LOGGER.info("YNArganEnergyService delete begin......");
        if (AECModel == null) {
            LOGGER.info("YNArganEnergyService delete ArganEnergyConsumeModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
			arganEnergyConsumeModelMapper.deleteByPrimaryKey(AECModel.getId());
		} catch (Exception e) {
			LOGGER.error("YNArganEnergyService delete deleteByPrimaryKey has error......");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
		}
		return RestResponse.success();
	}
	
	/**
	 * 公共机构数据中心机房能源消费信息 add
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_addIDC(ArganEnergyIdcModel AEIModel) {
		LOGGER.info("YNArganEnergyService add begin......");
        if (AEIModel == null) {
            LOGGER.info("YNArganEnergyService add ArganEnergyIdcModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	AEIModel.setPrepardate(new Date());
        	AEIModel.setReportdate(new Date());
        	AEIModel.setI0("2");
			arganEnergyIdcModelMapper.insert(AEIModel);
		} catch (Exception e1) {
			LOGGER.info("YNArganEnergyService add save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService add end......");
        return RestResponse.success();
        /*String enterpriseCode = AEIModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNArganEnergyService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNArganEnergyService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNArganEnergyService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("ArganEnergyIdcModel", AEIModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addIDC", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                AEIModel.setDataindex(dataIndex);
                try {
                	arganEnergyIdcModelMapper.insert(AEIModel);
                } catch (Exception e) {
                    LOGGER.info("YNArganEnergyService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNArganEnergyService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }*/
	}
	
	/**
	 * 公共机构数据中心机房能源消费信息 update
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_updateIDC(ArganEnergyIdcModel AEIModel){
		LOGGER.info("YNArganEnergyService update begin......");
        if (AEIModel == null) {
            LOGGER.info("YNArganEnergyService update ArganEnergyIdcModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	arganEnergyIdcModelMapper.updateByPrimaryKey(AEIModel);
		} catch (Exception e) {
			LOGGER.info("YNArganEnergyService update save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService update end......");
        return RestResponse.success();
	}
	
	/**
	 * 公共机构数据中心机房能源消费信息 delete
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_deleteIDC(ArganEnergyIdcModel AEIModel){
		LOGGER.info("YNArganEnergyService delete begin......");
        if (AEIModel == null) {
            LOGGER.info("YNArganEnergyService delete ArganEnergyConsumeModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	arganEnergyIdcModelMapper.deleteByPrimaryKey(AEIModel.getId());
		} catch (Exception e) {
			LOGGER.error("YNArganEnergyService delete deleteByPrimaryKey has error......");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
		}
		return RestResponse.success();
	}
	
	/**
	 * 公共机构采暖能源资源消费信息 add
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_addWarm(ArganEnergyWarmModel AEWModel) {
		LOGGER.info("YNArganEnergyService add begin......");
        if (AEWModel == null) {
            LOGGER.info("YNArganEnergyService add ArganEnergyWarmModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	AEWModel.setPrepardate(new Date());
        	AEWModel.setReportdate(new Date());
        	AEWModel.setI0("2");
			arganEnergyWarmModelMapper.insert(AEWModel);
		} catch (Exception e) {
			LOGGER.info("YNArganEnergyService add save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService add end......");
        return RestResponse.success();
        /*String enterpriseCode = AEWModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNArganEnergyService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNArganEnergyService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNArganEnergyService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNArganEnergyService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("ArganEnergyWarmModel", AEWModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/arganEnergy/addWarm", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                AEWModel.setDataindex(dataIndex);
                try {
                	arganEnergyWarmModelMapper.insert(AEWModel);
                } catch (Exception e) {
                    LOGGER.info("YNArganEnergyService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNArganEnergyService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }*/
	}
	
	/**
	 * 公共机构采暖能源资源消费信息 update
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_updateWarm(ArganEnergyWarmModel AEWModel){
		LOGGER.info("YNArganEnergyService update begin......");
        if (AEWModel == null) {
            LOGGER.info("YNArganEnergyService update ArganEnergyWarmModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	arganEnergyWarmModelMapper.updateByPrimaryKey(AEWModel);
		} catch (Exception e) {
			LOGGER.info("YNArganEnergyService update save has error");
            return RestResponse.error(RestCode.SAVE_FAIL);
		}
        LOGGER.info("YNArganEnergyService update end......");
        return RestResponse.success();
	}
	
	/**
	 * 公共机构采暖能源资源消费信息 delete
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> arganEnergy_deleteWarm(ArganEnergyWarmModel AEWModel){
		LOGGER.info("YNArganEnergyService delete begin......");
        if (AEWModel == null) {
            LOGGER.info("YNArganEnergyService delete ArganEnergyWarmModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        try {
        	arganEnergyWarmModelMapper.deleteByPrimaryKey(AEWModel.getId());
		} catch (Exception e) {
			LOGGER.error("YNArganEnergyService delete deleteByPrimaryKey has error......");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
		}
		return RestResponse.success();
	}
	
	public List<ArganEnergyConsumeModel> addEnergy_list(String enterpriseCode){
		LOGGER.info("YNDataCollectConfigureService list begin......");
		List<ArganEnergyConsumeModel> list = arganEnergyConsumeModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNDataCollectConfigureService list end......");
		return list;
	}
	
	public List<ArganEnergyIdcModel> addIDC_list(String enterpriseCode){
		LOGGER.info("YNDataCollectConfigureService list begin......");
		List<ArganEnergyIdcModel> list = arganEnergyIdcModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNDataCollectConfigureService list end......");
		return list;
	}
	
	public List<ArganEnergyWarmModel> addWarm_list(String enterpriseCode){
		LOGGER.info("YNDataCollectConfigureService list begin......");
		List<ArganEnergyWarmModel> list = arganEnergyWarmModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNDataCollectConfigureService list end......");
		return list;
	}
}
