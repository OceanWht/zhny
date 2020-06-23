package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.model.SysApplicationModelWithBLOBs;
import com.xl.ems.ynnhjc.service.YNEnterpriseServcie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/enterpriseInfo")
public class YNEnterpriseController {

    @Autowired
    YNEnterpriseServcie ynEnterpriseServcie;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> login(@RequestBody JSONObject requestJson) {
        LOGGER.info("YNEnterpriseController login begin......");
        if (requestJson == null) {
            LOGGER.error("YNEnterpriseController login requestJson is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynEnterpriseServcie.login(requestJson);

        LOGGER.info("YNEnterpriseController login end......");
        return res;
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> apply(@RequestBody JSONObject requestJson) {
        LOGGER.info("YNEnterpriseController apply begin......");
        if (requestJson == null) {
            LOGGER.error("YNEnterpriseController apply sysApplicationModelWithBLOBs is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynEnterpriseServcie.apply(requestJson);

        LOGGER.info("YNEnterpriseController apply end......");
        return res;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> update(@RequestBody JSONObject requestJson) {
        LOGGER.info("YNEnterpriseController update begin......");
        /*SysApplicationModelWithBLOBs sysApplicationModelWithBLOBs = requestJson.getJSONObject("data")
                .toJavaObject(SysApplicationModelWithBLOBs.class);*/
        if (requestJson == null) {
            LOGGER.error("YNEnterpriseController update sysApplicationModelWithBLOBs is null......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynEnterpriseServcie.update(requestJson);

        LOGGER.info("YNEnterpriseController update end......");
        return res;
    }

    @RequestMapping(value = "/getBasicData", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> getBasicData(@RequestBody JSONObject request) {
        LOGGER.info("YNEnterpriseController getBasicData begin......");
        String code = request.getString("code");
        JSONObject res = ynEnterpriseServcie.getBasicData(code);
        if (res == null) {
            LOGGER.error("YNEnterpriseController getBasicData res is null......");
            return RestResponse.error(RestCode.UNKNOWN_ERROR);
        }
        LOGGER.info("YNEnterpriseController getBasicData end......");
        return RestResponse.success(res);
    }

}
