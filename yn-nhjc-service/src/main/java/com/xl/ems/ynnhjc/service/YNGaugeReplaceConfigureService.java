package com.xl.ems.ynnhjc.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.xl.ems.ynnhjc.mapper.DataCollectConfigureModelMapper;
import com.xl.ems.ynnhjc.mapper.GaugeReplaceConfigureMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.DataCollectConfigureModel;
import com.xl.ems.ynnhjc.model.GaugeReplaceConfigure;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;


@Service
public class YNGaugeReplaceConfigureService {
	
	@Autowired
    GenericRest genericRest;
	
	@Autowired
    GetUrlUtils getUrlUtils;
	
	@Autowired
	GaugeReplaceConfigureMapper gaugeReplaceConfigureMapper;
	
	@Autowired
	AKModelMapper akModelMapper;
	
	@Autowired
	YNEnterpriseServcie yNEnterpriseServcie;
	
	@Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(YNGaugeReplaceConfigureService.class);
	
	/**
	 * 用能单位计量器具更换记录 add
	 *  @return JSONObject
	 * @throws ParseException
	 */
	public RestResponse<JSONObject> gaugeReplaceConfigure_add(GaugeReplaceConfigure GRCModel) {
		LOGGER.info("YNGaugeReplaceConfigureService add begin......");
        if (GRCModel == null) {
            LOGGER.info("YNGaugeReplaceConfigureService add gaugeReplaceConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GRCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeReplaceConfigureService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeReplaceConfigureService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNGaugeReplaceConfigureService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNGaugeReplaceConfigureService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeReplaceConfigure", GRCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeReplaceConfigure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                GRCModel.setDataindex(dataIndex);
                try {
                	gaugeReplaceConfigureMapper.insert(GRCModel);
                } catch (Exception e) {
                    LOGGER.info("YNGaugeReplaceConfigureService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else {
                LOGGER.info("YNGaugeReplaceConfigureService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeReplaceConfigureService add end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具更换记录 delete
	 *  @return JSONObject
	 */
	public RestResponse<JSONObject> gaugeReplaceConfigure_delete(GaugeReplaceConfigure GRCModel) {
		LOGGER.info("YNGaugeReplaceConfigureService delete begin......");
        if (GRCModel == null) {
            LOGGER.info("YNGaugeReplaceConfigureService delete gaugeReplaceConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GRCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeReplaceConfigureService delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeReplaceConfigureService delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeReplaceConfigureService delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeReplaceConfigure", GRCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeReplaceConfigure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeReplaceConfigureMapper.deleteByPrimaryKey(GRCModel.getDataindex());
                } catch (Exception e) {
                    LOGGER.error("YNGaugeReplaceConfigureService delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeReplaceConfigureService delete  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeReplaceConfigureService delete end......");
        return RestResponse.success();
	}
	
	/**
	 * 用能单位计量器具更换记录 update
	 *  @return JSONObject
	 * @throws ParseException 
	 */
	public RestResponse<JSONObject> gaugeReplaceConfigure_update(GaugeReplaceConfigure GRCModel) {
		LOGGER.info("YNGaugeReplaceConfigureService update begin......");
        if (GRCModel == null) {
            LOGGER.info("YNGaugeReplaceConfigureService update gaugeReplaceConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = GRCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNGaugeReplaceConfigureService update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNGaugeReplaceConfigureService update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNGaugeReplaceConfigureService update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("GaugeReplaceConfigure", GRCModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/gaugeReplaceConfigure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                	gaugeReplaceConfigureMapper.updateByPrimaryKey(GRCModel);
                } catch (Exception e) {
                    LOGGER.error("YNGaugeReplaceConfigureService update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            }else {
                LOGGER.error("YNGaugeReplaceConfigureService update  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNGaugeReplaceConfigureService update end......");
        return RestResponse.success();
	}
	
	public List<GaugeReplaceConfigure> list(String enterpriseCode){
		LOGGER.info("YNGaugeReplaceConfigureService list begin......");
		List<GaugeReplaceConfigure> list = gaugeReplaceConfigureMapper.selectByEnterpriseCode(enterpriseCode);
		LOGGER.info("YNGaugeReplaceConfigureService list end......");
		return list;
	}
}
