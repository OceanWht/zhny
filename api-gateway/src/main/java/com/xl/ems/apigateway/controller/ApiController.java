package com.xl.ems.apigateway.controller;


import com.google.common.base.Strings;
import com.xl.ems.apigateway.bean.*;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.model.*;
import com.xl.ems.apigateway.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

    @RequestMapping(value = "/savePass",method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public RestResponse<String> savePass(@RequestBody Map<String,Object> request){
        LinkedHashMap<String,Object> userInfoModel = (LinkedHashMap<String,Object>)request.get("userInfoModel");
        String npwd = (String)request.get("npwd");
        String url = (String)request.get("url");
        if (userInfoModel != null && !Strings.isNullOrEmpty(npwd)){
            String userid = (String) userInfoModel.get("userid");
            String opwd = (String)userInfoModel.get("pass");
            String token = (String)userInfoModel.get("token");
            //判空
            if (Strings.isNullOrEmpty(userid) || Strings.isNullOrEmpty(opwd) ||
                    Strings.isNullOrEmpty(token)||Strings.isNullOrEmpty(url)){
                return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
            }

            if (!apiService.savePass(userid,opwd,token,npwd,url)){
                return RestResponse.error(RestCode.UNKNOWN_ERROR.code,RestCode.UNKNOWN_ERROR.msg);
            }
        }

        return RestResponse.success();
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> login(@RequestBody Map<String,String> request){
        String name = request.get("name");
        String pass = request.get("pass");
        String url = request.get("url");
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(pass) || Strings.isNullOrEmpty(url)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        String res = apiService.login(name,pass,url);
        return RestResponse.success(res);
    }


    @RequestMapping(value = "/GetPlatFormCompany",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<EmsCompanyModel>> getPlatFormCompany(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String pid = request.get("pid");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(pid)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<EmsCompanyModel> emsCompanyModels= apiService.getPlatFormCompany(pid,url);

        return RestResponse.success(emsCompanyModels);
    }

    @RequestMapping(value = "/GetAccountFeePublic",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlAccountBean>> getAccountFeePublic(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String pid = request.get("pid");
        String uids = request.get("uids");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(pid) || Strings.isNullOrEmpty(uids)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlAccountBean> xlAccountBeans = apiService.getAccountFeePublic(pid,uids,url);
        if (CollectionUtils.isEmpty(xlAccountBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        return RestResponse.success(xlAccountBeans);

    }

    @RequestMapping(value = "/GetDataCode",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlDatacodeModel>> getDataCode(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String token = request.get("token");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(token)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlDatacodeModel> xlDatacodeModels = apiService.getDataCode(token,url);
        if (CollectionUtils.isEmpty(xlDatacodeModels)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(xlDatacodeModels);
    }

    @RequestMapping(value = "/GetUnitCalcGroup",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlUnitCalcGroupModel>> getUnitCalcGroup(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String token = request.get("token");
        String uids = request.get("uids");

        if (Strings.isNullOrEmpty(uids) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlUnitCalcGroupModel> xlUnitCalcGroupModels = apiService.getUnitCalcGroup(uids,token,url);
        if (CollectionUtils.isEmpty(xlUnitCalcGroupModels)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(xlUnitCalcGroupModels);
    }

    @RequestMapping(value = "/GetUnitLinkByDataType",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> getUnitLinkByDataType(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String dataids = request.get("dataids");
        String token = request.get("token");
        String uid = request.get("uid");
        if (Strings.isNullOrEmpty(dataids) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url)
        ||Strings.isNullOrEmpty(uid)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        String res = apiService.getUnitLinkByDataType(uid,token,dataids,url);
        if (Strings.isNullOrEmpty(res)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }
        return RestResponse.success(res);
    }

    @RequestMapping(value = "/GetGroupAnalog",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlGroupanalogModel>> getGroupAnalog(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String groupid = request.get("groupids");
        String token = request.get("token");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(groupid) || Strings.isNullOrEmpty(token)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlGroupanalogModel> groupanalogModels = apiService.getGroupAnalog(groupid,token,url);
        if (CollectionUtils.isEmpty(groupanalogModels)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(groupanalogModels);
    }


    @RequestMapping(value = "/GetDidByAnalogId",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlAidDidModel>> getDidByAnalogId(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String aids = request.get("aid");
        String token = request.get("token");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(aids) || Strings.isNullOrEmpty(token)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlAidDidModel> xlAidDidModels = apiService.getDidByAnalogId(aids,url,token);
        if (CollectionUtils.isEmpty(xlAidDidModels)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(xlAidDidModels);

    }


    @RequestMapping(value = "/GetAnalogByDid",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<XlAidDidModel>> getAnalogByDid(@RequestBody Map<String,String> request){
        String url = request.get("url");
        String dids = request.get("did");
        String token = request.get("token");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(dids) || Strings.isNullOrEmpty(token)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<XlAidDidModel> xlAidDidModels = apiService.getAnalogByDid(dids,url,token);
        if (CollectionUtils.isEmpty(xlAidDidModels)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(xlAidDidModels);
    }

    @RequestMapping(value = "/GetUnitGroupMonthData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupMounthDataBean>> getUnitGroupMonthData (@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        String url = request.get("url");
        String uid = request.get("uid");
        String sdt = request.get("sdt");
        String edt = request.get("edt");
        String token  = request.get("token");
        String dataid = request.get("dataid");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(sdt) ||
                Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(dataid)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = apiService.getUnitGroupMonthData(request);
        if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(unitGroupMounthDataBeans);
    }

    @RequestMapping(value = "/GetUnitGroupDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupMounthDataBean>> getEnergyDayData (@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        String url = request.get("url");
        String uid = request.get("uid");
        String sdt = request.get("sdt");
        String edt = request.get("edt");
        String token  = request.get("token");
        String dataid = request.get("dataid");
        String io = request.get("io");
        if (Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(sdt) ||
                Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(dataid)
                ||Strings.isNullOrEmpty(io)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<UnitGroupMounthDataBean> unitGroupMounthDataBeans = apiService.getUnitGroupDayData(request);
        if (CollectionUtils.isEmpty(unitGroupMounthDataBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(unitGroupMounthDataBeans);
    }

    @RequestMapping(value = "/GetAnalogDoc",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<AnalogDocBean>> getAnalogDoc(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<AnalogDocBean> analogDocBeans = apiService.getAnalogDoc(request);
        if (CollectionUtils.isEmpty(analogDocBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(analogDocBeans);
    }


    @RequestMapping(value = "/GetMeterArchive",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<MeterArchiveBean>> getMeterArchive(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<MeterArchiveBean> meterArchiveBeans = apiService.getMeterArchive(request);
        if (CollectionUtils.isEmpty(meterArchiveBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(meterArchiveBeans);
    }

    @RequestMapping(value = "/GetMeterAnalogTSCurveData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<AnalogTSCurveDataBean>> getMeterAnalogTSCurveData(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<AnalogTSCurveDataBean> analogTSCurveDataBeans = apiService.getMeterAnalogTSCurveData(request);
        if (CollectionUtils.isEmpty(analogTSCurveDataBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(analogTSCurveDataBeans);
    }

    @RequestMapping(value = "/GetAnalogDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<AnalogDataBean>> getAnalogDayData(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<AnalogDataBean> analogDataBeans = apiService.getAnalogDayData(request);
        if (CollectionUtils.isEmpty(analogDataBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(analogDataBeans);
    }

    @RequestMapping(value = "/GetAnalogMonthData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<AnalogDataBean>> getAnalogMonthData(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<AnalogDataBean> analogDataBeans = apiService.getAnalogMonthData(request);
        if (CollectionUtils.isEmpty(analogDataBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(analogDataBeans);
    }

    @RequestMapping(value = "/GetVagueUnit",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitInfoBean>> getVagueUnit(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<UnitInfoBean> unitInfoBeans = apiService.getVagueUnit(request);
        if (CollectionUtils.isEmpty(unitInfoBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(unitInfoBeans);
    }


    @RequestMapping(value = "/GetUnitAllCalcGroup",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<CalcGroupBean>> getUnitAllCalcGroup(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<CalcGroupBean> calcGroupBeans = apiService.getUnitAllCalcGroup(request);
        if (CollectionUtils.isEmpty(calcGroupBeans)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(calcGroupBeans);
    }

    @RequestMapping(value = "/GetUnitGroupCurveData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupCurveDataBean>> getUnitGroupCurveData(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<UnitGroupCurveDataBean> unitGroupCurveData = apiService.getUnitGroupCurveData(request);
        if (CollectionUtils.isEmpty(unitGroupCurveData)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(unitGroupCurveData);
    }



   /* @RequestMapping(value = "/GetUnitGroupDayData",method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<List<UnitGroupDayDataBean>> getUnitGroupDayData(@RequestBody Map<String,String> request){
        if (request == null){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        List<UnitGroupDayDataBean> unitGroupDayData = apiService.getUnitGroupDayData(request);
        if (CollectionUtils.isEmpty(unitGroupDayData)){
            return RestResponse.error(RestCode.WRONG_PARAMS.code,RestCode.WRONG_PARAMS.msg);
        }

        return RestResponse.success(unitGroupDayData);
    }*/
}
