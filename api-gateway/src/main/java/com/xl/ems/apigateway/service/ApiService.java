package com.xl.ems.apigateway.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.apigateway.bean.*;
import com.xl.ems.apigateway.config.GenericRest;
import com.xl.ems.apigateway.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    public List<XlAccountBean> getAccountFeePublic(String pid, String uids, String url) {
        //设置URL
        url = "direct://"+url;
        //设置请求头
        HttpHeaders headers = getHttpHeaders();
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("pid",pid);
        requestBody.put("uid",uids);
        HttpEntity entity = new HttpEntity(requestBody,headers);
        List<XlAccountBean> xlAccountBeans = null;
        //发送请求
        ResponseEntity<String> responseEntity = genericRest.post(url + "Account/GetAccountFeePublic",
                entity, new ParameterizedTypeReference<String>() {});

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            JSONArray bodyArray = resJson.getJSONArray("body");
            if (bodyArray != null){
                xlAccountBeans = bodyArray.toJavaList(XlAccountBean.class);
            }
        }

        return xlAccountBeans;

    }

    public List<XlDatacodeModel> getDataCode(String token, String url) {
        //设置URL
        url = "direct://"+url;

        HttpHeaders headers = getHttpHeaders(token);

        HttpEntity entity = new HttpEntity(headers);
        //发送请求
        ResponseEntity<String> responseEntity = genericRest.post(url + "Archive/GetDataCode",
                entity, new ParameterizedTypeReference<String>() {});
        List<XlDatacodeModel> xlDatacodeModels = null;
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            JSONArray bodyArray = resJson.getJSONArray("body");
            if (bodyArray != null){
                xlDatacodeModels = bodyArray.toJavaList(XlDatacodeModel.class);
            }
        }

        return xlDatacodeModels;

    }

    public List<XlUnitCalcGroupModel> getUnitCalcGroup(String uids, String token, String url) {
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("uid",uids);
        requestBody.put("ioo","0");
        requestBody.put("isall","1");

        HttpHeaders headers = getHttpHeaders(token);

        HttpEntity httpEntity = new HttpEntity(requestBody,headers);

        url = "direct://"+url;
        ResponseEntity<String> entity = genericRest.post(url+"/Archive/GetUnitCalcGroup", httpEntity, new ParameterizedTypeReference<String>() {});
        List<XlUnitCalcGroupModel> xlUnitCalcGroupModels = null;
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                JSONArray body = obj.getJSONArray("body");
                if (body != null){
                    xlUnitCalcGroupModels = body.toJavaList(XlUnitCalcGroupModel.class);
                }
            }

        }

        return xlUnitCalcGroupModels;
    }

    public String getUnitLinkByDataType(String uid, String token, String dataids, String url) {
        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("uid",uid);
        requestBody.put("dt",dataids);
        requestBody.put("io","1");
        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "/Archive/GetUnitLinkByDataType", httpEntity,
                new ParameterizedTypeReference<String>() {});
        String res = null;
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            res = entity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getJSONObject("header").getInteger("status") == 0){
                return res;
            }
        }

        return null;
    }

    public List<XlGroupanalogModel> getGroupAnalog(String groupid, String token, String url) {
        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("groupid",groupid);
        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "/Archive/GetGroupAnalog", httpEntity,
                new ParameterizedTypeReference<String>() {});
        List<XlGroupanalogModel> xlGroupanalogModels = null;

        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getJSONObject("header").getInteger("status") == 0){
                xlGroupanalogModels = resJson.getJSONArray("body").toJavaList(XlGroupanalogModel.class);
            }

        }

        return xlGroupanalogModels;
    }

    public List<XlAidDidModel> getDidByAnalogId(String aids, String url, String token) {
        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("aid",aids);
        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "/Archive/GetDidByAnalogId", httpEntity,
                new ParameterizedTypeReference<String>() {});
        List<XlAidDidModel> result = null;
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getJSONObject("header").getInteger("status") == 0){
                result = resJson.getJSONArray("body").toJavaList(XlAidDidModel.class);
            }

        }

        return result;
    }

    public List<XlAidDidModel> getAnalogByDid(String dids, String url, String token) {
        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("did",dids);
        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "/Archive/GetAnalogByDid", httpEntity,
                new ParameterizedTypeReference<String>() {});
        List<XlAidDidModel> result = null;
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getJSONObject("header").getInteger("status") == 0){
                result = resJson.getJSONArray("body").toJavaList(XlAidDidModel.class);
            }

        }

        return result;
    }

    public List<UnitGroupMounthDataBean> getUnitGroupMonthData(Map<String, String> request) {

        List<UnitGroupMounthDataBean> result = new ArrayList<UnitGroupMounthDataBean>();
        if (CollectionUtils.isEmpty(request)){
            return result;
        }

        String url = request.get("url");
        url = "direct://"+url;
        String token = request.get("token");
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("uid",request.get("uid"));
        requestBody.put("sdt",request.get("sdt"));
        requestBody.put("edt",request.get("edt"));
        requestBody.put("dataid",request.get("dataid"));
        requestBody.put("io","1");

        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "Unit/GetUnitGroupMonthData", httpEntity,
                new ParameterizedTypeReference<String>() {});

        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return result;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                result = obj.getJSONArray("body").toJavaList(UnitGroupMounthDataBean.class);
            }


        }


        return result;
    }

    public List<UnitGroupMounthDataBean> getUnitGroupDayData(Map<String, String> request) {
        List<UnitGroupMounthDataBean> result = new ArrayList<UnitGroupMounthDataBean>();
        if (CollectionUtils.isEmpty(request)){
            return result;
        }

        String url = request.get("url");
        url = "direct://"+url;
        String token = request.get("token");
        HttpHeaders headers = getHttpHeaders(token);
        Map<String,String> requestBody = new HashMap<String, String>();
        requestBody.put("uid",request.get("uid"));
        requestBody.put("sdt",request.get("sdt"));
        requestBody.put("edt",request.get("edt"));
        requestBody.put("dataid",request.get("dataid"));
        requestBody.put("io",request.get("io"));

        HttpEntity httpEntity = new HttpEntity(requestBody,headers);
        ResponseEntity<String> entity = genericRest.post(url + "Unit/GetUnitGroupDayData", httpEntity,
                new ParameterizedTypeReference<String>() {});

        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return result;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                result = obj.getJSONArray("body").toJavaList(UnitGroupMounthDataBean.class);
            }

        }


        return result;
    }



    public List<AnalogDocBean> getAnalogDoc(Map<String, String> request) {
        List<AnalogDocBean> analogDocBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String aids = request.get("aids");

        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(aids)){
            return  analogDocBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("aid",aids);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Archive/GetAnalogDoc", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return analogDocBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                analogDocBeans = obj.getJSONArray("body").toJavaList(AnalogDocBean.class);
            }

        }

        return analogDocBeans;
    }

    public List<MeterArchiveBean> getMeterArchive(Map<String, String> request) {
        List<MeterArchiveBean> meterArchiveBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String dids = request.get("dids");

        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(dids)){
            return  meterArchiveBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("did",dids);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Archive/GetMeterArchive", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return meterArchiveBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                meterArchiveBeans = obj.getJSONArray("body").toJavaList(MeterArchiveBean.class);
            }

        }

        return meterArchiveBeans;
    }

    public List<AnalogTSCurveDataBean> getMeterAnalogTSCurveData(Map<String, String> request) {
        List<AnalogTSCurveDataBean> analogTSCurveDataBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String dids = request.get("aids");
        String sdt= request.get("sdt");
        String edt = request.get("edt");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(dids)
        || Strings.isNullOrEmpty(sdt) || Strings.isNullOrEmpty(edt)){
            return  analogTSCurveDataBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("did",dids);
        map.put("sdt",sdt);
        map.put("edt",edt);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Meter/GetMeterAnalogTSCurveData", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return analogTSCurveDataBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                analogTSCurveDataBeans = obj.getJSONArray("body").toJavaList(AnalogTSCurveDataBean.class);
            }

        }

        return analogTSCurveDataBeans;
    }

    public List<AnalogDataBean> getAnalogDayData(Map<String, String> request) {
        List<AnalogDataBean> analogDataBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String aid = request.get("aids");
        String sdt= request.get("sdt");
        String edt = request.get("edt");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(aid)
                || Strings.isNullOrEmpty(sdt) || Strings.isNullOrEmpty(edt)){
            return  analogDataBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("aid",aid);
        map.put("sdt",sdt);
        map.put("edt",edt);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Meter/GetAnalogDayData", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return analogDataBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                analogDataBeans = obj.getJSONArray("body").toJavaList(AnalogDataBean.class);
            }

        }

        return analogDataBeans;
    }

    public List<AnalogDataBean> getAnalogMonthData(Map<String, String> request) {
        List<AnalogDataBean> analogDataBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String aid = request.get("aids");
        String sdt= request.get("sdt");
        String edt = request.get("edt");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(aid)
                || Strings.isNullOrEmpty(sdt) || Strings.isNullOrEmpty(edt)){
            return  analogDataBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("aid",aid);
        map.put("sdt",sdt);
        map.put("edt",edt);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Meter/GetAnalogMonthData", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return analogDataBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                analogDataBeans = obj.getJSONArray("body").toJavaList(AnalogDataBean.class);
            }

        }

        return analogDataBeans;
    }

    public List<UnitInfoBean> getVagueUnit(Map<String, String> request) {
        List<UnitInfoBean> unitInfoBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String uid = request.get("uid");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(uid)){
            return  unitInfoBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("uid",uid);
        map.put("name","");
        map.put("ut","7000");
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Archive/GetVagueUnit", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return unitInfoBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                unitInfoBeans = obj.getJSONArray("body").toJavaList(UnitInfoBean.class);
            }

        }

        return unitInfoBeans;
    }

    public List<CalcGroupBean> getUnitAllCalcGroup(Map<String, String> request) {
        List<CalcGroupBean> calcGroupBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        String uIds = request.get("uIds");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(url) ||Strings.isNullOrEmpty(uIds)){
            return  calcGroupBeans;
        }

        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);

        Map<String,String> map = new HashMap<String, String>();
        map.put("uIds",uIds);
        HttpEntity httpEntity = new HttpEntity(map,headers);

        ResponseEntity<String> entity = genericRest.post(url + "Archive/GetUnitAllCalcGroup", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return calcGroupBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                calcGroupBeans = obj.getJSONArray("body").toJavaList(CalcGroupBean.class);
            }

        }

        return calcGroupBeans;
    }

    public List<UnitGroupCurveDataBean> getUnitGroupCurveData(Map<String, String> request) {
        List<UnitGroupCurveDataBean> groupCurveDataBeans = null;
        String token = request.get("token");
        String url = request.get("url");
        url = "direct://"+url;
        HttpHeaders headers = getHttpHeaders(token);
        HttpEntity httpEntity = new HttpEntity(request,headers);
        ResponseEntity<String> entity = genericRest.post(url + "Unit/GetUnitGroupCurveData", httpEntity,
                new ParameterizedTypeReference<String>() {});
        if (entity.getStatusCode().equals(HttpStatus.OK)){
            String res = entity.getBody();
            if (Strings.isNullOrEmpty(res)){
                return groupCurveDataBeans;
            }

            JSONObject obj = JSONObject.parseObject(res);
            if (obj.getJSONObject("header").getInteger("status") == 0){
                groupCurveDataBeans = obj.getJSONArray("body").toJavaList(UnitGroupCurveDataBean.class);
            }

        }

        return groupCurveDataBeans;
    }
}
