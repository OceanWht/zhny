package com.xl.ems.apigateway.controller;


import com.google.common.base.Strings;
import com.xl.ems.apigateway.common.RestCode;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.model.EmsCompanyModel;
import com.xl.ems.apigateway.model.UserInfoModel;
import com.xl.ems.apigateway.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
