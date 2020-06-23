package com.xl.ems.ynnhjc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.controller.YNOtherSysConfigController;
import com.xl.ems.ynnhjc.mapper.SysWorkingCodeModelMapper;
import com.xl.ems.ynnhjc.mapper.UidRelationshipModelMapper;
import com.xl.ems.ynnhjc.model.SysWorkingCodeModel;
import com.xl.ems.ynnhjc.model.UidRelationshipModel;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import com.xl.ems.ynnhjc.service.YNOtherSysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName YNOtherSysConfigServiceImpl
 * @Description TODO
 * @Author wht
 * @Date 13:59
 **/
@Service
public class YNOtherSysConfigServiceImpl implements YNOtherSysConfigService {

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    @Autowired
    UidRelationshipModelMapper uidRelationshipModelMapper;

    @Autowired
    SysWorkingCodeModelMapper sysWorkingCodeModelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNOtherSysConfigServiceImpl.class);

    @Override
    public List<UidRelationshipModel> getUidRelList() {
        return uidRelationshipModelMapper.getAll();
    }

    @Override
    public RestResponse<JSONObject> addUidRel(JSONObject request) {
        UidRelationshipModel uidRelationshipModel = request.getJSONObject("data").toJavaObject(UidRelationshipModel.class);
        if (uidRelationshipModel == null){
                return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String uid = uidRelationshipModel.getUid();
        UidRelationshipModel relationshipModel = uidRelationshipModelMapper.selectByUID(uid);
        if (relationshipModel != null){
            return  RestResponse.error(RestCode.SAVE_FAIL);
        }else {
            uidRelationshipModelMapper.insertSelective(uidRelationshipModel);
        }
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> updateUidRel(JSONObject request) {
        UidRelationshipModel uidRelationshipModel = request.getJSONObject("data").toJavaObject(UidRelationshipModel.class);
        if (uidRelationshipModel == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String uid = uidRelationshipModel.getUid();
        UidRelationshipModel relationshipModel = uidRelationshipModelMapper.selectByUID(uid);
        if (relationshipModel == null){
            return  RestResponse.error(RestCode.UPDATE_FAIL);
        }else {
            relationshipModel.setEnterpriseCode(uidRelationshipModel.getEnterpriseCode());
            uidRelationshipModelMapper.updateByPrimaryKeySelective(uidRelationshipModel);
        }
        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> deleteUidRel(JSONObject request) {
        UidRelationshipModel uidRelationshipModel = request.getJSONObject("data").toJavaObject(UidRelationshipModel.class);
        if (uidRelationshipModel == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        String uid = uidRelationshipModel.getUid();
        UidRelationshipModel relationshipModel = uidRelationshipModelMapper.selectByUID(uid);
        if (relationshipModel == null){
            return  RestResponse.error(RestCode.DELETE_FAIL);
        }else {
            uidRelationshipModelMapper.deleteByPrimaryKey(uidRelationshipModel.getId());
        }
        return RestResponse.success();
    }

    @Override
    public List<SysWorkingCodeModel> getWorkingCode() {

        return sysWorkingCodeModelMapper.getAllWorkingCode();
    }

    @Override
    public RestResponse<JSONObject> addWorkingCode(JSONObject request) {
        SysWorkingCodeModel sysWorkingCodeModel = request.getJSONObject("data").toJavaObject(SysWorkingCodeModel.class);
        if (sysWorkingCodeModel == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        SysWorkingCodeModel workingCodeModel = sysWorkingCodeModelMapper.selectByEnterpriseCode(sysWorkingCodeModel.getEnterprisecode());
        if (workingCodeModel == null){
            workingCodeModel = new SysWorkingCodeModel();
            workingCodeModel.setEnterprisecode(sysWorkingCodeModel.getEnterprisecode());
            sysWorkingCodeModelMapper.insertSelective(workingCodeModel);
        }else {
            RestResponse.error(RestCode.SAVE_FAIL);
        }

        return RestResponse.success();

    }

    @Override
    public RestResponse<JSONObject> updateWorkingCode(JSONObject request) {
       SysWorkingCodeModel sysWorkingCodeModel = request.getJSONObject("data").toJavaObject(SysWorkingCodeModel.class);
        if (sysWorkingCodeModel == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        SysWorkingCodeModel workingCodeModel = sysWorkingCodeModelMapper.selectByPrimaryKey(sysWorkingCodeModel.getId());
        if (workingCodeModel != null){
            workingCodeModel.setEnterprisecode(sysWorkingCodeModel.getEnterprisecode());
            sysWorkingCodeModelMapper.updateByPrimaryKeySelective(workingCodeModel);
        }else {
            RestResponse.error(RestCode.UPDATE_FAIL);
        }

        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> deleteWorkingCode(JSONObject request) {
        SysWorkingCodeModel sysWorkingCodeModel = request.getJSONObject("data").toJavaObject(SysWorkingCodeModel.class);
        if (sysWorkingCodeModel == null){
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        SysWorkingCodeModel workingCodeModel = sysWorkingCodeModelMapper.selectByPrimaryKey(sysWorkingCodeModel.getId());
        if (workingCodeModel != null){
            sysWorkingCodeModelMapper.deleteByPrimaryKey(sysWorkingCodeModel.getId());
        }else {
            RestResponse.error(RestCode.DELETE_FAIL);
        }

        return RestResponse.success();
    }

    @Override
    public RestResponse<JSONObject> rwk() {
        LOGGER.info("YNOtherSysConfigServiceImpl rwk begin......");
        //获取工作密钥
        ynEnterpriseServcie.getRWK();
        //端系统基础数据下载
        ynEnterpriseServcie.dataDownloadPublic();
        //端系统注册申请
        ynEnterpriseServcie.enterpriseInfoApply();

        //获取AK token
        try {
            ynEnterpriseServcie.requestAK();
        }catch (Exception e){

            LOGGER.error("YNOtherSysConfigServiceImpl rwk has error......"+e.getMessage());
            RestResponse.success();
        }


        ynEnterpriseServcie.checkVersion();
        //平台基础配置地址查询
        ynEnterpriseServcie.platformAccessURL();

        //下载基础数据
        ynEnterpriseServcie.dataDownload();
        LOGGER.info("YNOtherSysConfigServiceImpl rwk end......");
        return RestResponse.success();
    }
}
