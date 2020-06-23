package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.SysApplicationModelWithBLOBs;

public interface YNEnterpriseServcie {
    boolean getRWK();

    void dataDownloadPublic();

    void enterpriseInfoApply();

    void requestAK();

    boolean checkVersion();

    void dataDownload();

    void platformAccessURL();


    RestResponse<JSONObject> apply(JSONObject sysApplicationModelWithBLOBs);

    RestResponse<JSONObject> update(JSONObject sysApplicationModelWithBLOBs);

    JSONObject getBasicData(String code);

    RestResponse<JSONObject> login(JSONObject requestJson);
}
