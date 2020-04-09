package com.xl.ems.userservice.service;


import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.SurfaceUrlModelMapper;
import com.xl.ems.userservice.mapper.UserInfoModelMapper;
import com.xl.ems.userservice.model.SurfaceUrlModel;
import com.xl.ems.userservice.model.UserInfoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    SurfaceUrlModelMapper surfaceUrlModelMapper;

    @Value("${user.pass.patten}")
    private String patten;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;

    @Autowired
    GenericRest genericRest;

    public UserInfoModel getUserInfoByNP(String name, String pass) {
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setName(name);
        userInfoModel.setPass(pass);
        UserInfoModel result = userInfoModelMapper.getUserInfoByNP(userInfoModel);
        return result;
    }

    public boolean passValidate(String pass) {
        if (pass.matches(patten)){
            return true;
        }

        return false;
    }

    public boolean savePass(String name, String npwd) {

        UserInfoModel record = new UserInfoModel();
        record.setName(name);
        UserInfoModel userInfoModel = userInfoModelMapper.getUserInfoByNP(record);

        //调用接口，修改密码
        //api接口服务
        SurfaceUrlModel surfaceUrlModel = surfaceUrlModelMapper.selectByPrimaryKey(apiUrlType);
        if (surfaceUrlModel == null){
            return  false;
        }
        String apiUrl = surfaceUrlModel.getUrl();
        //软件公司接口
        surfaceUrlModel = surfaceUrlModelMapper.selectByPrimaryKey(softUrlType);
        if (surfaceUrlModel == null){
            return  false;
        }

        Map<String,Object> reqBody = new HashMap<String, Object>();
        reqBody.put("userInfoModel",userInfoModel);
        reqBody.put("npwd",npwd);
        reqBody.put("url",surfaceUrlModel.getUrl());
        ResponseEntity<RestResponse<String>>  responseEntity = genericRest.post(apiUrl+"/savePass", reqBody, new ParameterizedTypeReference<RestResponse<String>>() {});
        if (responseEntity == null){
            return false;
        }

        return true;
    }

    public boolean updateUserInfo(UserInfoModel userInfoModel) {
       int result =  userInfoModelMapper.updateUserInfo(userInfoModel);
       if (result == 0){
           return false;
       }

       return true;
    }
}
