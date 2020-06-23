package com.xl.ems.apigateway.service.ynservice;


import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface YNEnterpriseService {
    JSONObject enterpriseInfoRWK(Map<String, String> requestMap);

    JSONObject dataDownloadPublic(Map<String, String> requestMap);

    JSONObject enterpriseInfoApply(String requestMap);

    JSONObject enterpriseInfoUpdate(JSONObject requestMap);

    JSONObject requestAK(Map<String, String> requestMap);

    JSONObject checkVersion(Map<String, String> requestMap);

    JSONObject platformAccessURL(Map<String, String> requestMap);

    JSONObject dataDownload(Map<String, String> requestMap);
}
