package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import com.xl.ems.ynnhjc.model.MaterielProductModel;

import java.util.List;
import java.util.Map;

//用能单位非能源产品信息接口
public interface YNCompanyMaterielProductService {

    /**
     * 非能源产品信息增加
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyMaterielProductAdd(Map<String, String> requestMap);

    /**
     *非能源产品信息修改
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyMaterielProductUpdate(Map<String, String> requestMap);

    /**
     *非能源产品信息删除
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyMaterielProductDelete(Map<String, String> requestMap);

    RestResponse<List<MaterielProductModel>> getList(String code);
}
