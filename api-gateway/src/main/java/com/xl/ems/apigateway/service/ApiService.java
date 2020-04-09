package com.xl.ems.apigateway.service;

import com.xl.ems.apigateway.common.RestResponse;
import com.xl.ems.apigateway.config.GenericRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送请求至8008
 */
@Service
public class ApiService {


    @Autowired
    GenericRest genericRest;

    public boolean savePass(String userid, String opwd, String token, String npwd,String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Accept",MediaType.APPLICATION_JSON.toString());
        headers.add("token",token);

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
}
