package com.xl.ems.userservice.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.SurfaceUrlModelMapper;
import com.xl.ems.userservice.mapper.UserInfoModelMapper;
import com.xl.ems.userservice.model.SurfaceUrlModel;
import com.xl.ems.userservice.model.UserInfoModel;
import com.xl.ems.userservice.utils.GetUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    UserInfoModelMapper userInfoModelMapper;

    @Autowired
    GetUrlUtils getUrlUtils;

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
        if (userInfoModel == null){
            return false;
        }

        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)){
            return false;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)){
            return false;
        }

        Map<String,Object> reqBody = new HashMap<String, Object>();
        reqBody.put("userInfoModel",userInfoModel);
        reqBody.put("npwd",npwd);
        reqBody.put("url",softUrl);
        ResponseEntity<RestResponse<String>>  responseEntity = genericRest.post(apiUrl+"/savePass", reqBody, new ParameterizedTypeReference<RestResponse<String>>() {});
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)){
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

    public JSONArray login(final String name, final String pass) {
        if (Strings.isNullOrEmpty(name) || Strings.isNullOrEmpty(pass)){
            return null;
        }

        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)){
            return null;
        }
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)){
            return null;
        }

        Map<String,Object> reqBody = new HashMap<String, Object>();
        reqBody.put("name",name);
        reqBody.put("pass",pass);
        reqBody.put("url",softUrl);
        ResponseEntity<RestResponse<String>>  responseEntity = genericRest.post(apiUrl+"/login", reqBody, new ParameterizedTypeReference<RestResponse<String>>() {});
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return null;
        }
        String res = responseEntity.getBody().getResult();
        JSONObject resJson = JSONObject.parseObject(res);
        JSONArray body = null;
        if (resJson.getJSONObject("header").getInteger("status").equals(0)){
            //登录成功之后，先判断数据库中有没有该用户，没有则新增，有就更新一下token
            body =  resJson.getJSONArray("body");
           final JSONObject object = (JSONObject) body.get(0);

           //开启一个线程处理数据库userinfo
            new Thread(){
                @Override
                public synchronized void run() {
                    UserInfoModel record = new UserInfoModel();
                    record.setName(name);
                    record.setPass(pass);
                    UserInfoModel userInfoModel = userInfoModelMapper.getUserInfoByNP(record);
                    if (userInfoModel == null){
                        //新增
                        record.setToken(object.getString("token"));
                        record.setUid(object.getString("uid"));
                        record.setUserid(object.getString("userid"));
                        userInfoModelMapper.insertSelective(record);
                    }else {
                        //更新token
                        userInfoModel.setToken(object.getString("token"));
                        userInfoModelMapper.updateByPrimaryKey(userInfoModel);
                    }
                }
            }.start();

        }
        return body;
    }

}
