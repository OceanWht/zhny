package com.xl.ems.apigateway.service.ynservice;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

//用能单位产值、增加值、销售收入信息接口
public interface YNCompanyOutputValueService {

    /**
     *销售收入信息增加
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyOutputValueAdd(Map<String, Object> requestMap);

    /**
     *销售收入信息修改
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyOutputValueUpdate(Map<String, Object> requestMap);

    /**
     *销售收入信息删除
     * @param requestMap
     * @return
     */
    JSONObject YNCompanyOutputValueDelete(Map<String, Object> requestMap);
}
