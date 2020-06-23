package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.CompanyContacterModel;
import com.xl.ems.ynnhjc.model.VersionInfoModel;

import java.util.List;
import java.util.Map;

public interface YNCompanyContacterService {
    RestResponse<JSONObject> add(CompanyContacterModel companyContacterModel);

    RestResponse<JSONObject> update(CompanyContacterModel companyContacterModel);

    RestResponse<JSONObject> delete(CompanyContacterModel companyContacterModel);

    List<CompanyContacterModel> list(String enterpriseCode);

    VersionInfoModel getVersion(String enterpriseCode);

    List<Map<String, Object>> getDataCode(String enterpriseCode);
}
