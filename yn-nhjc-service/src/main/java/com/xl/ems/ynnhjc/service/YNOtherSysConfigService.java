package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.SysWorkingCodeModel;
import com.xl.ems.ynnhjc.model.UidRelationshipModel;

import java.util.List;

public interface YNOtherSysConfigService {
    List<UidRelationshipModel> getUidRelList();

    RestResponse<JSONObject>  addUidRel(JSONObject request);

    RestResponse<JSONObject> updateUidRel(JSONObject request);

    RestResponse<JSONObject> deleteUidRel(JSONObject request);

    List<SysWorkingCodeModel> getWorkingCode();

    RestResponse<JSONObject> addWorkingCode(JSONObject request);

    RestResponse<JSONObject> updateWorkingCode(JSONObject request);

    RestResponse<JSONObject> deleteWorkingCode(JSONObject request);

    RestResponse<JSONObject> rwk();
}
