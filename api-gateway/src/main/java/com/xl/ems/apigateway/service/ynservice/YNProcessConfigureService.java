package com.xl.ems.apigateway.service.ynservice;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

//用能单位生产工序信息接口
public interface YNProcessConfigureService {

    /**
     * 生产工序增加
     * @param requestMap
     * @return
     */
    JSONObject YNProcessConfigureAdd(Map<String, String> requestMap);

    /**
     * 生产工序修改
     * @param requestMap
     * @return
     */
    JSONObject YNProcessConfigureUpdate(Map<String, String> requestMap);

    /**
     * 生产工序删除
     * @param requestMap
     * @return
     */
    JSONObject YNProcessConfigureDelete(Map<String, String> requestMap);
}
