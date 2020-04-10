package com.xl.ems.userservice.controller;

import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Strings;
import com.mysql.cj.xdevapi.JsonArray;
import com.xl.ems.userservice.common.RestCode;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.model.UserInfoModel;
import com.xl.ems.userservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/user/login")
    @ResponseBody
    public RestResponse<JSONArray> login(@RequestBody Map<String, String> request) {

        String pass = request.get("pass");
        String name = request.get("name");
        if (Strings.isNullOrEmpty(pass) || Strings.isNullOrEmpty(name)) {
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        //发送请求至api-gateway，验证用户名和密码
        JSONArray res = loginService.login(name, pass);
        if (res == null) {
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        return RestResponse.success(res);

    }

    @RequestMapping(value = "/user/loginValidate", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> loginValidate(@RequestBody Map<String, String> request) {

        String name = request.get("name");
        String pass = request.get("pass");
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(pass)) {
            return RestResponse.error(RestCode.LACK_PARAMS);
        }

        //先从库中查找，找到后再校验密码规则
        UserInfoModel userInfoModel = loginService.getUserInfoByNP(name, pass);

        if (userInfoModel == null) {
            return RestResponse.error(RestCode.USER_NOT_EXIST);
        }

        //存在该用户，但密码格式不对，提示修改密码,校验失败，提示修改密码
        if (!loginService.passValidate(pass)) {
            return RestResponse.error(RestCode.WRONG_PASS);
        }

        return RestResponse.success("校验成功");
    }

    @RequestMapping(value = "/user/passSave", method = RequestMethod.POST)
    @ResponseBody
    public RestResponse<String> passSave(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String pass = request.get("pass");
        String npwd = request.get("npwd");
        String npwdconfirm = request.get("npwdconfirm");

        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(pass) || Strings.isNullOrEmpty(npwd) || Strings.isNullOrEmpty(npwdconfirm)) {
            return RestResponse.error(RestCode.LACK_PARAMS);
        }
        //新密码与确认密码
        if (!npwd.equals(npwdconfirm)) {
            return RestResponse.error(RestCode.WRONG_PASSCONFIRM);
        }
        //密码规范
        if (!loginService.passValidate(npwd)) {
            return RestResponse.error(RestCode.WRONG_PASS);
        }

        //修改密码
        if (!loginService.savePass(name, npwd)) {
            //修改失败
            return RestResponse.error(RestCode.WRONG_SAVEPASS);
        }
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setName(name);
        userInfoModel.setPass(npwd);
        //8002设置密码成功后，更新本地数据库密码
        if (!loginService.updateUserInfo(userInfoModel)) {
            //修改失败
            return RestResponse.error(RestCode.WRONG_SAVEPASS);
        }


        return RestResponse.success("修改密码成功");
    }
}
