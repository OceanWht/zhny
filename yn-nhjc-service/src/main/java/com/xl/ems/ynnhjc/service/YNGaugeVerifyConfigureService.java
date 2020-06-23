package com.xl.ems.ynnhjc.service;

import java.text.ParseException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
import com.xl.ems.ynnhjc.mapper.GaugeVerifyConfigureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.GaugeVerifyConfigureModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
@Service
public class YNGaugeVerifyConfigureService {
	
	@Autowired
	GaugeVerifyConfigureModelMapper gaugeVerifyConfigureModelMapper;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeVerifyConfigureService.class);
	
	/**
	 * 用能单位计量器具检定/校准记录 add
	 * @return JSONObject
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> gaugeVerifyConfigure_add(GaugeVerifyConfigureModel GVCModel) {
		LOGGER.info("YNGaugeVerifyConfigureService add begin......");
        if (GVCModel == null) {
            LOGGER.info("YNGaugeVerifyConfigureService add gaugeVerifyConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GVCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeVerifyConfigureService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeVerifyConfigureService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNGaugeVerifyConfigureService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeVerifyConfigureService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeVerifyConfigureModel", GVCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeVerifyConfigure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                GVCModel.setDataindex(dataIndex);
                try {
                	gaugeVerifyConfigureModelMapper.insert(GVCModel);
                } catch (Exception e) {
                    LOGGER.info("YNGaugeVerifyConfigureService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNGaugeVerifyConfigureService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeVerifyConfigureService add end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具检定/校准记录 delete
	 * @return JSONObject
	 */
	public RestResponse<JSONObject> gaugeVerifyConfigure_delete(GaugeVerifyConfigureModel GVCModel) {
		LOGGER.info("YNGaugeVerifyConfigureService delete begin......");
        if (GVCModel == null) {
            LOGGER.info("YNGaugeVerifyConfigureService delete GaugeVerifyConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GVCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeVerifyConfigureService delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeVerifyConfigureService delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeVerifyConfigureService delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeVerifyConfigureModel", GVCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeVerifyConfigure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeVerifyConfigureModelMapper.deleteByPrimaryKey(GVCModel.getDataindex());
                } catch (Exception e) {
                    LOGGER.error("YNGaugeVerifyConfigureService delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeVerifyConfigureService delete  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeVerifyConfigureService delete end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具检定/校准记录 update
	 * return JSONObject
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> gaugeVerifyConfigure_update(GaugeVerifyConfigureModel GVCModel) {
		LOGGER.info("YNGaugeVerifyConfigureService update begin......");
        if (GVCModel == null) {
            LOGGER.info("YNGaugeVerifyConfigureService update gaugeVerifyConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GVCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeVerifyConfigureService update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeVerifyConfigureService update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeVerifyConfigureService update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeVerifyConfigureModel", GVCModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeVerifyConfigure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeVerifyConfigureModelMapper.updateByPrimaryKey(GVCModel);
                } catch (Exception e) {
                    LOGGER.error("YNGaugeVerifyConfigureService update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeVerifyConfigureService update  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeVerifyConfigureService update end......");
        return RestResponse.success();
	}
	
	public List<GaugeVerifyConfigureModel> list(String enterpriseCode){
		LOGGER.info("YNGaugeVerifyConfigureService list begin......");
		List<GaugeVerifyConfigureModel> list = gaugeVerifyConfigureModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNGaugeVerifyConfigureService list end......");
		return list;
	}
}
