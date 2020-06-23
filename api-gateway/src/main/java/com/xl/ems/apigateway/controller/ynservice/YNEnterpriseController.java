package com.xl.ems.apigateway.controller.ynservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.model.SysApplicationModelWithBLOBs;
import com.xl.ems.apigateway.service.ynservice.YNEnterpriseService;
import com.xl.ems.apigateway.sm4.PIN;
import com.xl.ems.apigateway.sm4.SMS4Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class YNEnterpriseController {

    @Autowired
    YNEnterpriseService ynEnterpriseService;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNEnterpriseController.class);

    /**
     * 端系统申请工作密钥
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/rwk", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> enterpriseInfoRWK(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController enterpriseInfoRWK begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController enterpriseInfoRWK requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.enterpriseInfoRWK(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController enterpriseInfoRWK end...");
            return RestResponse.success(result);
        }
    }


    /**
     * 端系统注册基础数据下载
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/dataDownload/public", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> dataDownloadPublic(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController dataDownloadPublic begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController dataDownloadPublic requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.dataDownloadPublic(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController dataDownloadPublic end...");
            return RestResponse.success(result);
        }

    }


    /**
     * 端系统注册申请
     *
     * @param req 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/enterpriseInfo/apply", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> enterpriseInfoApply(@RequestBody String req, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController enterpriseInfoApply begin...");

        if (Strings.isNullOrEmpty(req)) {
            LOGGER.error("YNEnterpriseController enterpriseInfoApply requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynEnterpriseService.enterpriseInfoApply(req);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController enterpriseInfoApply end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 端系统注册申请
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/enterpriseInfo/update", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> enterpriseInfoUpdate(@RequestBody JSONObject requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController enterpriseInfoUpdate begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController enterpriseInfoUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.enterpriseInfoUpdate(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController enterpriseInfoUpdate end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 端系统获取 AK
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/requestAK", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> requestAK(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController enterpriseInfoRequestAK begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController enterpriseInfoRequestAK requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.requestAK(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController enterpriseInfoRequestAK end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 平台版本校验
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/checkVersion", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> checkVersion(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController checkVersion begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController checkVersion requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.checkVersion(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController checkVersion end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 平台基础配置地址查询
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/platformAccessURL", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> platformAccessURL(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController platformAccessURL begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController platformAccessURL requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.platformAccessURL(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController platformAccessURL end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 平台基础数据下载
     *
     * @param requestMap 请求map
     * @param request    http请求消息头
     * @return
     */
    @RequestMapping(value = "/dataDownload", method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> dataDownload(@RequestBody Map<String, String> requestMap, HttpServletRequest request) {
        LOGGER.info("YNEnterpriseController dataDownload begin...");

        if (requestMap == null || requestMap.size() == 0) {
            LOGGER.error("YNEnterpriseController dataDownload requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code, RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynEnterpriseService.dataDownload(requestMap);
        if (result == null) {
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code, RestCode.UNKNOWN_ERROR.msg);
        } else {
            LOGGER.info("YNEnterpriseController dataDownload end...");
            return RestResponse.success(result);
        }

    }
}
