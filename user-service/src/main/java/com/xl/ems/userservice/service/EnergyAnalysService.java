package com.xl.ems.userservice.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface EnergyAnalysService {
    JSONObject unitTreeTWO(Map<String, String> requestMap);

    JSONObject getAnalogDayData(Map<String, String> requestMap);

    JSONObject getUnitDayData(Map<String, String> requestMap);

    JSONObject getAnalogMonthData(Map<String, String> requestMap);
}
