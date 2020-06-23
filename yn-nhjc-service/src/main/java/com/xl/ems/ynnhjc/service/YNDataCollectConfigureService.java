package com.xl.ems.ynnhjc.service;

import java.util.*;

import com.xl.ems.ynnhjc.bean.CalcGroupBean;
import com.xl.ems.ynnhjc.bean.UnitInfoBean;
import com.xl.ems.ynnhjc.mapper.*;
import com.xl.ems.ynnhjc.model.*;
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
import com.xl.ems.ynnhjc.utils.GetUrlUtils;
import org.springframework.util.CollectionUtils;

@Service
public class YNDataCollectConfigureService {

    @Autowired
    DataCollectConfigureModelMapper dataCollectConfigureModelMapper;

    @Autowired
    ProcessConfigureModelMapper processConfigureModelMapper;

    @Autowired
    EnterpriseEnergyModelMapper enterpriseEnergyModelMapper;

    @Autowired
    UnitConfigureModelMapper unitConfigureModelMapper;

    @Autowired
    PlatformBasicdataModelMapper platformBasicdataModelMapper;

    @Autowired
    DeviceConfigureModelMapper deviceConfigureModelMapper;

    @Autowired
    MaterielProductModelMapper materielProductModelMapper;

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

    @Value("${user.8008_surface}")
    private String Url8008Type;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNDataCollectConfigureService.class);

    /**
     * 用能单位数据采集配置项信息 add
     */
    public RestResponse<JSONObject> dataCollectConfigure_add(DataCollectConfigureModel DCCModel) {
        LOGGER.info("YNDataCollectConfigureService add begin......");
        if (DCCModel == null) {
            LOGGER.info("YNDataCollectConfigureService add dataCollectConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDataCollectConfigureService add enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDataCollectConfigureService add apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        if (akModel == null) {
            LOGGER.error("YNDataCollectConfigureService add akModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNDataCollectConfigureService add token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("DataCollectConfigureModel", DCCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/dataCollectConfigure/add", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                String dataIndex = res.getJSONObject("data").getString("dataIndex");
                DCCModel.setDataindex(dataIndex);
                try {
                    //成功后同时插入表yn_nhjc_enterprise_energy
                    EnterpriseEnergyModel enterpriseEnergyModel = new EnterpriseEnergyModel();
                    //名称
                    enterpriseEnergyModel.setBackupField1(DCCModel.getCollectitemname());

                    //设置折标系数
                    //综合能耗，设为1
                    if (DCCModel.getEnergytypecode().equals("0000")){
                        enterpriseEnergyModel.setConvertration("1");
                    }else {
                        String convertration = platformBasicdataModelMapper.getCollectRatio(DCCModel.getEnergytypecode());
                        enterpriseEnergyModel.setConvertration(convertration);
                    }
                    //编码
                    enterpriseEnergyModel.setDatacode(DCCModel.getDatacode());
                    enterpriseEnergyModel.setDicversion(DCCModel.getDicversion());
                    enterpriseEnergyModel.setRegversion(DCCModel.getRegversion());
                    enterpriseEnergyModel.setEnterprisecode(DCCModel.getEnterprisecode());
                    enterpriseEnergyModel.setScope(DCCModel.getScope());
                    enterpriseEnergyModel.setInputtype(DCCModel.getInputtype());
                    enterpriseEnergyModel.setStattype(DCCModel.getStattype());

                    //默认待传
                    enterpriseEnergyModel.setBackupField3("2");
                    //本条数据得dataindex 作为下面更新得查询条件，因为datacode可以变
                    enterpriseEnergyModel.setBackupField2(DCCModel.getDataindex());
                    enterpriseEnergyModelMapper.insertSelective(enterpriseEnergyModel);
                    dataCollectConfigureModelMapper.insert(DCCModel);
                } catch (Exception e) {
                    LOGGER.info("YNDataCollectConfigureService add save has error");
                    return RestResponse.error(RestCode.SAVE_FAIL);
                }


            } else {
                LOGGER.info("YNDataCollectConfigureService add res has error:{" + res.getString("responseCode") + ":"
                        + res.getString("responseMessage") + "}......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDataCollectConfigureService add end......");
        return RestResponse.success();
    }

    /**
     * 用能单位数据采集配置项信息 delete
     */
    public RestResponse<JSONObject> dataCollectConfigure_delete(DataCollectConfigureModel DCCModel) {
        LOGGER.info("YNDataCollectConfigureService delete begin......");
        if (DCCModel == null) {
            LOGGER.info("YNDataCollectConfigureService delete dataCollectConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDataCollectConfigureService delete enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNDataCollectConfigureService delete token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDataCollectConfigureService delete apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        Map<String, Object> body = new Hashtable<>();
        body.put("DataCollectConfigureModel", DCCModel);
        body.put("softurl", softUrl);
        body.put("token", token);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/dataCollectConfigure/delete", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    dataCollectConfigureModelMapper.deleteByPrimaryKey(DCCModel.getDataindex());
                    EnterpriseEnergyModel enterpriseEnergyModel = enterpriseEnergyModelMapper.selectByBackupField2(DCCModel.getDataindex());
                    if (enterpriseEnergyModel != null) {
                        enterpriseEnergyModelMapper.deleteByBackupField2(DCCModel.getDataindex());
                    }

                } catch (Exception e) {
                    LOGGER.error("YNDataCollectConfigureService delete deleteByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            } else {
                LOGGER.error("YNDataCollectConfigureService delete  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDataCollectConfigureService delete end......");
        return RestResponse.success();
    }

    /**
     * 用能单位数据采集配置项信息 update
     */
    public RestResponse<JSONObject> dataCollectConfigure_update(DataCollectConfigureModel DCCModel) {
        LOGGER.info("YNDataCollectConfigureService update begin......");
        if (DCCModel == null) {
            LOGGER.info("YNDataCollectConfigureService update dataCollectConfigureModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = DCCModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.info("YNDataCollectConfigureService update enterpriseCode is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.info("YNDataCollectConfigureService update token is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNDataCollectConfigureService update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("DataCollectConfigureModel", DCCModel);
        body.put("softurl", softUrl);
        body.put("token", token);

        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/dataCollectConfigure/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                try {
                    dataCollectConfigureModelMapper.updateByPrimaryKey(DCCModel);
                    //成功后同时更新表yn_nhjc_enterprise_energy
                    EnterpriseEnergyModel enterpriseEnergyModel = enterpriseEnergyModelMapper.selectByBackupField2(DCCModel.getDataindex());
                    if (enterpriseEnergyModel != null) {
                        //名称
                        enterpriseEnergyModel.setBackupField1(DCCModel.getCollectitemname());

                        //设置折标系数
                        //综合能耗，设为1
                        if (DCCModel.getEnergytypecode().equals("0000")){
                            enterpriseEnergyModel.setConvertration("1");
                        }else {
                            String convertration = platformBasicdataModelMapper.getCollectRatio(DCCModel.getEnergytypecode());
                            enterpriseEnergyModel.setConvertration(convertration);
                        }
                        //编码
                        enterpriseEnergyModel.setDatacode(DCCModel.getDatacode());
                        enterpriseEnergyModel.setDicversion(DCCModel.getDicversion());
                        enterpriseEnergyModel.setRegversion(DCCModel.getRegversion());
                        enterpriseEnergyModel.setEnterprisecode(DCCModel.getEnterprisecode());
                        enterpriseEnergyModel.setScope(DCCModel.getScope());
                        enterpriseEnergyModel.setInputtype(DCCModel.getInputtype());
                        enterpriseEnergyModel.setStattype(DCCModel.getStattype());
                        //只要修改了，状态就改为待传
                        enterpriseEnergyModel.setBackupField3("2");
                        //  enterpriseEnergyModel.setStatdate(new Date());
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(enterpriseEnergyModel);
                    }

                } catch (Exception e) {
                    LOGGER.error("YNDataCollectConfigureService update updateByPrimaryKey has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            } else {
                LOGGER.error("YNDataCollectConfigureService update  has error......");
                return RestResponse.error(res);
            }
        }
        LOGGER.info("YNDataCollectConfigureService update end......");
        return RestResponse.success();
    }

    public List<DataCollectConfigureModel> list(String enterpriseCode) {
        LOGGER.info("YNDataCollectConfigureService list begin......");
        List<DataCollectConfigureModel> list = dataCollectConfigureModelMapper.selectByEnterpriseCode(enterpriseCode);
        LOGGER.info("YNDataCollectConfigureService list end......");
        return list;
    }

    public JSONObject getBasicData(String enterpriseCode,String token,String uid) {

        JSONObject res = new JSONObject();
        //生产工序
        List<ProcessConfigureModel> processConfigureModels = processConfigureModelMapper.getList(enterpriseCode);
        //工序单元
        List<UnitConfigureModel> unitConfigureModels = unitConfigureModelMapper.getList(enterpriseCode);
        //重点耗能设备 选中已添加的设备后，equipmentCode重点耗能设备类型 自动生成，不可修改
        List<DeviceConfigureModel> deviceConfigureModels = deviceConfigureModelMapper.selectByEnterpriseCode(enterpriseCode);
        //采集对象类型
        List<PlatformBasicdataModel> energyClassCode = platformBasicdataModelMapper.selectByIntemIndex("7");
        //用途编码
        List<PlatformBasicdataModel> dataUsageCode = platformBasicdataModelMapper.selectByIntemIndex("14");

        //重点用能设备分类编码d
        List<PlatformBasicdataModel> deviceTypeCodes = platformBasicdataModelMapper.selectByIntemIndex("17");
        //能源分类 + 分项
        //能源品种编码计量单位及精度
        List<PlatformBasicdataModel> energyTypeUnitCollectCodes = platformBasicdataModelMapper.selectByIntemIndex("8");
        //能耗工质
        List<PlatformBasicdataModel> energySubstanceCodes = platformBasicdataModelMapper.selectByIntemIndex("9");
        //非能源类产品及计量单位(包含平台预定义编码及企业自定义编码) 用企业自定义的
        //  List<PlatformBasicdataModel> unenergyProductCodes = platformBasicdataModelMapper.selectByIntemIndex("10");
        List<PlatformBasicdataModel> unenergyProductCodes = new ArrayList<>();
        List<MaterielProductModel> unenergyProducts = materielProductModelMapper.getList(enterpriseCode);
        for (MaterielProductModel materielProductModel : unenergyProducts) {
            PlatformBasicdataModel model = new PlatformBasicdataModel();
            model.setCode(materielProductModel.getProduceno());
            model.setName(materielProductModel.getProducename());
            unenergyProductCodes.add(model);
        }

        //能效指标编码及计量单位
        List<PlatformBasicdataModel> energyEfficiencyCodes = platformBasicdataModelMapper.selectByIntemIndex("11");
        //找出能效指标分类
        Set<String> energyEfficiencyType = new HashSet<>();
        if (!CollectionUtils.isEmpty(energyEfficiencyCodes)) {
            energyEfficiencyCodes.forEach(e -> {
                energyEfficiencyType.add(e.getIndustry());
            });
        }
        List<PlatformBasicdataModel> energyEfficiencyCode = new ArrayList<>();
        if (!CollectionUtils.isEmpty(energyEfficiencyType)) {
            int i = 0;
            for (String type : energyEfficiencyType) {
                PlatformBasicdataModel model = new PlatformBasicdataModel();
                model.setCode(String.valueOf(i));
                model.setName(type);
                energyEfficiencyCode.add(model);
                i++;
            }
        }
        //经营指标编码及计量单位
        List<PlatformBasicdataModel> businessTargetCodes = platformBasicdataModelMapper.selectByIntemIndex("12");
        //其它数据编码
        List<PlatformBasicdataModel> otherDataCodes = platformBasicdataModelMapper.selectByIntemIndex("13");

        //数据采集来源 inputType
        List<PlatformBasicdataModel> inputType = platformBasicdataModelMapper.selectByIntemIndex("6");

        //获取企业下得所有单元 模糊查找企业下的单元
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(Url8008Type);
        Map<String, Object> body = new Hashtable<>();
        body.put("url", softUrl);
        body.put("token", token);
        body.put("uid",uid);
        ResponseEntity<RestResponse<List<UnitInfoBean>>> entity = genericRest.post(apiUrl + "/GetVagueUnit", body,
                new ParameterizedTypeReference<RestResponse<List<UnitInfoBean>>>() {});


        StringBuilder uIds = new StringBuilder();
        uIds.append(uid).append(",");
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            List<UnitInfoBean> unitInfoBeans = entity.getBody().getResult();
            if (!CollectionUtils.isEmpty(unitInfoBeans)){
                res.put("unitInfoBeans", unitInfoBeans);
                for (UnitInfoBean unitInfoBean: unitInfoBeans){
                  uIds.append(unitInfoBean.getUid()).append(",");
                }
            }
        }


        //uid 字符串
        String uids = uIds.substring(0,uIds.lastIndexOf(","));
        body.put("uIds",uids);
        //获取单元下所有得计算组数据
        ResponseEntity<RestResponse<List<CalcGroupBean>>> calcGroupEntity = genericRest.post(apiUrl + "/GetUnitAllCalcGroup", body,
                new ParameterizedTypeReference<RestResponse<List<CalcGroupBean>>>() {});

        if (calcGroupEntity.getStatusCode().equals(HttpStatus.OK)){
            List<CalcGroupBean> calcGroupBeans = calcGroupEntity.getBody().getResult();
            if (!CollectionUtils.isEmpty(calcGroupBeans)){
                res.put("calcGroupBeans", calcGroupBeans);
            }
        }


        res.put("processConfigureModels", processConfigureModels);
        res.put("unitConfigureModels", unitConfigureModels);
        res.put("deviceConfigureModels", deviceConfigureModels);
        res.put("energyClassCode", energyClassCode);
        res.put("dataUsageCode", dataUsageCode);
        res.put("energyTypeUnitCollectCodes", energyTypeUnitCollectCodes);
        res.put("energySubstanceCodes", energySubstanceCodes);
        res.put("unenergyProductCodes", unenergyProductCodes);
        res.put("energyEfficiencyCodes", energyEfficiencyCodes);
        res.put("businessTargetCodes", businessTargetCodes);
        res.put("otherDataCodes", otherDataCodes);
        res.put("inputType", inputType);
        res.put("energyEfficiencyType", energyEfficiencyCode);
        res.put("deviceTypeCodes", deviceTypeCodes);
        return res;

    }
}
