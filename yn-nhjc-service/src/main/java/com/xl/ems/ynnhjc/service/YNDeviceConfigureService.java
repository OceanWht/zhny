package com.xl.ems.ynnhjc.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;

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
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.DeviceConfigureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.DeviceConfigureModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;

@Service
public class YNDeviceConfigureService {
	
	@Autowired
	DeviceConfigureModelMapper deviceConfigureModelMapper;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNDeviceConfigureService.class);
	
	/**
	 * 用能单位重点耗能设备信息 add
	 */
	public RestResponse<JSONObject> DeviceConfigure_add(DeviceConfigureModel DCModel) {
		LOGGER.info("YNDeviceConfigureService add begin......");
        if (DCModel == null) {
            LOGGER.info("YNDeviceConfigureService add companyContacterModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDeviceConfigureService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDeviceConfigureService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNDeviceConfigureService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDeviceConfigureService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("DeviceConfigureModel", DCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/deviceConfigure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                DCModel.setDataindex(dataIndex);
                try {
                	deviceConfigureModelMapper.insert(DCModel);
                } catch (Exception e) {
                    LOGGER.info("YNDeviceConfigureService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNDeviceConfigureService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDeviceConfigureService add end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位重点耗能设备信息 delete
	 */
	public RestResponse<JSONObject> DeviceConfigure_delete(DeviceConfigureModel DCModel) {
		LOGGER.info("YNDeviceConfigureService delete begin......");
        if (DCModel == null) {
            LOGGER.info("YNDeviceConfigureService delete deviceConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDeviceConfigureService delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNDeviceConfigureService delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDeviceConfigureService delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("DeviceConfigureModel", DCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/deviceConfigure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	deviceConfigureModelMapper.deleteByPrimaryKey(DCModel.getDataindex());
                } catch (Exception e) {
                    LOGGER.error("YNDeviceConfigureService delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNDeviceConfigureService delete  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDeviceConfigureService delete end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位重点耗能设备信息 update
	 */
	public RestResponse<JSONObject> DeviceConfigure_update(DeviceConfigureModel DCModel) {
		LOGGER.info("YNDeviceConfigureService update begin......");
        if (DCModel == null) {
            LOGGER.info("YNDeviceConfigureService update DCModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDeviceConfigureService update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNDeviceConfigureService update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDeviceConfigureService update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("DeviceConfigureModel", DCModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/deviceConfigure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	deviceConfigureModelMapper.updateByPrimaryKey(DCModel);
                } catch (Exception e) {
                    LOGGER.error("YNDeviceConfigureService update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNDeviceConfigureService update  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDeviceConfigureService update end......");
        return RestResponse.success();
	}
	
	public List<DeviceConfigureModel> list(String enterpriseCode){
		LOGGER.info("YNCompanyContacterServiceImpl list begin......");
		List<DeviceConfigureModel> list = deviceConfigureModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNCompanyContacterServiceImpl list end......");
		return list;
	}
}
