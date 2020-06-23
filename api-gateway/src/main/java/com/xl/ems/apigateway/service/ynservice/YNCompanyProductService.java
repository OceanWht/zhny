package com.xl.ems.apigateway.service.ynservice;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

//用能单位产品结构信息接口
public interface YNCompanyProductService {

    /**
     *产品结构信息增加
     * @param requestMap
     * @return
     */
    JSONObject companyProductStructureAdd(Map<String, Object> requestMap);

    /**
     * 产品结构信息修改
     * @param requestMap
     * @return
     */
    JSONObject companyProductStructureUpdate(Map<String, Object> requestMap);

    /**
     * 产品结构信息删除
     * @param requestMap
     * @return
     */
    JSONObject companyProductStructureDelete(Map<String, Object> requestMap);
}
