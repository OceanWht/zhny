package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.model.UnitConfigureModel;

import java.util.List;
import java.util.Map;

//用能单位工序单元信息接口
public interface YNUnitConfigureService {

    /**
     * 工序单元增加
     * @param requestMap
     * @return
     */
    JSONObject YNUnitConfigureAdd(Map<String, String> requestMap);

    /**
     * 工序单元修改
     * @param requestMap
     * @return
     */
    JSONObject YNUnitConfigureUpdate(Map<String, String> requestMap);

    /**
     * 工序单元删除
     * @param requestMap
     * @return
     */
    JSONObject YNUnitConfigureDelete(Map<String, String> requestMap);

    List<UnitConfigureModel> list(String code);
}
