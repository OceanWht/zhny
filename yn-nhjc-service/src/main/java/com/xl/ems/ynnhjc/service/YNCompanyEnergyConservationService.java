package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.model.EnergyConservationModel;

import java.util.List;
import java.util.Map;

//用能单位节能项目情况信息接口
public interface YNCompanyEnergyConservationService {

    /**
     *节能项目情况增加
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyConservationAdd(Map<String, String> requestMap);

    /**
     *节能项目情况修改
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyConservationUpdate(Map<String, String> requestMap);

    /**
     *节能项目情况删除
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyEnergyConservationDelete(Map<String, String> requestMap);

    List<EnergyConservationModel> list(Map<String, String> requestMap);
}
