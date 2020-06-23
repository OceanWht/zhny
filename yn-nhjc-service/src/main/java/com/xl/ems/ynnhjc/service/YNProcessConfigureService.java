package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.model.ProcessConfigureModel;

import java.util.List;
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

    List<ProcessConfigureModel> list(String code);
}
