package com.xl.ems.apigateway.controller.ynservice;


import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.model.*;
import com.xl.ems.apigateway.service.ynservice.YNCompanyInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Map;

/**
 * 用能单位相关接口
 */
@RestController
public class YNCompanyInfoController {


    @Autowired
    YNCompanyInfoService ynCompanyInfoService;


    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyInfoController.class);

    /**
     * 用能单位联系人信息新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyContacter/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyContacterAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyContacterAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyContacterAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynCompanyInfoService.companyContacterAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyContacterAdd end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位联系人信息修改
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyContacter/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyContacterUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyContacterAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyContacterUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynCompanyInfoService.companyContacterUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyContacterUpdate end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位联系人信息修改
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyContacter/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyContacterDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyContacterAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyContacterDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynCompanyInfoService.companyContacterDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyContacterDelete end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergySys/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergySysAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergySysAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergySysAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynCompanyInfoService.companyEnergySysAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergySysAdd end...");
            return RestResponse.success(result);
        }

    }


    /**
     * 用能单位能源管理体系信息 更新
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergySys/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergySysUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergySysUpdate begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergySysUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        JSONObject result = ynCompanyInfoService.companyEnergySysUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergySysUpdate end...");
            return RestResponse.success(result);
        }

    }


    /**
     * 用能单位能源管理体系信息 删除
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergySys/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergySysDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergySysUpdate begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergySysDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        JSONObject result = ynCompanyInfoService.companyEnergySysDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergySysDelete end...");
            return RestResponse.success(result);
        }

    }



    /**
     * 用能单位能源管理体系信息 新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyManager/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyManagerAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyManagerAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyManagerAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyEnergyManagerAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyManagerAdd end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 更新
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyManager/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyManagerUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyManagerAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyManagerAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyEnergyManagerUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyManagerAdd end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 删除
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyManager/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyManagerDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyManagerDelete begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyManagerDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynCompanyInfoService.companyEnergyManagerDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyManagerDelete end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyMonitor/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyMonitorAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyMonitorAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyMonitorAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyEnergyMonitorAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyMonitorAdd end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 更新
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyMonitor/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyMonitorUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyMonitorUpdate begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyMonitorUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyEnergyMonitorUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyMonitorUpdate end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 删除
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyMonitor/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyMonitorDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyMonitorDelete begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyMonitorDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynCompanyInfoService.companyEnergyMonitorDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyMonitorDelete end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyCalculater/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyCalculaterAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyCalculaterAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyCalculaterAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyCalculaterAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyCalculaterAdd end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 更新
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyCalculater/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyCalculaterUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyCalculaterUpdate begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyCalculaterUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyCalculaterUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyCalculaterUpdate end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 删除
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyCalculater/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyCalculaterDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyCalculaterDelete begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyCalculaterDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyCalculaterDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyCalculaterDelete end...");
            return RestResponse.success(result);
        }

    }


    /**
     * 用能单位能源管理体系信息 新增
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyCertification/add",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyCertificationAdd(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyCertificationAdd begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyCertificationAdd requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynCompanyInfoService.companyEnergyCertificationAdd(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyCertificationAdd end...");
            return RestResponse.success(result);
        }

    }


    /**
     * 用能单位能源管理体系信息 更新
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyCertification/update",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyCertificationUpdate(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyCertificationUpdate begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyCertificationUpdate requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }



        JSONObject result = ynCompanyInfoService.companyEnergyCertificationUpdate(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyCertificationUpdate end...");
            return RestResponse.success(result);
        }

    }

    /**
     * 用能单位能源管理体系信息 删除
     * @param requestMap 请求map
     * @param request http请求消息头
     * @return
     */
    @RequestMapping(value = "/companyEnergyCertification/delete",method = RequestMethod.POST)
    @ResponseBody
    RestResponse<JSONObject> companyEnergyCertificationDelete(@RequestBody Map<String,Object> requestMap, HttpServletRequest request){
        LOGGER.info("YNEnterpriseController companyEnergyCertificationDelete begin...");

        if (requestMap == null || requestMap.size() == 0){
            LOGGER.error("YNEnterpriseController companyEnergyCertificationDelete requestMap error");
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }


        JSONObject result = ynCompanyInfoService.companyEnergyCertificationDelete(requestMap);
        if (result == null){
            return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
        }else {
            LOGGER.info("YNEnterpriseController companyEnergyCertificationDelete end...");
            return RestResponse.success(result);
        }

    }

}
