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
import com.xl.ems.ynnhjc.mapper.GaugeConfigureModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.GaugeConfigureModel;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;

@Service
public class YNGaugeConfigureService {
	
	@Autowired
	GaugeConfigureModelMapper gaugeConfigureModelMapper;
	
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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeConfigureService.class);
	
	/**
	 * 用能单位计量器具信息  add
	 *  return JSONObject
     * @throws ParseException 
	 */
	public RestResponse<JSONObject> gaugeConfigure_add(GaugeConfigureModel GCModel) {
		LOGGER.info("YNGaugeConfigureService add begin......");
        if (GCModel == null) {
            LOGGER.info("YNGaugeConfigureService add gaugeConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeConfigureService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeConfigureService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNGaugeConfigureService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeConfigureService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeConfigureModel", GCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeConfigure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                GCModel.setDataindex(dataIndex);
                try {
                	gaugeConfigureModelMapper.insert(GCModel);
                } catch (Exception e) {
                    LOGGER.info("YNGaugeConfigureService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNGaugeConfigureService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeConfigureService add end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具信息 delete
	 *  return JSONObject
	 */
	public RestResponse<JSONObject> gaugeConfigure_delete(GaugeConfigureModel GCModel) {
		LOGGER.info("YNGaugeConfigureService delete begin......");
        if (GCModel == null) {
            LOGGER.info("YNGaugeConfigureService delete gaugeConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeConfigureService delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeConfigureService delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeConfigureService delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeConfigureModel", GCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeConfigure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeConfigureModelMapper.deleteByPrimaryKey(GCModel.getDataindex());
                } catch (Exception e) {
                    LOGGER.error("YNGaugeConfigureService delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeConfigureService delete  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeConfigureService delete end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具信息 update
	 *  return JSONObject
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> gaugeConfigure_update(GaugeConfigureModel GCModel) {
		LOGGER.info("YNGaugeConfigureService update begin......");
        if (GCModel == null) {
            LOGGER.info("YNGaugeConfigureService update gaugeConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeConfigureService update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeConfigureService update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeConfigureService update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeConfigureModel", GCModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeConfigure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeConfigureModelMapper.updateByPrimaryKey(GCModel);
                } catch (Exception e) {
                    LOGGER.error("YNGaugeConfigureService update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeConfigureService update  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeConfigureService update end......");
        return RestResponse.success();
	}
	
	public List<GaugeConfigureModel> list(String enterpriseCode){
		LOGGER.info("YNDataCollectConfigureService list begin......");
		List<GaugeConfigureModel> list = gaugeConfigureModelMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNDataCollectConfigureService list end......");
		return list;
	}
}
