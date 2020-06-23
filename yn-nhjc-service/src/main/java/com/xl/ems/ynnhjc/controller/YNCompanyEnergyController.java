package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.config.GenericRest;
import com.xl.ems.ynnhjc.model.*;
import com.xl.ems.ynnhjc.service.YNCompanyEnergyService;
import com.xl.ems.ynnhjc.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/CompanyEnergy")
public class YNCompanyEnergyController {

    @Autowired
    YNCompanyEnergyService ynCompanyEnergyService;


    private static final Logger LOGGER = LoggerFactory.getLogger(YNCompanyEnergyController.class);

    @RequestMapping(value = "/sysEnergyAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> sysEnergyAdd(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController sysEnergyAdd begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController sysEnergyAdd request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        SysEnergyModel energyModel = request.getJSONObject("data").toJavaObject(SysEnergyModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.sysEnergyAdd(energyModel);
        LOGGER.info("YNCompanyEnergyController sysEnergyAdd end......");
        return res;
    }

    @RequestMapping(value = "/energyManagerAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyManagerAdd(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyManagerAdd begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyManagerAdd request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyManagerModel energyModel = request.getJSONObject("data").toJavaObject(EnergyManagerModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyManagerAdd(energyModel);
        LOGGER.info("YNCompanyEnergyController sysEnergyAdd end......");
        return res;
    }


    @RequestMapping(value = "/sysEnergyUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> sysEnergyUpdate(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController sysEnergyUpdate begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController sysEnergyUpdate request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        SysEnergyModel energyModel = request.getJSONObject("data").toJavaObject(SysEnergyModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.sysEnergyUpdate(energyModel);
        LOGGER.info("YNCompanyEnergyController sysEnergyUpdate end......");
        return res;
    }

    @RequestMapping(value = "/energyManagerUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyManagerUpdate(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyManagerUpdate begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyManagerUpdate request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyManagerModel energyModel = request.getJSONObject("data").toJavaObject(EnergyManagerModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyManagerUpdate(energyModel);
        LOGGER.info("YNCompanyEnergyController energyManagerUpdate end......");
        return res;
    }

