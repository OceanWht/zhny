package com.xl.ems.apigateway.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.EmsCompanyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 发送请求至8008
 */
@Service
public class ApiService {


    @Autowired
    GenericRest genericRest;

    public boolean savePass(String userid, String opwd, String token, String npwd,String url) {
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> body  = new HashMap<String,String>();
        body.put("userid",userid);
        body.put("opwd",opwd);
        body.put("npwd",npwd);

        HttpEntity httpEntity = new HttpEntity(body,headers);
        //设置8002后台 账户密码
        url = "direct://"+url;
        ResponseEntity<String> responseEntity =  genericRest.post(url + "Account/SetAccountPwd", httpEntity, new ParameterizedTypeReference<String>() {
        });

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            return true;
        }

        return false;
    }

    private HttpHeaders getHttpHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        headers.add("token",token);
        return headers;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    public String login(String name, String pass,String url) {
        HttpHeaders headers = getHttpHeaders();
        //设置8002后台 账户密码
        url = "direct://"+url;
        Map<String,String> body  = new HashMap<String,String>();
        body.put("name",name);
        body.put("pwd",pass);
        body.put("client_id","dny_power");
        HttpEntity httpEntity = new HttpEntity(body,headers);
        ResponseEntity<String> responseEntity =  genericRest.post(url + "Account/Login", httpEntity, new ParameterizedTypeReference<String>() {
        });

        return responseEntity.getBody();
    }

    public List<EmsCompanyModel> getPlatFormCompany(String pid, String url) {
        HttpHeaders httpHeaders = getHttpHeaders();
        //设置8002后台 账户密码
        url = "direct://"+url;
        Map<String,String> body  = new HashMap<String,String>();
        body.put("pid",pid);
        HttpEntity httpEntity = new HttpEntity(body,httpHeaders);
        List<EmsCompanyModel> emsCompanyModels = null;
        ResponseEntity<String> responseEntity = genericRest.post(url + "Archive/GetPlatFormCompany",
                httpEntity, new ParameterizedTypeReference<String>() {});

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            JSONArray bodyArray = resJson.getJSONArray("body");
            if (bodyArray != null){
                emsCompanyModels = bodyArray.toJavaList(EmsCompanyModel.class);
            }

        }

        return emsCompanyModels;
    }
}
