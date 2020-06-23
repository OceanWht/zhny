package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.mapper.*;
import com.xl.ems.ynnhjc.model.*;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import com.xl.ems.ynnhjc.sm4.PIN;
import com.xl.ems.ynnhjc.sm4.SMS4Main;
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

@Service
public class YNEnterpriseServcieImpl implements YNEnterpriseServcie {

    @Autowired
    SysWorkingCodeModelMapper sysWorkingCodeModelMapper;

    @Autowired
    PlatformBasicdataModelMapper platformBasicdataModelMapper;

    @Autowired
    SysApplicationModelMapper sysApplicationModelMapper;

    @Autowired
    AKModelMapper akModelMapper;

    @Autowired
    VersionInfoModelMapper versionInfoModelMapper;

    @Autowired
    PlatformURLModelMapper platformURLModelMapper;

    @Autowired
    PlagformBasicdataRelModelMapper plagformBasicdataRelModelMapper;

    @Autowired
    BasicdataOtherModelMapper basicdataOtherModelMapper;

    @Autowired
    GenericRest genericRest;


    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    UidRelationshipModelMapper uidRelationshipModelMapper;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.yn_surface}")
    private String softUrlType;

    @Value("${yn-nhjc.download_public_index}")
    private String downloadPublicIndex;

    @Value("${yn-nhjc.init-password}")
    private String initPassWord;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseServcieImpl.class);

    //企业类型编码
    private static final String ENTERPRISE_TYPE = "16";
    //行业类型编码
    private static final String INDUSTRY_TYPE = "2";

    private static final String FIELD_CODE = "18";

    private static final String ENERGY_CONSUME_LEVEL = "19";

    private static final String ORG_TYPE = "20";

    @Override
    public boolean getRWK() {
        LOGGER.info("YNEnterpriseServcieImpl getRWK begin......");
        // SysWorkingCodeModel workingCodeModel = sysWorkingCodeModelMapper.selectByPrimaryKey(2);
        List<SysWorkingCodeModel> workingCodeModels = sysWorkingCodeModelMapper.getAllWorkingCode();
        if (workingCodeModels == null) {
            LOGGER.error("YNEnterpriseServcieImpl getRWK workingCodeModel is null......");
            return false;
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl getRWK apiUrl or softUrl is null......");
            return false;
        }

        Map<String, String> request = new Hashtable<>();

        request.put("softurl", softUrl);
        for (SysWorkingCodeModel workingCodeModel : workingCodeModels) {
            String secretKey = workingCodeModel.getSecretkey();
            //密钥没有有效期，获取后就不更新了,secretKey只在第一次获取
            if (!Strings.isNullOrEmpty(secretKey)) {
                continue;
            }
            request.put("enterpriseCode", workingCodeModel.getEnterprisecode());
            //if (Strings.isNullOrEmpty(secretKey))
            //   request.put("secretKey", workingCodeModel.getSecretkey());
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/rwk", request,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                if (res.getString("responseCode").equals("200")) {
                    secretKey = res.getJSONObject("data").getString("secretKey");
                    workingCodeModel.setSecretkey(secretKey);
                    workingCodeModel.setBackupField1(null);
                    workingCodeModel.setBackupField2(null);
                    workingCodeModel.setBackupField3(null);
                    //更新
                    sysWorkingCodeModelMapper.updateByPrimaryKey(workingCodeModel);
                } else {
                    String responseMessage = res.getString("responseMessage");
                    String responseCode = res.getString("responseCode");
                    workingCodeModel.setBackupField1(responseMessage);
                    workingCodeModel.setBackupField2(responseCode);
                    workingCodeModel.setBackupField3(DateUtils.formatDateTime(new Date()));
                    //记录失败原因
                    sysWorkingCodeModelMapper.updateByPrimaryKey(workingCodeModel);
                    LOGGER.error("YNEnterpriseServcieImpl getRWK error:" + responseCode + ":" + responseMessage + "......");
                }
            }
        }


        LOGGER.info("YNEnterpriseServcieImpl getRWK success......");
        return true;
    }

    /**
     * 企业申请注册前得一些基础数据下载
     * 行政区划代码  行业编码  企业类型编码
     */
    @Override
    public void dataDownloadPublic() {
        LOGGER.info("YNEnterpriseServcieImpl dataDownloadPublic begin......");
        Map<String, String> requestMap = new Hashtable<>();
        SysWorkingCodeModel workingCodeModel = sysWorkingCodeModelMapper.getAllWorkingCode().get(0);
        if (workingCodeModel == null) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic workingCodeModel is null......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic apiUrl or softUrl is null......");
            return;
        }

        requestMap.put("softurl", softUrl);
        requestMap.put("enterpriseCode", workingCodeModel.getEnterprisecode());
        requestMap.put("itemIndex", downloadPublicIndex);
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/dataDownload/public", requestMap,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            RestResponse<JSONObject> result = entity.getBody();
            JSONObject res = null;
            try {
                res = result.getResult();
            } catch (NullPointerException e) {
                LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic res is null......");
                return;
            }

            if (res.getString("responseCode").equals("200")) {
                JSONObject data = res.getJSONObject("data");
                List<PlatformBasicdataModel> enterpriseTypeCodes =
                        data.getJSONArray("EnterpriseTypeCodes").toJavaList(PlatformBasicdataModel.class);
                List<PlatformBasicdataModel> regionCodes =
                        data.getJSONArray("RegionCodes").toJavaList(PlatformBasicdataModel.class);
                List<PlatformBasicdataModel> industryCodes =
                        data.getJSONArray("IndustryCodes").toJavaList(PlatformBasicdataModel.class);
                System.out.println(entity);

                if (CollectionUtils.isEmpty(enterpriseTypeCodes) || CollectionUtils.isEmpty(regionCodes)
                        || CollectionUtils.isEmpty(industryCodes)) {
                    LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic data has error......");
                    return;
                }

                //设置基础数据index类别
                //企业类型
                enterpriseTypeCodes.forEach(e -> {
                    e.setCode(e.getCode().trim());
                    e.setItemindex("16");
                });
                //区域编码
                regionCodes.forEach(r -> {
                    r.setCode(r.getCode().trim());
                    r.setItemindex("1");
                });
                //行业编码
                industryCodes.forEach(i -> {
                    i.setCode(i.getCode().trim());
                    i.setItemindex("2");
                });

                List<PlatformBasicdataModel> enterpriseTypeCodesFromDB
                        = platformBasicdataModelMapper.selectByIntemIndex("16");
                List<PlatformBasicdataModel> diff = getDiff(enterpriseTypeCodes, enterpriseTypeCodesFromDB);
                try {
                    setDiff(enterpriseTypeCodes, enterpriseTypeCodesFromDB, diff);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic enterpriseTypeCodes setDiff hase error......");
                    e.printStackTrace();
                    return;
                }

                List<PlatformBasicdataModel> regionCodesDB
                        = platformBasicdataModelMapper.selectByIntemIndex("1");
                diff = getDiff(regionCodes, regionCodesDB);
                try {
                    setDiff(regionCodes, regionCodesDB, diff);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic get regionCodes failed......");
                    e.printStackTrace();
                    return;
                }


                List<PlatformBasicdataModel> industryCodesDB
                        = platformBasicdataModelMapper.selectByIntemIndex("2");
                diff = getDiff(industryCodes, industryCodesDB);
                try {
                    setDiff(industryCodes, industryCodesDB, diff);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServcieImpl dataDownloadPublic get industryCodes failed......");
                    e.printStackTrace();
                    return;
                }
            }
        }

        LOGGER.info("YNEnterpriseServcieImpl dataDownloadPublic success......");
    }

    private List<PlatformBasicdataModel> getDiff(List<PlatformBasicdataModel> list1, List<PlatformBasicdataModel> list2) {
        long st = System.nanoTime();
        List<PlatformBasicdataModel> diff = new ArrayList<>();
        List<String> diffStr = new ArrayList<>();
        List<PlatformBasicdataModel> maxList = list1;
        List<PlatformBasicdataModel> minList = list2;
        if (list2.size() > list1.size()) {
            maxList = list2;
            minList = list1;
        }

        if (minList.size() == 0) {
            return maxList;
        }

        Map<String, Integer> map = new Hashtable<>(maxList.size());
        for (PlatformBasicdataModel plb : maxList) {
            String key = plb.getCode() + ":" + plb.getName();
            if (map.get(key) == null) {
                map.put(key, 1);
            }
        }

        for (PlatformBasicdataModel plb1 : minList) {
            String key = plb1.getCode() + ":" + plb1.getName();
            if (map.get(key) != null) {
                map.put(key, 2);
                continue;
            }
            diff.add(plb1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                diffStr.add(entry.getKey());
            }
        }

        for (String str : diffStr) {
            String code = str.split(":")[0];
            String name = str.split(":")[1];
            for (PlatformBasicdataModel plb2 : maxList) {
                if (code.trim().equals(plb2.getCode().trim()) && name.trim().equals(plb2.getName().trim())) {
                    diff.add(plb2);
                }
            }
        }


        System.out.println("getDiffrent5 total times " + (System.nanoTime() - st));
        return diff;
    }

    private void setDiff(List<PlatformBasicdataModel> enterpriseTypeCodes, List<PlatformBasicdataModel> enterpriseTypeCodesFromDB,
                         List<PlatformBasicdataModel> diff) throws Exception {
        int ret = 0;
        //diff size为0 表示没有不同得
        if (diff.size() > 0) {
            if (enterpriseTypeCodesFromDB.size() > enterpriseTypeCodes.size()) {
                //删除多余得
                ret = platformBasicdataModelMapper.deleteBanth(diff);
            } else {
                //插入多余得
                ret = platformBasicdataModelMapper.insertBanth(diff);
            }
        }

    }

    @Override
    public void enterpriseInfoApply() {
        LOGGER.info("YNEnterpriseServcieImpl enterpriseInfoApply begin......");
        List<SysApplicationModelWithBLOBs> sysApplicationModels = sysApplicationModelMapper.getAll();
        if (sysApplicationModels == null) {
            LOGGER.error("YNEnterpriseServcieImpl sysApplicationModels has error......");
            return;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl enterpriseInfoApply apiUrl or softUrl is null......");
            return;
        }

        for (SysApplicationModelWithBLOBs sysApplicationModelWithBLOBs : sysApplicationModels) {
            String dataIndex = sysApplicationModelWithBLOBs.getDataindex();
            //有数据索引得表示已经该企业申请注册过了，一个企业只能注册一次
            if (!Strings.isNullOrEmpty(dataIndex)) {
                continue;
            }
            String enterpriseCode = sysApplicationModelWithBLOBs.getEnterprisecode();
            String password = sysApplicationModelWithBLOBs.getPassword();
            //如果初始密码为空，表示未注册过，如果密码不为空，就用原来得密码
            if (Strings.isNullOrEmpty(password)) {
                //设置密码
                String initPass = initPassWord;
                String pinBlock = PIN.printHexString2("", PIN.getHPin(initPass));
                SMS4Main sms4Main = new SMS4Main();
                SysWorkingCodeModel sysWorkingCodeModel = null;
                try {
                    sysWorkingCodeModel = sysWorkingCodeModelMapper.selectByEnterpriseCode(enterpriseCode);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServcieImpl sysWorkingCodeModel has error......");
                    continue;
                }
                if (sysWorkingCodeModel == null) {
                    LOGGER.error("YNEnterpriseServcieImpl sysWorkingCodeModel is null......");
                    continue;
                }
                sms4Main.setSecretKey(sysWorkingCodeModel.getSecretkey());
                String iv = enterpriseCode.substring(0, 16);
                sms4Main.setIv(iv);
                String entext = sms4Main.encryptData_CBC(pinBlock);
                entext = entext.substring(0, 32);
                sysApplicationModelWithBLOBs.setPassword(entext);
            } else {
                continue;
            }

            Map<String, Object> body = new Hashtable<>();

            body.put("softurl", softUrl);
            body.put("SysApplicationModel", sysApplicationModelWithBLOBs);
            //发送请求
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/enterpriseInfo/apply", body,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                if (res == null) {
                    LOGGER.error("YNEnterpriseServcieImpl res is null......");
                    sysApplicationModelWithBLOBs.setBackupField1(String.valueOf(RestCode.UNKNOWN_ERROR.code));
                    sysApplicationModelWithBLOBs.setBackupField2("返回值res为空");
                    sysApplicationModelWithBLOBs.setBackupField3(DateUtils.formatDateTime(new Date()));
                    sysApplicationModelMapper.updateByPrimaryKey(sysApplicationModelWithBLOBs);
                    continue;
                }

                if (res.getInteger("responseCode") == 200) {
                    dataIndex = res.getJSONObject("data").getString("dataIndex");
                    sysApplicationModelWithBLOBs.setDataindex(dataIndex);
                    sysApplicationModelMapper.updateByPrimaryKey(sysApplicationModelWithBLOBs);
                } else {
                    LOGGER.info("YNEnterpriseServcieImpl enterpriseInfoApply apply errorCode:" + res.getInteger("responseCode") + ",errorMsg:" +
                            "" + res.getString("responseMessage") + "......");
                    sysApplicationModelWithBLOBs.setBackupField1(String.valueOf(res.getInteger("responseCode")));
                    sysApplicationModelWithBLOBs.setBackupField2(res.getString("responseMessage"));
                    sysApplicationModelWithBLOBs.setBackupField3(DateUtils.formatDateTime(new Date()));
                    sysApplicationModelMapper.updateByPrimaryKey(sysApplicationModelWithBLOBs);
                }
            }
        }

        LOGGER.info("YNEnterpriseServcieImpl enterpriseInfoApply success......");
    }

    @Override
    public void requestAK() {
        LOGGER.info("YNEnterpriseServcieImpl requestAK begin......");

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl requestAK apiUrl or softUrl is null......");
            return;
        }
        List<SysApplicationModelWithBLOBs> sysApplicationModelWithBLOBs = sysApplicationModelMapper.getAll();
        if (sysApplicationModelWithBLOBs == null) {
            LOGGER.error("YNEnterpriseServcieImpl requestAK sysApplicationModelWithBLOBs has error......");
            return;
        }
        for (SysApplicationModelWithBLOBs sysApplicationModelWithBLOBs1 : sysApplicationModelWithBLOBs) {
            String enterpriseCode = sysApplicationModelWithBLOBs1.getEnterprisecode();
            String pwd = sysApplicationModelWithBLOBs1.getPassword();
            Map<String, String> body = new Hashtable<>();
            body.put("softurl", softUrl);
            body.put("enterpriseCode", enterpriseCode);
            body.put("password", pwd);
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/requestAK", body,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                if (res == null) {
                    LOGGER.error("YNEnterpriseServcieImpl requestAK res is null......");
                    return;
                }

                if (res.getInteger("responseCode") == 200) {
                    AKModel data = res.getJSONObject("data").toJavaObject(AKModel.class);
                    AKModel akModel = akModelMapper.selectByEP(enterpriseCode);
                    if (akModel == null) {
                        try {
                            data.setEnterprisecode(enterpriseCode);
                            data.setPassword(pwd);
                            akModelMapper.insertSelective(data);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl requestAK insertSelective has error......Enterprisecode:{"+sysApplicationModelWithBLOBs1.getEnterprisecode()+"}");
                            return;
                        }

                    } else {
                        akModel.setAkexpiresin(data.getAkexpiresin());
                        akModel.setAkvalidat(data.getAkvalidat());
                        akModel.setPlatformurl(data.getPlatformurl());
                        akModel.setToken(data.getToken());
                        akModel.setEnterpriseuploadtime(data.getEnterpriseuploadtime());
                        try {
                            akModelMapper.updateByPrimaryKeySelective(akModel);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl requestAK updateByPrimaryKeySelective has error......Enterprisecode:{"+sysApplicationModelWithBLOBs1.getEnterprisecode()+"}");
                            return;
                        }

                    }
                } else {
                    LOGGER.error("YNEnterpriseServcieImpl requestAK has error......{" + res.getString("resopnseCode") + ":" + res.getString("responseMessage") + "}.....Enterprisecode:{"+sysApplicationModelWithBLOBs1.getEnterprisecode()+"}");
                }
            }

        }
        LOGGER.info("YNEnterpriseServcieImpl requestAK success......");
    }

    @Override
    public boolean checkVersion() {
        LOGGER.info("YNEnterpriseServcieImpl checkVersion begin......");
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl checkVersion apiUrl or softUrl is null......");
            return false;
        }
        //
        List<AKModel> akModels = akModelMapper.getAll();
        if (CollectionUtils.isEmpty(akModels)) {
            LOGGER.error("YNEnterpriseServcieImpl checkVersion modelWithBLOBs is null......");
            return false;
        }
        //获取请求参数
        String enterpriseCode = "";
        String token = "";
        for (AKModel akModel : akModels) {
            if (Strings.isNullOrEmpty(akModel.getToken())) {
                continue;
            }
            enterpriseCode = akModel.getEnterprisecode();
            token = akModel.getToken();
            //发起请求
            Map<String, String> body = new Hashtable<>();
            body.put("enterpriseCode", enterpriseCode);
            body.put("token", token);
            body.put("softurl", softUrl);
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/checkVersion", body,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                if (res.getInteger("responseCode") == 200) {
                    JSONObject data = res.getJSONObject("data");
                    String dicVersion = data.getString("dicVersion");
                    String loadPlatformDataURL = data.getString("loadPlatformDataURL");
                    String regVersion = data.getString("regVersion");
                    String modifyDescription = data.getString("modifyDescription");
                    VersionInfoModel versionInfoModel = versionInfoModelMapper.selectByDLR(enterpriseCode);
                    if (versionInfoModel == null) {
                        versionInfoModel = new VersionInfoModel();
                        versionInfoModel.setEnterprisecode(enterpriseCode);
                        versionInfoModel.setDicversion(dicVersion);
                        versionInfoModel.setRegversion(regVersion);
                        versionInfoModel.setBackupField1(modifyDescription);
                        versionInfoModel.setLoadplatformdataurl(loadPlatformDataURL);
                        try {
                            versionInfoModelMapper.insertSelective(versionInfoModel);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl checkVersion insertSelective has error......");
                            return false;
                        }

                    } else {
                        if (versionInfoModel.getDicversion().equals(dicVersion) && versionInfoModel.getRegversion().equals(regVersion)
                                && versionInfoModel.getLoadplatformdataurl().equals(loadPlatformDataURL)) {
                            //平台版本无变化，无需更新下载数据
                            LOGGER.info("YNEnterpriseServcieImpl checkVersion success two......");
                            return false;
                        }
                        versionInfoModel.setDicversion(dicVersion);
                        versionInfoModel.setRegversion(regVersion);
                        versionInfoModel.setBackupField1(modifyDescription);
                        versionInfoModel.setLoadplatformdataurl(loadPlatformDataURL);
                        try {
                            versionInfoModelMapper.updateByPrimaryKeySelective(versionInfoModel);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl checkVersion updateByPrimaryKeySelective has error......");
                            return false;
                        }
                    }
                } else if (res.getInteger("responseCode") == 401) {
                    //"AK验证失败,请确认AK的完整性和有效性!"
                    requestAK();
                    checkVersion();
                } else {
                    LOGGER.error("YNEnterpriseServcieImpl checkVersion has error......" + res.getString("responseCode")
                            + ":" + res.getString("responseMessage"));
                    return false;
                }
            }
        }


        LOGGER.info("YNEnterpriseServcieImpl checkVersion success......");
        return true;
    }

    @Override
    public void dataDownload() {
        LOGGER.info("YNEnterpriseServcieImpl dataDownload begin......");
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownload apiUrl or softUrl is null......");
            return;
        }

        //基础数据与企业不是强相关，但是必须带一个企业code
        AKModel akModel = akModelMapper.getAll().get(0);
        if (akModel == null) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownload akModel is null......");
            return;
        }
        String token = akModel.getToken();
        String enterpriseCode = akModel.getEnterprisecode();
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(enterpriseCode)) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownload token or enterpriseCode is null......");
            return;
        }
        List<PlagformBasicdataRelModel> plagformBasicdataRelModels = plagformBasicdataRelModelMapper.getAll();
        if (CollectionUtils.isEmpty(plagformBasicdataRelModels)) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownload plagformBasicdataRelModels is null......");
            return;
        }
        String itemIndex = getItemIndex(plagformBasicdataRelModels);
        if (Strings.isNullOrEmpty(itemIndex)) {
            LOGGER.error("YNEnterpriseServcieImpl dataDownload itemIndex is null......");
            return;
        }

        Map<String, String> body = new Hashtable<>();
        body.put("softurl", softUrl);
        body.put("token", token);
        body.put("itemIndex", itemIndex);
        body.put("enterpriseCode", enterpriseCode);
        ResponseEntity<RestResponse<JSONObject>> entity =
                genericRest.post(apiUrl + "/dataDownload", body,
                        new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                        });

        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res.getInteger("responseCode") == 200) {
                JSONObject data = res.getJSONObject("data");
                if (data == null) {
                    LOGGER.error("YNEnterpriseServcieImpl dataDownload data is null......");
                    return;
                }
                for (PlagformBasicdataRelModel plbr : plagformBasicdataRelModels) {
                    if (plbr.getItemindex().equals(1) || plbr.getItemindex().equals(2) || plbr.getItemindex().equals(16)) {
                        continue;
                    }

                    List<PlatformBasicdataModel> resFromDB = platformBasicdataModelMapper.
                            selectByIntemIndex(String.valueOf(plbr.getItemindex()));

                    List<PlatformBasicdataModel> resPlbd =
                            data.getJSONArray(plbr.getBasicdataname()).toJavaList(PlatformBasicdataModel.class);
                    if (CollectionUtils.isEmpty(resPlbd)) {
                        continue;
                    }

                    //设置ItemIndex
                    for (PlatformBasicdataModel platformBasicdataModel : resPlbd) {
                        platformBasicdataModel.setItemindex(String.valueOf(plbr.getItemindex()));
                    }

                    List<PlatformBasicdataModel> diff = getDiff(resPlbd, resFromDB);
                    try {
                        setDiff(resPlbd, resFromDB, diff);
                    } catch (Exception e) {
                        LOGGER.error("YNEnterpriseServcieImpl dataDownload " + plbr.getBasicdataname() + ":" + plbr.getItemindex() + " setDiff hase error......");
                        e.printStackTrace();
                        return;
                    }

                    /*try {
                        platformBasicdataModelMapper.insertBanth(resPlbd);
                    } catch (Exception e) {
                        LOGGER.error("YNEnterpriseServcieImpl dataDownload platformBasicdataModelMapper insertBanth has error......");
                        return;
                    }*/


                }
            } else if (res.getInteger("responseCode") == 401) {
                requestAK();
                this.dataDownload();
            } else {
                LOGGER.error("YNEnterpriseServcieImpl checkVersion has error......" + res.getString("responseCode")
                        + ":" + res.getString("responseMessage"));
                return;
            }
        }
        LOGGER.info("YNEnterpriseServcieImpl dataDownload success......");
    }

    private String getItemIndex(List<PlagformBasicdataRelModel> plagformBasicdataRelModels) {
        StringBuilder sb = new StringBuilder();
        for (PlagformBasicdataRelModel p : plagformBasicdataRelModels) {
            if (p.getItemindex().equals(1) || p.getItemindex().equals(2) || p.getItemindex().equals(16)) {
                continue;
            }
            sb.append(p.getItemindex()).append(",");
        }

        return sb.substring(0, sb.lastIndexOf(","));
    }

    /**
     * 平台基础配置地址查询
     */
    @Override
    public void platformAccessURL() {
        LOGGER.info("YNEnterpriseServcieImpl platformAccessURL begin......");
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServcieImpl platformAccessURL apiUrl or softUrl is null......");
            return;
        }

        List<AKModel> akModels = akModelMapper.getAll();
        if (CollectionUtils.isEmpty(akModels)) {
            LOGGER.error("YNEnterpriseServcieImpl platformAccessURL akModel  is null......");
            return;
        }

        for (AKModel akModel : akModels) {
            Map<String, String> body = new Hashtable<>();
            body.put("enterpriseCode", akModel.getEnterprisecode());
            body.put("token", akModel.getToken());
            body.put("softurl", softUrl);
            ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/platformAccessURL", body,
                    new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                    });
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                JSONObject res = entity.getBody().getResult();
                PlatformURLModel data = res.getJSONObject("data").toJavaObject(PlatformURLModel.class);
                if (data == null) {
                    LOGGER.error("YNEnterpriseServcieImpl platformAccessURL platformURLModel  is null......");
                    return;
                }
                if (res.getInteger("responseCode") == 200) {
                    PlatformURLModel platformURLModel = platformURLModelMapper.selectByETP(akModel.getEnterprisecode());
                    //新增
                    if (platformURLModel == null) {
                        try {
                            platformURLModelMapper.insertSelective(data);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl platformAccessURL insertSelective  has error......");
                            return;
                        }
                    } else {
                        //更新
                        platformURLModel.setEnterprisedatadownloadurl(data.getEnterprisedatadownloadurl());
                        platformURLModel.setEnterprisedataurl(data.getEnterprisedataurl());
                        platformURLModel.setEnterpriseinfourl(data.getEnterpriseinfourl());
                        platformURLModel.setLoadbasedataurl(data.getLoadbasedataurl());
                        platformURLModel.setOrganizationdataurl(data.getOrganizationdataurl());
                        platformURLModel.setEnterpriseinfodownloadurl(data.getEnterpriseinfodownloadurl());
                        try {
                            platformURLModelMapper.updateByPrimaryKey(platformURLModel);
                        } catch (Exception e) {
                            LOGGER.error("YNEnterpriseServcieImpl platformAccessURL updateByPrimaryKey  has error......");
                            return;
                        }
                    }

                } else if (res.getInteger("responseCode") == 401) {
                    //Token失效时
                    requestAK();
                    platformAccessURL();
                } else {
                    LOGGER.error("YNEnterpriseServcieImpl checkVersion has error......" + res.getString("responseCode")
                            + ":" + res.getString("responseMessage"));
                    return;
                }
            }
        }

        LOGGER.info("YNEnterpriseServiceImpl platformAccessURL success......");
    }

    @Override
    public RestResponse<JSONObject> apply(JSONObject requestBody) {
        LOGGER.info("YNEnterpriseServiceImpl apply begin......");
        if (requestBody == null) {
            LOGGER.error("YNEnterpriseServiceImpl apply has error......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        JSONObject requestJson = requestBody.getJSONObject("data");
        SysApplicationModelWithBLOBs sysApplicationModel = getSysApplicationModelWithBLOBs(requestJson);

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServiceImpl apply apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = sysApplicationModel.getEnterprisecode();
        SysApplicationModelWithBLOBs sysApplicationModelWithBLOBs = sysApplicationModelMapper.selectByEC(enterpriseCode);
      /*  if (sysApplicationModelWithBLOBs != null && !Strings.isNullOrEmpty(sysApplicationModelWithBLOBs.getDataindex())) {
            LOGGER.error("YNEnterpriseServiceImpl apply query sysApplicationModelWithBLOBs is already existed......");
            return RestResponse.error(RestCode.WRONG_APPLY);
        }*/
        //设置密码
        if (setPassWord(sysApplicationModel, enterpriseCode)) {
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        Map<String, Object> body = new Hashtable<>();
        body.put("softurl", softUrl);
        body.put("SysApplicationModel", sysApplicationModel);
        //发送请求
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/enterpriseInfo/apply", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res == null) {
                LOGGER.error("YNEnterpriseServiceImpl apply res is null......");
                return RestResponse.error(RestCode.UNKNOWN_ERROR);
            }

            if (res.getInteger("responseCode") == 200) {
                JSONObject data = res.getJSONObject("data");
                sysApplicationModel.setDataindex(data.getString("dataIndex"));
                try {
                    sysApplicationModelMapper.insertSelective(sysApplicationModel);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServiceImpl apply insertSelective has error......");
                    return RestResponse.error(RestCode.UNKNOWN_ERROR);
                }
            } else {
                LOGGER.error("YNEnterpriseServiceImpl apply res has error{"+res.getString("responseCode")+":"+res.getString("responseMessage")+"......");
                return RestResponse.error(res);
            }

        }

        LOGGER.info("YNEnterpriseServiceImpl apply end......");
        return RestResponse.success();
    }

    private SysApplicationModelWithBLOBs getSysApplicationModelWithBLOBs(JSONObject requestJson) {
        SysApplicationModelWithBLOBs sysApplicationModel = new SysApplicationModelWithBLOBs();
        //设置请求model
        String area = requestJson.getString("area");
        String city = requestJson.getString("city");
        String province = requestJson.getString("province");
        //设置所属地区
        if (!Strings.isNullOrEmpty(area)) {
            sysApplicationModel.setRegioncode(area);
        } else if (!Strings.isNullOrEmpty(city)) {
            sysApplicationModel.setRegioncode(city);
        } else if (!Strings.isNullOrEmpty(province)) {
            sysApplicationModel.setRegioncode(province);
        }
        String typeCode = requestJson.getString("typeCode");
        String typeCodeTwo = requestJson.getString("typeCodeTwo");
        //单位类型
        if (!Strings.isNullOrEmpty(typeCodeTwo)){
            sysApplicationModel.setTypecode(typeCodeTwo);
        }else if (!Strings.isNullOrEmpty(typeCode)){
            sysApplicationModel.setTypecode(typeCode);
        }

        //所属行业
        String IndustryCodesOne = requestJson.getString("IndustryCodesOne");
        String IndustryCodesTwo = requestJson.getString("IndustryCodesTwo");
        String IndustryCodesThree = requestJson.getString("IndustryCodesThree");
        String IndustryCodesFour = requestJson.getString("IndustryCodesFour");
        if (!Strings.isNullOrEmpty(IndustryCodesFour)) {
            sysApplicationModel.setIndustrycode(IndustryCodesFour);
        } else if (!Strings.isNullOrEmpty(IndustryCodesThree)) {
            sysApplicationModel.setIndustrycode(IndustryCodesThree);
        } else if (!Strings.isNullOrEmpty(IndustryCodesTwo)) {
            sysApplicationModel.setIndustrycode(IndustryCodesTwo);
        } else if (!Strings.isNullOrEmpty(IndustryCodesOne)) {
            sysApplicationModel.setIndustrycode(IndustryCodesOne);
        }

        //单位名称
        sysApplicationModel.setCmpname(requestJson.getString("cmpName"));
        //统一社会信用代码
        sysApplicationModel.setEnterprisecode(requestJson.getString("enterpriseCode"));
        //法人代表
        sysApplicationModel.setCorporationname(requestJson.getString("corporationName"));
        //所属领域
        sysApplicationModel.setFieldcode(requestJson.getString("fieldCode"));
        //企业耗能级别
        sysApplicationModel.setEnergyconsumelevel(requestJson.getString("energyConsumeLevel"));
        //是否是公共机构
        sysApplicationModel.setIsorg(requestJson.getString("isorg"));
        //机构类型
        String orgType = requestJson.getString("orgType");
        String orgTypeChild = requestJson.getString("orgTypeChild");
        if (!Strings.isNullOrEmpty(orgTypeChild)) {
            sysApplicationModel.setOrgtype(orgTypeChild);
        } else if (!Strings.isNullOrEmpty(orgType)) {
            sysApplicationModel.setOrgtype(orgType);
        }

        //组织机构代码
        sysApplicationModel.setOrgcode(requestJson.getString("orgCode"));
        //是否央企
        sysApplicationModel.setCenter(requestJson.getString("center"));
        //集团名称
        sysApplicationModel.setGroupname(requestJson.getString("groupName"));
        //集团地址
        sysApplicationModel.setGroupaddress(requestJson.getString("groupAddress"));
        //是否能源加工转换类企业
        sysApplicationModel.setJgzh(requestJson.getString("jgzh"));
        //单位注册日期
        sysApplicationModel.setRegisterdate(requestJson.getString("registerDate"));
        //单位注册资本
        sysApplicationModel.setRegisterprincipal(requestJson.getString("registerPrincipal"));
        //企业注册地址
        sysApplicationModel.setRegisteraddress(requestJson.getString("registerAddress"));
        //企业地址
        sysApplicationModel.setAddress(requestJson.getString("address"));
        //企业网站地址
        sysApplicationModel.setUrl(requestJson.getString("url"));
        //邮政编码
        sysApplicationModel.setZipcode(requestJson.getString("zipCode"));
        //传真
        sysApplicationModel.setFax(requestJson.getString("fax"));
        sysApplicationModel.setPhone(requestJson.getString("phone"));
        //email
        sysApplicationModel.setEmail(requestJson.getString("email"));
        //经度
        sysApplicationModel.setLongitude(requestJson.getString("longitude"));
        sysApplicationModel.setLatitude(requestJson.getString("latitude"));
        //生产线名称
        sysApplicationModel.setProductionline(requestJson.getString("productionLine"));
        //主导产品
        sysApplicationModel.setLeadingproduct(requestJson.getString("leadingProduct"));
        sysApplicationModel.setRemark(requestJson.getString("remark"));
        sysApplicationModel.setLicense(requestJson.getString("license"));
        sysApplicationModel.setOrganization(requestJson.getString("organization"));
        return sysApplicationModel;
    }

    private boolean setPassWord(SysApplicationModelWithBLOBs sysApplicationModel, String enterpriseCode) {
        SysWorkingCodeModel sysWorkingCodeModel = null;
        try {
            sysWorkingCodeModel = sysWorkingCodeModelMapper.selectByEnterpriseCode(enterpriseCode);
        } catch (Exception e) {
            LOGGER.error("YNEnterpriseServiceImpl apply sysWorkingCodeModel has error......");
            return true;
        }
        if (sysWorkingCodeModel == null) {
            LOGGER.error("YNEnterpriseServiceImpl apply sysWorkingCodeModel is null......");
            return true;
        }
        //设置密码
        String initPass = initPassWord;
        String pinBlock = PIN.printHexString2("", PIN.getHPin(initPass));
        SMS4Main sms4Main = new SMS4Main();
        sms4Main.setSecretKey(sysWorkingCodeModel.getSecretkey());
        String iv = enterpriseCode.substring(0, 16);
        sms4Main.setIv(iv);
        String entext = sms4Main.encryptData_CBC(pinBlock);
        entext = entext.substring(0, 32);
        sysApplicationModel.setPassword(entext);
        return false;
    }

    @Override
    public RestResponse<JSONObject> update(JSONObject requestBody) {
        LOGGER.info("YNEnterpriseServiceImpl update begin......");
        if (requestBody == null) {
            LOGGER.error("YNEnterpriseServiceImpl update has error......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        JSONObject resJson = requestBody.getJSONObject("data");
        SysApplicationModelWithBLOBs sysApplicationModel = getSysApplicationModelWithBLOBs(resJson);

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("YNEnterpriseServiceImpl update apiUrl or softUrl is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = resJson.getString("enterpriseCode");
        SysApplicationModelWithBLOBs sysApplicationModelWithBLOBs = sysApplicationModelMapper.selectByEC(enterpriseCode);
        if (sysApplicationModelWithBLOBs == null) {
            LOGGER.error("YNEnterpriseServiceImpl update query sysApplicationModelWithBLOBs is not existed......");
            return RestResponse.error(RestCode.WRONG_APPLY_UPDATE);
        }
        //设置密码
        sysApplicationModel.setPassword(sysApplicationModelWithBLOBs.getPassword());

        Map<String, Object> body = new Hashtable<>();

        body.put("softurl", softUrl);
        body.put("SysApplicationModel", sysApplicationModelWithBLOBs);
        //发送请求
        ResponseEntity<RestResponse<JSONObject>> entity = genericRest.post(apiUrl + "/enterpriseInfo/update", body,
                new ParameterizedTypeReference<RestResponse<JSONObject>>() {
                });
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            JSONObject res = entity.getBody().getResult();
            if (res == null) {
                LOGGER.error("YNEnterpriseServiceImpl update res is null......");
                return RestResponse.error(RestCode.WRONG_APPLY_UPDATE);
            }

            if (res.getInteger("responseCode") == 200) {
                JSONObject data = res.getJSONObject("data");
                sysApplicationModel.setDataindex(data.getString("dataIndex"));
                sysApplicationModel.setId(sysApplicationModelWithBLOBs.getId());
                try {
                    sysApplicationModelMapper.updateByPrimaryKeySelective(sysApplicationModel);
                } catch (Exception e) {
                    LOGGER.error("YNEnterpriseServiceImpl update insertSelective has error......");
                    return RestResponse.error(RestCode.WRONG_APPLY_UPDATE);
                }
            } else {
                LOGGER.error("YNEnterpriseServiceImpl update has error{"+res.getString("responseCode")+":"+res.getString("responseMessage")+"......");
                return RestResponse.error(res);
            }
        }

        LOGGER.info("YNEnterpriseServiceImpl update end......");
        return RestResponse.success();
    }

    @Override
    public JSONObject getBasicData(String enterpriseCode) {
        LOGGER.info("YNEnterpriseServiceImpl getBasicData begin......");
        JSONObject res = new JSONObject();
        //获取企业类型编码，只需要获取第一层
        List<PlatformBasicdataModel> companyTypeList = new ArrayList<>();
        List<PlatformBasicdataModel> companyTypeListTwo = new ArrayList<>();
        List<PlatformBasicdataModel> companyTypeListDB = platformBasicdataModelMapper.selectByIntemIndex(ENTERPRISE_TYPE);
        if (CollectionUtils.isEmpty(companyTypeListDB)) {
            LOGGER.info("YNEnterpriseServiceImpl getBasicData companyTypeListDB is null......");
            return null;
        }
        for (PlatformBasicdataModel p : companyTypeListDB) {
            String code = p.getCode();
            if (code.substring(1).equals("000")) {
                companyTypeList.add(p);
            }

            if (code.substring(2).equals("00") && !code.substring(1).equals("000")){
                companyTypeListTwo.add(p);
            }
        }
        if (!CollectionUtils.isEmpty(companyTypeList)) {
            res.put("energyTypeList", companyTypeList);
        }

        if (!CollectionUtils.isEmpty(companyTypeListTwo)) {
            res.put("energyTypeListTwo", companyTypeListTwo);
        }

        //获取行业编码
        List<PlatformBasicdataModel> industryTypeList = new ArrayList<>();
        List<PlatformBasicdataModel> industryTypeListOne = new ArrayList<>();
        List<PlatformBasicdataModel> industryTypeListTwo = new ArrayList<>();
        List<PlatformBasicdataModel> industryTypeListThree = new ArrayList<>();
        List<PlatformBasicdataModel> industryTypeListDB = platformBasicdataModelMapper.selectByIntemIndex(INDUSTRY_TYPE);
        if (CollectionUtils.isEmpty(industryTypeListDB)) {
            LOGGER.info("YNEnterpriseServiceImpl getBasicData industryTypeListDB is null......");
            return null;
        }
        for (PlatformBasicdataModel p : industryTypeListDB) {
            String code = p.getCode();
            if (code.length() == 1) {
                industryTypeList.add(p);
            }
            if (code.length() == 3) {
                industryTypeListOne.add(p);
            }
            if (code.length() == 4) {
                industryTypeListTwo.add(p);
            }
            if (code.length() == 5) {
                industryTypeListThree.add(p);
            }
        }

         /*setChild(industryTypeList, industryTypeListOne);

        setChild(industryTypeListOne, industryTypeListTwo);

        setChild(industryTypeListTwo, industryTypeListThree);*/

        if (!CollectionUtils.isEmpty(industryTypeList)) {
            res.put("IndustryCodesOneList", industryTypeList);
        }

        if (!CollectionUtils.isEmpty(industryTypeList)) {
            res.put("IndustryCodesTwoList", industryTypeListOne);
        }

        if (!CollectionUtils.isEmpty(industryTypeList)) {
            res.put("IndustryCodesThreeList", industryTypeListTwo);
        }

        if (!CollectionUtils.isEmpty(industryTypeList)) {
            res.put("IndustryCodesFourList", industryTypeListThree);
        }

        //获取其他基础数据
        List<BasicdataOtherModel> otherModels = basicdataOtherModelMapper.getAll();
        if (CollectionUtils.isEmpty(otherModels)) {
            LOGGER.error("YNEnterpriseServiceImpl getBasicData otherModels is null......");
            return null;
        }

        List<BasicdataOtherModel> fieldCode = new ArrayList<>();
        List<BasicdataOtherModel> energyConsumeLevel = new ArrayList<>();
        List<BasicdataOtherModel> orgType = new ArrayList<>();
        for (BasicdataOtherModel b : otherModels) {

            if (b.getItemindex().equals(FIELD_CODE)) {
                fieldCode.add(b);
            } else if (b.getItemindex().equals(ENERGY_CONSUME_LEVEL)) {
                energyConsumeLevel.add(b);
            } else if (b.getItemindex().equals(ORG_TYPE)) {
                orgType.add(b);
            }
        }

        if (!CollectionUtils.isEmpty(fieldCode)) {
            res.put("filedCodeList", fieldCode);
        }

        if (!CollectionUtils.isEmpty(energyConsumeLevel)) {
            res.put("energyConsumeLevelList", energyConsumeLevel);
        }

        if (!CollectionUtils.isEmpty(orgType)) {
            res.put("orgTypeList", orgType);
        }

        //获取注册信息，如果没有提示注册，如果有就默认展示
        SysApplicationModelWithBLOBs applicationModel =
                sysApplicationModelMapper.selectByEC(enterpriseCode);
        if (applicationModel == null) {
            res.put("applicationCode", 404);
        } else {
            /*String regioncode = applicationModel.getRegioncode();
            if (!Strings.isNullOrEmpty(regioncode)){
                Set<PlatformBasicdataModel> basicdataModels = platformBasicdataModelMapper.selectByCode(regioncode,"1");
                if (!CollectionUtils.isEmpty(basicdataModels)&&basicdataModels.size()==1){
                    List<PlatformBasicdataModel> basicdataModelList = new ArrayList<>(basicdataModels);
                    applicationModel.setRegioncode(basicdataModelList.get(0).getName());
                }
            }
            String industryCode = applicationModel.getIndustrycode();
            if (!Strings.isNullOrEmpty(industryCode)){
                Set<PlatformBasicdataModel> basicdataModels = platformBasicdataModelMapper.selectByCode(industryCode,"2");
                if (!CollectionUtils.isEmpty(basicdataModels)&&basicdataModels.size()==1){
                    List<PlatformBasicdataModel> basicdataModelList = new ArrayList<>(basicdataModels);
                    applicationModel.setIndustrycode(basicdataModelList.get(0).getName());
                }
            }

            String typeCode = applicationModel.getTypecode();
            if (!Strings.isNullOrEmpty(industryCode)){
                Set<PlatformBasicdataModel> basicdataModels = platformBasicdataModelMapper.selectByCode(typeCode,"16");
                if (!CollectionUtils.isEmpty(basicdataModels)&&basicdataModels.size()==1){
                    List<PlatformBasicdataModel> basicdataModelList = new ArrayList<>(basicdataModels);
                    applicationModel.setTypecode(basicdataModelList.get(0).getName());
                }
            }*/
            res.put("applicationCode", 200);
            res.put("applicationModel", applicationModel);
        }

        LOGGER.info("YNEnterpriseServiceImpl getBasicData end......");
        return res;
    }

    @Override
    public RestResponse<JSONObject> login(JSONObject requestJson) {
        LOGGER.info("YNEnterpriseServiceImpl login begin......");
        JSONObject res = new JSONObject();
        if (requestJson == null){
            LOGGER.info("YNEnterpriseServiceImpl login requestJson is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String uid = requestJson.getString("uid");
        String name = requestJson.getString("name");
        String pass = requestJson.getString("pass");
        String token = requestJson.getString("token");
        String userid = requestJson.getString("userid");
        UidRelationshipModel uidRelationshipModel = uidRelationshipModelMapper.selectByUID(uid);
        if (uidRelationshipModel == null){
            LOGGER.info("YNEnterpriseServiceImpl login uidRelationshipModel is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String enterpriseCode = uidRelationshipModel.getEnterpriseCode();
        res.put("enterpriseCode",enterpriseCode);
        UserInfoModel userInfoModel = userInfoModelMapper.selectByUserId(userid);
        if (userInfoModel == null){
            userInfoModel = new UserInfoModel();
            userInfoModel.setName(name);
            userInfoModel.setPass(pass);
            userInfoModel.setUserid(userid);
            userInfoModel.setUid(uid);
            userInfoModel.setToken(token);
            userInfoModelMapper.insertSelective(userInfoModel);
        }else {
            userInfoModel.setToken(token);
            userInfoModelMapper.updateByPrimaryKeySelective(userInfoModel);
        }
        res.put("userInfoModel",userInfoModel);
        LOGGER.info("YNEnterpriseServiceImpl getBasicData end......");
        return RestResponse.success(res);
    }

    private void setChild(List<PlatformBasicdataModel> industryTypeList, List<PlatformBasicdataModel> industryTypeListOne) {
        for (PlatformBasicdataModel p1 : industryTypeList) {
            List<PlatformBasicdataModel> child = new ArrayList<>();
            for (PlatformBasicdataModel p2 : industryTypeListOne) {
                if (p2.getCode().contains(p1.getCode())) {
                    child.add(p2);
                }
            }
            if (!CollectionUtils.isEmpty(child)) {
                p1.setChildren(child);
            }
        }
    }


}
