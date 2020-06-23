package com.xl.ems.ynnhjc.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.bean.UnitGroupCurveDataBean;
import com.xl.ems.ynnhjc.bean.UnitGroupDayDataBean;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.*;
import com.xl.ems.ynnhjc.model.AKModel;
import com.xl.ems.ynnhjc.model.DataCollectConfigureModel;
import com.xl.ems.ynnhjc.model.EnterpriseEnergyModel;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import com.xl.ems.ynnhjc.service.task.YNEnterpriseEnergyTaskService;
import com.xl.ems.ynnhjc.utils.DateUtils;
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

import java.util.*;

/**
 * @ClassName YNEnterpriseEnergyTaskImpl
 * @Description TODO
 * @Author wht
 * @Date 10:15
 **/
@Service
public class YNEnterpriseEnergyTaskImpl implements YNEnterpriseEnergyTaskService {

    @Autowired
    EnterpriseEnergyModelMapper enterpriseEnergyModelMapper;

    @Autowired
    EnterpriseEnergyHistoryModelMapper enterpriseEnergyHistoryModelMapper;

    @Autowired
    DataCollectConfigureModelMapper dataCollectConfigureModelMapper;

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    @Value("${user.8008_surface}")
    private String Url8008Type;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseEnergyTaskImpl.class);


    /**
     * 手工填报数据上报
     */
    @Override
    public void enterPrinseEnergyTaskManual() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask models is null......");
            return;
        }
        for (EnterpriseEnergyModel EEModel : models) {
            if (Strings.isNullOrEmpty(EEModel.getDatavalue()) || Strings.isNullOrEmpty(EEModel.getConvertration())) {
                continue;
            }

            String enterpriseCode = EEModel.getEnterprisecode();
            if (Strings.isNullOrEmpty(enterpriseCode)) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask enterpriseCode is null......");
                return;
            }

            String apiUrl = getUrlUtils.getUrl(apiUrlType);
            String softUrl = getUrlUtils.getUrl(softUrlType);
            if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask apiUrl or softUrl is null......");
                return;
            }

            AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
            if (akModel == null) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask akModel is null......");
                return;
            }
            String token = akModel.getToken();
            if (Strings.isNullOrEmpty(token)) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask token is null......");
                return;
            }

            EEModel.setStatdate(DateUtils.formatDateTime1(new Date()));
            EEModel.setUploaddate(DateUtils.formatDateTime1(new Date()));


            Map<String, Object> body = new Hashtable<>();
            body.put("EnterpriseEnergyModel", EEModel);
            body.put("softurl", softUrl);
            body.put("token", token);
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/dataEnterpriseEnergy", body,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    String platformTime = res.getJSONObject("data").getString("platformTime");
                    //上传时间
                    if (EEModel.getStattype().equals("2")) {
                        //月数据
                        EEModel.setUploaddate(DateUtils.formatDateTime3(new Date()));
                    } else if (EEModel.getStattype().equals("3")) {
                        //年数据
                        EEModel.setUploaddate(DateUtils.formatDateTime4(new Date()));
                    }
                    try {
                        //上传成功后，插入历史表
                        EEModel.setBackupField3("0");
                        Integer id = EEModel.getId();
                        EEModel.setId(null);
                        enterpriseEnergyHistoryModelMapper.insertSelective(EEModel);
                        //再清空原有数据
                        EEModel.setId(id);
                        EEModel.setDatavalue("");
                        EEModel.setConvertration("");
                        //状态更改为待传
                        EEModel.setBackupField3("2");
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(EEModel);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask save has error");
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //AK失效
                    ynEnterpriseServcie.requestAK();
                    this.enterPrinseEnergyTaskManual();
                } else {
                    EEModel.setBackupField3("1");
                    try {
                        //失败也插入历史表
                        enterpriseEnergyHistoryModelMapper.insertSelective(EEModel);
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(EEModel);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask updateByPrimaryKeySelective has error");
                        continue;
                    }
                    LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask res has error:{" + res.getString("responseCode") + ":"
                            + res.getString("responseMessage") + "}......");
                }
            }
        }

        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask end......");
    }

    /**
     * 实时数据 15分钟上传一次
     */
    @Override
    public void enterPrinseEnergyTaskAutomatic_15(String date) {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 begin......");

        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask models is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 softUrl is null......");
            return;
        }
        for (EnterpriseEnergyModel model : models) {
            //如果是手工填报得，略过
            if (model.getInputtype().equals("5")) {
                continue;
            }
            //如果上传频率不是实时得，略过
            if (!model.getStattype().equals("0")) {
                continue;
            }
            //获取计算组UID 和dataid  用能单元必须配有计算组 否则获取不到曲线数据
            String dataIndex = model.getBackupField2();
            DataCollectConfigureModel dataCollectConfigureModel =
                    dataCollectConfigureModelMapper.selectByDataIndex(dataIndex);
            if (dataCollectConfigureModel == null) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 dataCollectConfigureModel is null......{" + model.getBackupField1() + ":dataCollectConfigureModel is empty}");
                continue;
            }
            Map<String, Object> body = new Hashtable<>();
            String uid = dataCollectConfigureModel.getBackupField1();
            String dataid = dataCollectConfigureModel.getBackupField2();
            String token1 = userInfoModelMapper.getToken(model.getEnterprisecode());
            body.put("token", token1);
            body.put("uid", uid);
            body.put("dataid", dataid);
            body.put("sdt", DateUtils.formatDateTime2(new Date()));
            body.put("edt", DateUtils.formatDateTime2(new Date()));
            body.put("io", "1");
            body.put("url", url8008);
            //先获取计算组曲线数据
            ResponseEntity<RestResponse<List<UnitGroupCurveDataBean>>> entity = genericRest.post(apiUrl + "/GetUnitGroupCurveData", body,
                    new ParameterizedTypeReference<RestResponse<List<UnitGroupCurveDataBean>>>() {
                    });

            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                List<UnitGroupCurveDataBean> beanList = entity.getBody().getResult();
                if (CollectionUtils.isEmpty(beanList)) {
                    LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 beanList is null......{" + model.getBackupField1() + ":GroupCurveData is empty}");
                    continue;
                }
                //遍历。获取时间,比较时间跟当前时间一致时，上传数据
                for (UnitGroupCurveDataBean bean : beanList) {
                    String dt = bean.getDt();
                    dt = dt.substring(0, dt.lastIndexOf(":"));
                    //精确到分钟即可
                    if (dt.equals(date)) {
                        if (bean.getDs() == null) {
                            model.setDatavalue("0.00");
                        } else {
                            model.setDatavalue(bean.getDs());
                        }

                    }
                }
            } else {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 GetUnitGroupCurveData has error......{" + model.getBackupField1() + ":get GroupCurveData hae error}");
            }

            if (Strings.isNullOrEmpty(model.getDatavalue())) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 dataValue has error......{" + model.getBackupField1() + ":get dataValue is empty}");

                continue;
            }

            //默认都是有效得
            model.setValid("1");
            String token = getToken(model);
            model.setUploaddate(DateUtils.formatDateTime1(new Date()));
            model.setStatdate(DateUtils.formatDateTime1(new Date()));
            Map<String, Object> request = new Hashtable<>();
            request.put("EnterpriseEnergyModel", model);
            request.put("softurl", softUrl);
            request.put("token", token);


            ResponseEntity<RestResponse<JSONObject>> dataEnterpriseEnergyEntity = genericRest.post(apiUrl + "/dataEnterpriseEnergy", request,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (dataEnterpriseEnergyEntity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = dataEnterpriseEnergyEntity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    String platformTime = res.getJSONObject("data").getString("platformTime");
                    //上传时间
                    try {
                        //上传成功后，插入历史表
                        model.setBackupField3("0");
                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                        //再清空原有数据
                        model.setDatavalue("");
                        model.setConvertration("");
                        //状态更改为待传
                        model.setBackupField3("2");
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 save has error");
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //AK失效
                    ynEnterpriseServcie.requestAK();
                    this.enterPrinseEnergyTaskAutomatic_15(date);
                } else {
                    model.setBackupField3("1");
                    try {
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                        //失败也插入历史表
                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 updateByPrimaryKeySelective has error");
                        continue;
                    }
                    LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTask res has error:{" + res.getString("responseCode") + ":"
                            + res.getString("responseMessage") + "}......");
                }

            }

        }
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 end......");
    }

    /**
     * 自动采集日数据
     */
    @Override
    public void enterPrinseEnergyTaskAutomatic_day() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day models is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day softUrl is null......");
            return;
        }
        upload_day(models, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day end......");
    }

    private void upload_day(List<EnterpriseEnergyModel> models, String apiUrl, String url8008, String softUrl) {
        for (EnterpriseEnergyModel model : models) {
            //如果是手工填报得，略过
            if (model.getInputtype().equals("5")) {
                continue;
            }
            //如果上传频率不是日数据得，略过
            if (!model.getStattype().equals("1")) {
                continue;
            }
            //获取计算组UID 和dataid  用能单元必须配有计算组 否则获取不到日数据
            String dataIndex = model.getBackupField2();
            DataCollectConfigureModel dataCollectConfigureModel =
                    dataCollectConfigureModelMapper.selectByDataIndex(dataIndex);
            if (dataCollectConfigureModel == null) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day dataCollectConfigureModel is null......{" + model.getBackupField1() + ":dataCollectConfigureModel is empty}");
                continue;
            }
            Map<String, Object> body = new Hashtable<>();
            String uid = dataCollectConfigureModel.getBackupField1();

            String dataid = dataCollectConfigureModel.getBackupField2();
            String token1 = userInfoModelMapper.getToken(model.getEnterprisecode());
            body.put("token", token1);
            body.put("uid", uid);
            body.put("dataid", dataid);
            body.put("sdt", DateUtils.formatDateTime2(new Date()));
            body.put("edt", DateUtils.formatDateTime2(new Date()));
            body.put("io", "1");
            body.put("url", url8008);
            //先获取计算组日数据
            ResponseEntity<RestResponse<List<UnitGroupDayDataBean>>> entity = genericRest.post(apiUrl + "/GetUnitGroupDayData", body,
                    new ParameterizedTypeReference<RestResponse<List<UnitGroupDayDataBean>>>() {
                    });

            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                List<UnitGroupDayDataBean> beanList = entity.getBody().getResult();
                if (CollectionUtils.isEmpty(beanList)) {
                    LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day beanList is null......{" + model.getBackupField1() + ":UnitGroupDayDataBean is empty}");
                    continue;
                }
                UnitGroupDayDataBean bean = beanList.get(0);
                if (Strings.isNullOrEmpty(bean.getSum())) {
                    model.setDatavalue("0.00");
                } else {
                    model.setDatavalue(bean.getSum());
                }

            } else {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day GetUnitGroupCurveData has error......{" + model.getBackupField1() + ":get GroupCurveData hae error}");
            }

            if (Strings.isNullOrEmpty(model.getDatavalue())) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day dataValue has error......{" + model.getBackupField1() + ":get dataValue is empty}");

                continue;
            }

            //默认都是有效得
            model.setValid("1");
            String token = getToken(model);
            //设置时间，必须精确到秒，否则上传不成功
            model.setUploaddate(DateUtils.formatDateTime1(new Date()));
            model.setStatdate(DateUtils.formatDateTime1(new Date()));
            Map<String, Object> request = new Hashtable<>();
            if (Strings.isNullOrEmpty(model.getConvertration())) {
                model.setConvertration("0.00");
            }
            request.put("EnterpriseEnergyModel", model);
            request.put("softurl", softUrl);
            request.put("token", token);


            ResponseEntity<RestResponse<JSONObject>> dataEnterpriseEnergyEntity = genericRest.post(apiUrl + "/dataEnterpriseEnergy", request,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (dataEnterpriseEnergyEntity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = dataEnterpriseEnergyEntity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    String platformTime = res.getJSONObject("data").getString("platformTime");
                    //上传时间
                    try {
                        //上传成功后，插入历史表
                        Integer id = model.getId();
                        model.setBackupField3("0");
                        model.setId(null);
                        if (Strings.isNullOrEmpty(model.getConvertration())) {
                            model.setConvertration("0.00");
                        }

                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                        //再清空原有数据
                        model.setDatavalue("");
                        model.setId(id);
                        //状态更改为待传
                        model.setBackupField3("2");
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 save has error");
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //AK失效
                    ynEnterpriseServcie.requestAK();
                    this.enterPrinseEnergyTaskAutomatic_day();
                } else {
                    model.setBackupField3("1");
                    try {
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                        //失败也插入历史表
                        model.setId(null);
                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day updateByPrimaryKeySelective has error");
                        continue;
                    }
                    LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day res has error:{" + res.getString("responseCode") + ":"
                            + res.getString("responseMessage") + "}......");
                }

            }

        }
    }

    /**
     * 自动采集月数据，上报得是计算组月数据，8002后台用能单元下必须配置计算组
     */
    @Override
    public void enterPrinseEnergyTaskAutomatic_month() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month models is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month softUrl is null......");
            return;
        }
        uploadMonth(models, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month end......");
    }

    private void uploadMonth(List<EnterpriseEnergyModel> models, String apiUrl, String url8008, String softUrl) {
        for (EnterpriseEnergyModel model : models) {
            //如果是手工填报得，略过
            if (model.getInputtype().equals("5")) {
                continue;
            }
            //如果上传频率不是月数据得，略过
            if (!model.getStattype().equals("2")) {
                continue;
            }
            //获取计算组UID 和dataid  用能单元必须配有计算组 否则获取不到月数据
            String dataIndex = model.getBackupField2();
            DataCollectConfigureModel dataCollectConfigureModel =
                    dataCollectConfigureModelMapper.selectByDataIndex(dataIndex);
            if (dataCollectConfigureModel == null) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month dataCollectConfigureModel is null......{" + model.getBackupField1() + ":dataCollectConfigureModel is empty}");
                continue;
            }
            Map<String, Object> body = new Hashtable<>();
            String uid = dataCollectConfigureModel.getBackupField1();

            String dataid = dataCollectConfigureModel.getBackupField2();
            String token1 = userInfoModelMapper.getToken(model.getEnterprisecode());
            body.put("token", token1);
            body.put("uid", uid);
            body.put("dataid", dataid);
            body.put("sdt", DateUtils.formatDateTime3(new Date()));
            body.put("edt", DateUtils.formatDateTime3(new Date()));
            body.put("io", "1");
            body.put("url", url8008);
            //先获取计算组月数据
            ResponseEntity<RestResponse<List<UnitGroupDayDataBean>>> entity = genericRest.post(apiUrl + "/GetUnitGroupMonthData", body,
                    new ParameterizedTypeReference<RestResponse<List<UnitGroupDayDataBean>>>() {
                    });

            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                List<UnitGroupDayDataBean> beanList = entity.getBody().getResult();
                if (CollectionUtils.isEmpty(beanList)) {
                    LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month beanList is null......{" + model.getBackupField1() + ":UnitGroupDayDataBean is empty}");
                    continue;
                }
                UnitGroupDayDataBean bean = beanList.get(0);
                if (Strings.isNullOrEmpty(bean.getSum())) {
                    model.setDatavalue("0.00");
                } else {
                    model.setDatavalue(bean.getSum());
                }

            } else {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month GetUnitGroupCurveData has error......{" + model.getBackupField1() + ":get GroupCurveData hae error}");
            }

            if (Strings.isNullOrEmpty(model.getDatavalue())) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month dataValue has error......{" + model.getBackupField1() + ":get dataValue is empty}");

                continue;
            }

            //默认都是有效得
            model.setValid("1");
            String token = getToken(model);
            //设置时间，必须精确到秒，否则上传不成功
            model.setUploaddate(DateUtils.formatDateTime1(new Date()));
            model.setStatdate(DateUtils.formatDateTime1(new Date()));
            Map<String, Object> request = new Hashtable<>();
            if (Strings.isNullOrEmpty(model.getConvertration())) {
                model.setConvertration("0.00");
            }
            request.put("EnterpriseEnergyModel", model);
            request.put("softurl", softUrl);
            request.put("token", token);


            ResponseEntity<RestResponse<JSONObject>> dataEnterpriseEnergyEntity = genericRest.post(apiUrl + "/dataEnterpriseEnergy", request,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (dataEnterpriseEnergyEntity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = dataEnterpriseEnergyEntity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    String platformTime = res.getJSONObject("data").getString("platformTime");
                    //上传时间
                    try {
                        //上传成功后，插入历史表
                        Integer id = model.getId();
                        model.setBackupField3("0");
                        model.setId(null);
                        if (Strings.isNullOrEmpty(model.getConvertration())) {
                            model.setConvertration("0.00");
                        }

                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                        //再清空原有数据
                        model.setDatavalue("");
                        model.setId(id);
                        //状态更改为待传
                        model.setBackupField3("2");
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month save has error");
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //AK失效
                    ynEnterpriseServcie.requestAK();
                    this.enterPrinseEnergyTaskAutomatic_day();
                } else {
                    model.setBackupField3("1");
                    try {
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                        //失败也插入历史表
                        model.setId(null);
                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month updateByPrimaryKeySelective has error");
                        continue;
                    }
                    LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month res has error:{" + res.getString("responseCode") + ":"
                            + res.getString("responseMessage") + "}......");
                }

            }

        }
    }

    @Override
    public void enterPrinseEnergyTaskAutomatic_year() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year models is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year softUrl is null......");
            return;
        }
        upload_year(models, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year end......");
    }

    private void upload_year(List<EnterpriseEnergyModel> models, String apiUrl, String url8008, String softUrl) {
        for (EnterpriseEnergyModel model : models) {
            //如果是手工填报得，略过
            if (model.getInputtype().equals("5")) {
                continue;
            }
            //如果上传频率不是年数据得，略过
            if (!model.getStattype().equals("3")) {
                continue;
            }
            //获取计算组UID 和dataid  用能单元必须配有计算组 否则获取不到月数据
            String dataIndex = model.getBackupField2();
            DataCollectConfigureModel dataCollectConfigureModel =
                    dataCollectConfigureModelMapper.selectByDataIndex(dataIndex);
            if (dataCollectConfigureModel == null) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year dataCollectConfigureModel is null......{" + model.getBackupField1() + ":dataCollectConfigureModel is empty}");
                continue;
            }
            Map<String, Object> body = new Hashtable<>();
            String uid = dataCollectConfigureModel.getBackupField1();

            String dataid = dataCollectConfigureModel.getBackupField2();
            String token1 = userInfoModelMapper.getToken(model.getEnterprisecode());
            body.put("token", token1);
            body.put("uid", uid);
            body.put("dataid", dataid);
            body.put("sdt", DateUtils.formatDateTime4(new Date()) + "-01");
            body.put("edt", DateUtils.formatDateTime4(new Date()) + "-12");
            body.put("io", "1");
            body.put("url", url8008);
            //先获取计算组月数据
            ResponseEntity<RestResponse<List<UnitGroupDayDataBean>>> entity = genericRest.post(apiUrl + "/GetUnitGroupMonthData", body,
                    new ParameterizedTypeReference<RestResponse<List<UnitGroupDayDataBean>>>() {
                    });

            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                List<UnitGroupDayDataBean> beanList = entity.getBody().getResult();
                if (CollectionUtils.isEmpty(beanList)) {
                    LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year beanList is null......{" + model.getBackupField1() + ":UnitGroupDayDataBean is empty}");
                    continue;
                }

                //计算年数据
                Double sum = 0.0;
                for (UnitGroupDayDataBean groupDayDataBean: beanList){
                    if (groupDayDataBean.getSum() == null){
                        continue;
                    }
                    sum = sum + Double.valueOf(groupDayDataBean.getSum());
                }
                model.setDatavalue(String.valueOf(sum));

            } else {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year GetUnitGroupCurveData has error......{" + model.getBackupField1() + ":get GroupCurveData hae error}");
            }

            if (Strings.isNullOrEmpty(model.getDatavalue())) {
                LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year dataValue has error......{" + model.getBackupField1() + ":get dataValue is empty}");
                continue;
            }

            //默认都是有效得
            model.setValid("1");
            String token = getToken(model);
            //设置时间，必须精确到秒，否则上传不成功
            model.setUploaddate(DateUtils.formatDateTime1(new Date()));
            model.setStatdate(DateUtils.formatDateTime1(new Date()));
            Map<String, Object> request = new Hashtable<>();
            if (Strings.isNullOrEmpty(model.getConvertration())) {
                model.setConvertration("0.00");
            }
            request.put("EnterpriseEnergyModel", model);
            request.put("softurl", softUrl);
            request.put("token", token);


            ResponseEntity<RestResponse<JSONObject>> dataEnterpriseEnergyEntity = genericRest.post(apiUrl + "/dataEnterpriseEnergy", request,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (dataEnterpriseEnergyEntity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = dataEnterpriseEnergyEntity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    String platformTime = res.getJSONObject("data").getString("platformTime");
                    //上传时间
                    try {
                        //上传成功后，插入历史表
                        Integer id = model.getId();
                        model.setBackupField3("0");
                        model.setId(null);
                        if (Strings.isNullOrEmpty(model.getConvertration())) {
                            model.setConvertration("0.00");
                        }

                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                        //再清空原有数据
                        model.setDatavalue("");
                        model.setId(id);
                        //状态更改为待传
                        model.setBackupField3("2");
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year save has error");
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //AK失效
                    ynEnterpriseServcie.requestAK();
                    this.enterPrinseEnergyTaskAutomatic_day();
                } else {
                    model.setBackupField3("1");
                    try {
                        enterpriseEnergyModelMapper.updateByPrimaryKeySelective(model);
                        //失败也插入历史表
                        model.setId(null);
                        enterpriseEnergyHistoryModelMapper.insertSelective(model);
                    } catch (Exception e) {
                        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year updateByPrimaryKeySelective has error");
                        continue;
                    }
                    LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year res has error:{" + res.getString("responseCode") + ":"
                            + res.getString("responseMessage") + "}......");
                }

            }

        }
    }

    @Override
    public void enterPrinseEnergyTaskAutomatic_day_backup() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day_backup begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day_backup models is null......");
            return;
        }

        //筛选上传失败得
        List<EnterpriseEnergyModel> energyModels = new ArrayList<>();
        for (EnterpriseEnergyModel energyModel:models){
            //日数据失败得
            if (energyModel.getBackupField3().equals("1")&& energyModel.getStattype().equals("2") && !energyModel.getInputtype().equals("5")){
                energyModels.add(energyModel);
            }
        }

        //如果为空，就返回
        if (CollectionUtils.isEmpty(energyModels)){
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day_backup energyModels is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day_backup softUrl is null......");
            return;
        }
        upload_day(energyModels, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_day_backup end......");
    }

    @Override
    public void enterPrinseEnergyTaskAutomatic_month_backup() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month_backup begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month_backup models is null......");
            return;
        }

        //筛选上传失败得
        List<EnterpriseEnergyModel> energyModels = new ArrayList<>();
        for (EnterpriseEnergyModel energyModel:models){
            //月数据失败得
            if (energyModel.getBackupField3().equals("1")&& energyModel.getStattype().equals("2") && !energyModel.getInputtype().equals("5")){
                energyModels.add(energyModel);
            }
        }

        if (CollectionUtils.isEmpty(energyModels)){
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month_backup energyModels is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month_backup softUrl is null......");
            return;
        }
        uploadMonth(energyModels, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_month_backup end......");
    }

    @Override
    public void enterPrinseEnergyTaskAutomatic_year_backup() {
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year_backup begin......");
        List<EnterpriseEnergyModel> models = enterpriseEnergyModelMapper.getReadyUploadList();
        if (CollectionUtils.isEmpty(models)) {
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year_backup models is null......");
            return;
        }

        //筛选上传失败得
        List<EnterpriseEnergyModel> energyModels = new ArrayList<>();
        for (EnterpriseEnergyModel energyModel:models){
            //年数据失败得
            if (energyModel.getBackupField3().equals("1") && energyModel.getStattype().equals("3") && !energyModel.getInputtype().equals("5")){
                energyModels.add(energyModel);
            }
        }

        if (CollectionUtils.isEmpty(energyModels)){
            LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year_backup energyModels is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String url8008 = getUrlUtils.getUrl(Url8008Type);
        //发送请求
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year_backup softUrl is null......");
            return;
        }
        upload_year(energyModels, apiUrl, url8008, softUrl);
        LOGGER.info("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_year_backup end......");
    }

    private String getToken(EnterpriseEnergyModel model) {
        AKModel akModel = akModelMapper.selectByEP(model.getEnterprisecode());
        if (akModel == null) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 akModel is null......");
            return null;
        }
        String token = akModel.getToken();
        if (Strings.isNullOrEmpty(token)) {
            LOGGER.error("YNEnterpriseEnergyTaskImpl enterPrinseEnergyTaskAutomatic_15 token is null......");
            return null;
        }

        return token;
    }
}

