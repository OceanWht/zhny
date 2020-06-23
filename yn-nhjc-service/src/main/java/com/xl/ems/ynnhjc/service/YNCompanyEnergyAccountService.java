package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;

import java.util.List;
import java.util.Map;

//用能单位水、电、燃气户号信息接口
public interface YNCompanyEnergyAccountService {

    /**
     *能源账号信息增加
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyAccountAdd(Map<String, String> requestMap);

    /**
     *能源账号信息修改
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyAccountUpdate(Map<String, String> requestMap);

    /**
     *能源账号信息删除
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyAccountDelete(Map<String, String> requestMap);

    RestResponse<List<EnergyAccountModel>> getList(String code);
}
