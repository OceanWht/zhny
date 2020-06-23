package com.xl.ems.apigateway.service.ynservice;

import com.alibaba.fastjson.JSONObject;

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
}