    @RequestMapping(value = "/sysEnergyDelete", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> sysEnergyDelete(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController sysEnergyDelete begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController sysEnergyDelete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        SysEnergyModel energyModel = request.getJSONObject("data").toJavaObject(SysEnergyModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.sysEnergyDelete(energyModel);
        LOGGER.info("YNCompanyEnergyController sysEnergyDelete end......");
        return res;
    }

    @RequestMapping(value = "/energyManagerDelete", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyManagerDelete(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyManagerDelete begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyManagerDelete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyManagerModel energyModel = request.getJSONObject("data").toJavaObject(EnergyManagerModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyManagerDelete(energyModel);
        LOGGER.info("YNCompanyEnergyController energyManagerDelete end......");
        return res;
    }

    @RequestMapping(value = "/sysEnergyList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<SysEnergyModel>> sysEnergyList(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController sysEnergyList begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController sysEnergyList request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = request.getString("enterpriseCode");
        RestResponse<List<SysEnergyModel>> res = ynCompanyEnergyService.sysEnergyList(enterpriseCode);
        LOGGER.info("YNCompanyEnergyController sysEnergyList end......");
        return res;
    }


    @RequestMapping(value = "/energyManagerList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EnergyManagerModel>> energyManagerList(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyManagerList begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyManagerList request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = request.getString("enterpriseCode");
        RestResponse<List<EnergyManagerModel>> res = ynCompanyEnergyService.energyManagerList(enterpriseCode);
        LOGGER.info("YNCompanyEnergyController energyManagerList end......");
        return res;
    }


    @RequestMapping(value = "/energyMonitorAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyMonitorAdd(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyMonitorAdd begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyMonitorAdd request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyMonitorModel energyModel = request.getJSONObject("data").toJavaObject(EnergyMonitorModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyMonitorAdd(energyModel);
        LOGGER.info("YNCompanyEnergyController energyMonitorAdd end......");
        return res;
    }


    @RequestMapping(value = "/energyMonitorUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyMonitorUpdate(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyMonitorUpdate begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyMonitorUpdate request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyMonitorModel energyModel = request.getJSONObject("data").toJavaObject(EnergyMonitorModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyMonitorUpdate(energyModel);
        LOGGER.info("YNCompanyEnergyController energyMonitorUpdate end......");
        return res;
    }

    @RequestMapping(value = "/energyMonitorDelete", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyMonitorDelete(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyMonitorDelete begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyMonitorDelete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyMonitorModel energyModel = request.getJSONObject("data").toJavaObject(EnergyMonitorModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyMonitorDelete(energyModel);
        LOGGER.info("YNCompanyEnergyController energyMonitorDelete end......");
        return res;
    }

    @RequestMapping(value = "/energyMonitorList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EnergyMonitorModel>> energyMonitorList(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyMonitorList begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyMonitorList request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = request.getString("enterpriseCode");
        RestResponse<List<EnergyMonitorModel>> res = ynCompanyEnergyService.energyMonitorList(enterpriseCode);
        LOGGER.info("YNCompanyEnergyController energyMonitorList end......");
        return res;
    }

    @RequestMapping(value = "/calculaterAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> calculaterAdd(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController calculaterAdd begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController calculaterAdd request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        CalculaterModel energyModel = request.getJSONObject("data").toJavaObject(CalculaterModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.calculaterAdd(energyModel);
        LOGGER.info("YNCompanyEnergyController calculaterAdd end......");
        return res;
    }


    @RequestMapping(value = "/calculaterUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> calculaterUpdate(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController calculaterUpdate begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController calculaterUpdate request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        CalculaterModel energyModel = request.getJSONObject("data").toJavaObject(CalculaterModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.calculaterUpdate(energyModel);
        LOGGER.info("YNCompanyEnergyController calculaterUpdate end......");
        return res;
    }

    @RequestMapping(value = "/calculaterDelete", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> calculaterDelete(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController calculaterDelete begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController calculaterDelete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        CalculaterModel energyModel = request.getJSONObject("data").toJavaObject(CalculaterModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.calculaterDelete(energyModel);
        LOGGER.info("YNCompanyEnergyController calculaterDelete end......");
        return res;
    }

    @RequestMapping(value = "/calculaterList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<CalculaterModel>> calculaterList(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController calculaterList begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController calculaterList request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = request.getString("enterpriseCode");
        RestResponse<List<CalculaterModel>> res = ynCompanyEnergyService.calculaterList(enterpriseCode);
        LOGGER.info("YNCompanyEnergyController calculaterList end......");
        return res;
    }


    @RequestMapping(value = "/energyCertificationAdd", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyCertificationAdd(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyCertificationAdd begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyCertificationAdd request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyCertificationModel energyModel = request.getJSONObject("data").toJavaObject(EnergyCertificationModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyCertificationAdd(energyModel);
        LOGGER.info("YNCompanyEnergyController energyCertificationAdd end......");
        return res;
    }


    @RequestMapping(value = "/energyCertificationUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyCertificationUpdate(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyCertificationUpdate begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyCertificationUpdate request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyCertificationModel energyModel = request.getJSONObject("data").toJavaObject(EnergyCertificationModel.class);

        RestResponse<JSONObject> res = ynCompanyEnergyService.energyCertificationUpdate(energyModel);
        LOGGER.info("YNCompanyEnergyController energyCertificationUpdate end......");
        return res;
    }

    @RequestMapping(value = "/energyCertificationDelete", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> energyCertificationDelete(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyCertificationDelete begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyCertificationDelete request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        EnergyCertificationModel energyModel = request.getJSONObject("data").toJavaObject(EnergyCertificationModel.class);
        RestResponse<JSONObject> res = ynCompanyEnergyService.energyCertificationDelete(energyModel);
        LOGGER.info("YNCompanyEnergyController energyCertificationDelete end......");
        return res;
    }

    @RequestMapping(value = "/energyCertificationList", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EnergyCertificationModel>> energyCertificationList(@RequestBody JSONObject request) {
        LOGGER.info("YNCompanyEnergyController energyCertificationList begin......");
        if (request == null) {
            LOGGER.error("YNCompanyEnergyController energyCertificationList request is null");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        String enterpriseCode = request.getString("enterpriseCode");
        RestResponse<List<EnergyCertificationModel>> res = ynCompanyEnergyService.energyCertificationList(enterpriseCode);
        LOGGER.info("YNCompanyEnergyController energyCertificationList end......");
        return res;
    }

}
