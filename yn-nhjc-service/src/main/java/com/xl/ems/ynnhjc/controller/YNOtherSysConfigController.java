package com.xl.ems.ynnhjc.controller;

import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.common.RestCode;
import com.xl.ems.ynnhjc.common.RestResponse;
import com.xl.ems.ynnhjc.mapper.UidRelationshipModelMapper;
import com.xl.ems.ynnhjc.model.SysWorkingCodeModel;
import com.xl.ems.ynnhjc.model.UidRelationshipModel;
import com.xl.ems.ynnhjc.service.YNOtherSysConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName 系统配置
 * @Description TODO
 * @Author wht
 * @Date 13:54
 **/
@Controller
@RequestMapping("/otherSysConfig")
public class YNOtherSysConfigController {

    @Autowired
    YNOtherSysConfigService ynOtherSysConfigService;

    private static final Logger LOGGER = LoggerFactory.getLogger(YNOtherSysConfigController.class);


    @RequestMapping(value = "/getUidRelList",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UidRelationshipModel>> getUidRelList(){
        LOGGER.info("YNOtherSysConfigController getUidRelList begin......");
        List<UidRelationshipModel> uidRelationshipModels = ynOtherSysConfigService.getUidRelList();
        LOGGER.info("YNOtherSysConfigController getUidRelList end......");
        return RestResponse.success(uidRelationshipModels);
    }

    @RequestMapping(value = "/addUidRel",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> addUidRel(@RequestBody JSONObject request){
        LOGGER.info("YNOtherSysConfigController addUidRel begin......");
        if (request.size() == 0){
            LOGGER.error("YNOtherSysConfigController addUidRel request has error......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynOtherSysConfigService.addUidRel(request);
        LOGGER.info("YNOtherSysConfigController addUidRel end......");
        return res;
    }

    @RequestMapping(value = "/updateUidRel",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> updateUidRel(@RequestBody JSONObject request){
        LOGGER.info("YNOtherSysConfigController updateUidRel begin......");
        if (request.size() == 0){
            LOGGER.error("YNOtherSysConfigController updateUidRel request has error......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynOtherSysConfigService.updateUidRel(request);
        LOGGER.info("YNOtherSysConfigController updateUidRel end......");
        return res;
    }

    @RequestMapping(value = "/deleteUidRel",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> deleteUidRel(@RequestBody JSONObject request){
        LOGGER.info("YNOtherSysConfigController deleteUidRel begin......");
        if (request.size() == 0){
            LOGGER.error("YNOtherSysConfigController deleteUidRel request has error......");
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        RestResponse<JSONObject> res = ynOtherSysConfigService.deleteUidRel(request);
        LOGGER.info("YNOtherSysConfigController deleteUidRel end......");
        return res;
    }


    /**
     * 获取,新增workingcode
     */
    @RequestMapping(value = "/getWorkingCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<SysWorkingCodeModel>> getWorkingCode(){
        List<SysWorkingCodeModel> sysWorkingCodeModels = ynOtherSysConfigService.getWorkingCode();
        return RestResponse.success(sysWorkingCodeModels);
    }

    @RequestMapping(value = "/addWorkingCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> addWorkingCode(@RequestBody JSONObject request){
        RestResponse<JSONObject> res = ynOtherSysConfigService.addWorkingCode(request);
        return res;
    }

    @RequestMapping(value = "/updateWorkingCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> updateWorkingCode(@RequestBody JSONObject request){
        RestResponse<JSONObject> res = ynOtherSysConfigService.updateWorkingCode(request);
        return res;
    }

    @RequestMapping(value = "/deleteWorkingCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> deleteWorkingCode(@RequestBody JSONObject request){
        RestResponse<JSONObject> res = ynOtherSysConfigService.deleteWorkingCode(request);
        return res;
    }

    @RequestMapping(value = "/rwk",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<JSONObject> rwk(){
        RestResponse<JSONObject> res = ynOtherSysConfigService.rwk();
        return res;
    }


}
