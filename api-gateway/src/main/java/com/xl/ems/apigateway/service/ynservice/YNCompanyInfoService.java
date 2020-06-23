package com.xl.ems.apigateway.service.ynservice;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface YNCompanyInfoService {
    JSONObject companyContacterAdd(Map<String, Object> requestMap);

    JSONObject companyContacterUpdate(Map<String, Object> requestMap);

    JSONObject companyContacterDelete(Map<String, Object> requestMap);

    JSONObject companyEnergySysAdd(Map<String, Object> requestMap);

    JSONObject companyEnergySysUpdate(Map<String, Object> requestMap);

    JSONObject companyEnergySysDelete(Map<String, Object> requestMap);

    JSONObject companyEnergyManagerAdd(Map<String, Object> requestMap);

    JSONObject companyEnergyManagerUpdate(Map<String, Object> requestMap);

    JSONObject companyEnergyManagerDelete(Map<String, Object> requestMap);

    JSONObject companyEnergyMonitorAdd(Map<String, Object> requestMap);

    JSONObject companyEnergyMonitorUpdate(Map<String, Object> requestMap);

    JSONObject companyEnergyMonitorDelete(Map<String, Object> requestMap);

    JSONObject companyCalculaterAdd(Map<String, Object> requestMap);

    JSONObject companyCalculaterUpdate(Map<String, Object> requestMap);

    JSONObject companyCalculaterDelete(Map<String, Object> requestMap);

    JSONObject companyEnergyCertificationAdd(Map<String, Object> requestMap);

    JSONObject companyEnergyCertificationUpdate(Map<String, Object> requestMap);

    JSONObject companyEnergyCertificationDelete(Map<String, Object> requestMap);
}
