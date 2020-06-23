package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.AKModelMapper;
import com.xl.ems.ynnhjc.mapper.CompanyContacterModelMapper;
import com.xl.ems.ynnhjc.mapper.VersionInfoModelMapper;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.CompanyContacterModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;
import com.xl.ems.ynnhjc.service.YNCompanyContacterService;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
public class YNCompanyContacterServiceImpl implements YNCompanyContacterService {

    @Autowired
    CompanyContacterModelMapper companyContacterModelMapper;

    @Autowired
    VersionInfoModelMapper versionInfoModelMapper;

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyContacterServiceImpl.class);


    @Override
    public RestResponse<JSONObject> add(CompanyContacterModel companyContacterModel) {
        LOGGER.info("YNCompanyContacterServiceImpl add begin......");
        if (companyContacterModel == null) {
            LOGGER.info("YNCompanyContacterServiceImpl add companyContacterModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = companyContacterModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyContacterServiceImpl add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        List<CompanyContacterModel> companyContacterModels = companyContacterModelMapper.selectByEnterpriseCode(enterpriseCode);
        if (!CollectionUtils.isEmpty(companyContacterModels)) {
            for (CompanyContacterModel contacterModel : companyContacterModels) {
                if (contacterModel.getContactername().equals(companyContacterModel.getContactername()) &&
                        contacterModel.getDept().equals(companyContacterModel.getDept())
                        && contacterModel.getPhone().equals(companyContacterModel.getPhone())) {
                    LOGGER.info("YNCompanyContacterServiceImpl add companyContacterModel is already existed......");
                    return RestResponse.error(RestCode.USER_ALREADY_EXIST);
                }
            }
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyContacterServiceImpl add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNCompanyContacterServiceImpl add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNCompanyContacterServiceImpl add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("CompanyContacterModel", companyContacterModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyContacter/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                companyContacterModel.setDataindex(dataIndex);
                try {
                    companyContacterModelMapper.insertSelective(companyContacterModel);
                } catch (Exception e) {
                    LOGGER.info("YNCompanyContacterServiceImpl add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }
            } else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.add(companyContacterModel);
            }else {
                LOGGER.info("YNCompanyContacterServiceImpl add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyContacterServiceImpl add end......");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> update(CompanyContacterModel companyContacterModel) {
        LOGGER.info("YNCompanyContacterServiceImpl update begin......");
        if (companyContacterModel == null) {
            LOGGER.info("YNCompanyContacterServiceImpl update companyContacterModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = companyContacterModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyContacterServiceImpl update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyContacterServiceImpl update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyContacterServiceImpl update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("CompanyContacterModel", companyContacterModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyContacter/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    companyContacterModelMapper.updateByPrimaryKey(companyContacterModel);
                } catch (Exception e) {
                    LOGGER.error("YNCompanyContacterServiceImpl update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.update(companyContacterModel);
            }else {
                LOGGER.error("YNCompanyContacterServiceImpl update  has error......");
                return RestResponse.error(res);
            }
        }


        LOGGER.info("YNCompanyContacterServiceImpl update end......");
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> delete(CompanyContacterModel companyContacterModel) {
        LOGGER.info("YNCompanyContacterServiceImpl delete begin......");
        if (companyContacterModel == null) {
            LOGGER.info("YNCompanyContacterServiceImpl delete companyContacterModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = companyContacterModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNCompanyContacterServiceImpl delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNCompanyContacterServiceImpl delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNCompanyContacterServiceImpl delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("CompanyContacterModel", companyContacterModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/companyContacter/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    companyContacterModelMapper.deleteByPrimaryKey(companyContacterModel.getId());
                } catch (Exception e) {
                    LOGGER.error("YNCompanyContacterServiceImpl delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }

            }else if (res.getInteger("responseCode") == 401){
                ynEnterpriseServcie.requestAK();
                this.delete(companyContacterModel);
            }else {
                LOGGER.error("YNCompanyContacterServiceImpl delete  has error......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNCompanyContacterServiceImpl delete end......");
        return RestResponse.success();
    }

    @Override
    public List<CompanyContacterModel> list(String enterpriseCode) {
        LOGGER.info("YNCompanyContacterServiceImpl list begin......");
        List<CompanyContacterModel> list = companyContacterModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNCompanyContacterServiceImpl list end......");
        return list;
    }

    @Override
    public VersionInfoModel getVersion(String enterpriseCode) {
        LOGGER.info("YNCompanyContacterServiceImpl getVersion begin......");
        VersionInfoModel versionInfoModel = null;
        try {
            versionInfoModel = versionInfoModelMapper.selectByDLR(enterpriseCode);
        }catch (Exception e){
            LOGGER.error("YNCompanyContacterServiceImpl getVersion has error......");
            return  null;
        }

        LOGGER.info("YNCompanyContacterServiceImpl getVersion end......");
        return versionInfoModel;
    }

    @Override
    public List<Map<String, Object>> getDataCode(String enterpriseCode) {
        LOGGER.info("YNCompanyContacterServiceImpl getDataCode begin......");
        List<Map<String, Object>> dataCode = null;
        try {
            dataCode = versionInfoModelMapper.getDataCode(enterpriseCode);
        }catch (Exception e){
            LOGGER.error("YNCompanyContacterServiceImpl getDataCode has error......");
            return  null;
        }
        LOGGER.info("YNCompanyContacterServiceImpl getDataCode end......");
        return dataCode;
    }
}
