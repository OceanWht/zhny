package com.xl.ems.userservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.bean.*;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.XlAidDidModelMapper;
import com.xl.ems.userservice.model.XlAidDidModel;
import com.xl.ems.userservice.service.EnergyAnalysService;
import com.xl.ems.userservice.service.EnergyService;
import com.xl.ems.userservice.utils.GetUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EnergyAnalysServiceImpl implements EnergyAnalysService {

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;

    @Autowired
    StringRedisTemplate redisTemplate;


    @Autowired
    XlAidDidModelMapper xlAidDidModelMapper;

    //第二种结构树是在第一种结构树之上扩展得
    @Autowired
    EnergyService energyService;

    //第二种结构树
    private static final String UNITLINK_TWO = "2";

    //第一种结构树
    private static final String UNITLINK_ONE = "1";

    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(EnergyAnalysServiceImpl.class);


    /**
     * 第二种结构树
     * @param requestMap
     * @return
     */
    @Override
    public JSONObject unitTreeTWO(Map<String, String> requestMap) {

        LOGGER.info("EnergyAnalysServiceImpl unitTreeTWO...begin");

        JSONObject result = new JSONObject();


        String uid = requestMap.get("uid");
        String dataid = requestMap.get("dataid");
        String ioo = requestMap.get("ioo");
        String token = requestMap.get("token");

        if (Strings.isNullOrEmpty(uid)|| Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(ioo)||Strings.isNullOrEmpty(token)) {
            LOGGER.error("EnergyAnalysServiceImpl unitTreeTWO......params dataid or ioo error");
            return null;
        }

        //先从缓存找
       /* String key = uid + ":" + dataid + ":treelist:" + UNITLINK_TWO;
        if (redisTemplate.opsForValue().get(key) != null) {
            result = JSONObject.parseObject(redisTemplate.opsForValue().get(key));
            LOGGER.info("EnergyAnalysServiceImpl unitTreeTWO...redis end");
            return result;
        }*/

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("EnergyAnalysServiceImpl unitTreeTWO......apiUrl or softUrl error");
            return null;
        }

        //先获取第一种结构树,再设置设备
        requestMap.put("flag",UNITLINK_ONE);
        JSONObject treelist = energyService.unitTree(requestMap);
        UnitLinkTree root = treelist.getJSONObject("treelist").toJavaObject(UnitLinkTree.class);
        //获得树之后，循环设置单元下面得设备
        //默认展示全部设备的数据
        List<XlAidDidModel> xlAidDidModels = null;
        //模拟量档案集合
        List<AnalogDocBean> analogDocBeans = null;
        //设备档案集合 有删除操作，用线程安全的不会报ConcurrentModificationException
        List<MeterArchiveBean> meterArchiveBeans = null;
        //单元ID
        if (root != null){
            setUnitChildTWO(dataid, token, apiUrl, softUrl, root);
        }

        result.put("treelistTWO",root);

       /* if (redisTemplate.opsForValue().get(key) == null) {
            redisTemplate.opsForValue().set(key,JSONObject.toJSONString(result));
            redisTemplate.expire(key,15, TimeUnit.MINUTES);
        }*/

        return result;
    }


    private void setUnitChildTWO(String dataid, String token, String apiUrl, String softUrl, UnitLinkTree root) {
        List<XlAidDidModel> xlAidDidModels;
        List<AnalogDocBean> analogDocBeans;
        List<MeterArchiveBean> meterArchiveBeans;
        if (!Strings.isNullOrEmpty(root.getId())){
            //点击树结构不同的单元
            HashMap<String, Integer> mapp = new HashMap<>();
            mapp.put("uid", Integer.valueOf(root.getId()));
            mapp.put("dataid", Integer.valueOf(dataid));
            xlAidDidModels = xlAidDidModelMapper.getByUidDataid2(mapp);
             //A a = new A();
             // A.B ab = a.new B();
            EnergyService.SetAnaDocAndArchive setAnaDocAndArchive =
                    energyService.new SetAnaDocAndArchive(token, apiUrl, softUrl, xlAidDidModels).invoke();
            analogDocBeans = setAnaDocAndArchive.getAnalogDocBeans();
            meterArchiveBeans = setAnaDocAndArchive.getMeterArchiveBeans();
            for (MeterArchiveBean meterArchiveBean : meterArchiveBeans) {
                //设置设备得模拟量档案
                energyService.setAnaDoc(analogDocBeans,xlAidDidModels,meterArchiveBean,root.getId(),dataid);
            }

            root.setDievices(meterArchiveBeans);
            if (root.getChildren()!=null){
                for (UnitLinkTree unitLinkTree: root.getChildren()){
                    setUnitChildTWO(dataid,token,apiUrl,softUrl,unitLinkTree);
                }
            }
        }
    }


    @Override
    public JSONObject getAnalogDayData(Map<String, String> requestMap) {
        LOGGER.info("EnergyAnalysServiceImpl getAnalogData...begin");

        JSONObject result = new JSONObject();
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("EnergyAnalysServiceImpl getAnalogData......requestMap error");
            return null;
        }

        String aids = requestMap.get("aids");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String sdt1 = requestMap.get("sdt1");
        String edt1 = requestMap.get("edt1");
        String sdt2 = requestMap.get("sdt2");
        String edt2 = requestMap.get("edt2");
        String token = requestMap.get("token");

        if (Strings.isNullOrEmpty(aids) || Strings.isNullOrEmpty(sdt) ||
        Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(token)){
            LOGGER.error("EnergyAnalysServiceImpl getAnalogData......params   error");
            return null;
        }


        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("EnergyAnalysServiceImpl getAnalogData......apiUrl or softUrl error");
            return null;
        }

        //请求三次获取数据 当月，上月，去年同期得数据 到当月当天
        //当月数据
        List<AnalogDataBean> analogMonthData = null;
        List<AnalogDataBean> analogLastMonthData = null;
        List<AnalogDataBean> analogLastYearMonthData = null;

        Map<String,String> request = new HashMap<>();
        request.put("token",token);
        request.put("aids",aids);
        request.put("sdt",sdt);
        request.put("edt",edt);
        request.put("url",softUrl);
        //当月
        ResponseEntity<String> responseEntity =  genericRest.post(apiUrl + "/GetAnalogDayData", request, new ParameterizedTypeReference<String>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getString("code").equals("0")){
                analogMonthData  = resJson.getJSONArray("result").toJavaList(AnalogDataBean.class);
            }

        }

        //上月
        if(!Strings.isNullOrEmpty(sdt1) && !Strings.isNullOrEmpty(edt1)){
            request.put("sdt",sdt1);
            request.put("edt",edt1);
            responseEntity =  genericRest.post(apiUrl + "/GetAnalogDayData", request, new ParameterizedTypeReference<String>() {});
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                String res = responseEntity.getBody();
                JSONObject resJson = JSONObject.parseObject(res);
                if (resJson.getString("code").equals("0")){
                    analogLastMonthData  = resJson.getJSONArray("result").toJavaList(AnalogDataBean.class);
                }

            }
        }

        //去年
        if(!Strings.isNullOrEmpty(sdt2) && !Strings.isNullOrEmpty(edt2)){
            request.put("sdt",sdt2);
            request.put("edt",edt2);
            responseEntity =  genericRest.post(apiUrl + "/GetAnalogDayData", request, new ParameterizedTypeReference<String>() {});
            if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
                String res = responseEntity.getBody();
                JSONObject resJson = JSONObject.parseObject(res);
                if (resJson.getString("code").equals("0")){
                    analogLastYearMonthData  = resJson.getJSONArray("result").toJavaList(AnalogDataBean.class);
                }

            }
        }

        result.put("analogMonthData",analogMonthData);
        if (!CollectionUtils.isEmpty(analogLastMonthData)){
            result.put("analogLastMonthData",analogLastMonthData);
        }
        if (!CollectionUtils.isEmpty(analogLastYearMonthData)){
            result.put("analogLastYearMonthData",analogLastYearMonthData);
        }

        return result;
    }

    @Override
    public JSONObject getUnitDayData(Map<String, String> requestMap) {
        LOGGER.info("EnergyAnalysServiceImpl getUnitDayData...begin");

        JSONObject result = new JSONObject();
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("EnergyAnalysServiceImpl getUnitDayData......requestMap error");
            return null;
        }

        String uid = requestMap.get("uid");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String sdt1 = requestMap.get("sdt1");
        String edt1 = requestMap.get("edt1");
        String sdt2 = requestMap.get("sdt2");
        String edt2 = requestMap.get("edt2");
        String token = requestMap.get("token");
        String dataid = requestMap.get("dataid");

        if (Strings.isNullOrEmpty(uid) || Strings.isNullOrEmpty(sdt) ||
                Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(sdt1) ||
                Strings.isNullOrEmpty(edt1) ||Strings.isNullOrEmpty(sdt2) ||
                Strings.isNullOrEmpty(edt2) || Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(dataid)){
            LOGGER.error("EnergyAnalysServiceImpl getUnitDayData......params   error");
            return null;
        }


        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("EnergyAnalysServiceImpl getUnitDayData......apiUrl or softUrl error");
            return null;
        }

        //请求三次获取数据 当月，上月，去年同期得数据 到当月当天
        //当月数据
        List<UnitGroupMounthDataBean> unitMonthData = null;
        List<UnitGroupMounthDataBean> unitLastMonthData = null;
        List<UnitGroupMounthDataBean> unitYearLastMonthData = null;
        Map<String,String> request = new HashMap<>();
        request.put("token",token);
        request.put("uid",uid);
        request.put("sdt",sdt);
        request.put("edt",edt);
        request.put("url",softUrl);
        request.put("dataid",dataid);
        request.put("io", "1");
        //当月
        ResponseEntity<RestResponse<List<UnitGroupMounthDataBean>>> responseEntity =  genericRest.post(apiUrl + "/GetUnitGroupDayData", request,
                new ParameterizedTypeReference<RestResponse<List<UnitGroupMounthDataBean>>>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            unitMonthData = responseEntity.getBody().getResult();
        }

        request.put("sdt",sdt1);
        request.put("edt",edt1);
        //上月
        responseEntity =  genericRest.post(apiUrl + "/GetUnitGroupDayData", request,
                new ParameterizedTypeReference<RestResponse<List<UnitGroupMounthDataBean>>>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            unitLastMonthData = responseEntity.getBody().getResult();
        }

        request.put("sdt",sdt2);
        request.put("edt",edt2);
        //去年同月
        responseEntity =  genericRest.post(apiUrl + "/GetUnitGroupDayData", request,
                new ParameterizedTypeReference<RestResponse<List<UnitGroupMounthDataBean>>>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            unitYearLastMonthData = responseEntity.getBody().getResult();
        }


        result.put("unitMonthData",unitMonthData);
        result.put("unitLastMonthData",unitLastMonthData);
        result.put("unitYearLastMonthData",unitYearLastMonthData);
        return result;
    }

    /**
     * 模拟量月数据
     * @param requestMap
     * @return
     */
    @Override
    public JSONObject getAnalogMonthData(Map<String, String> requestMap) {
        LOGGER.info("EnergyAnalysServiceImpl getAnalogMonthData...begin");

        JSONObject result = new JSONObject();
        if(requestMap == null || requestMap.size() == 0){
            LOGGER.error("EnergyAnalysServiceImpl getAnalogMonthData......requestMap error");
            return null;
        }

        String aids = requestMap.get("aids");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String sdt1 = requestMap.get("sdt1");
        String edt1 = requestMap.get("edt1");
        String token = requestMap.get("token");

        if (Strings.isNullOrEmpty(aids) || Strings.isNullOrEmpty(sdt) ||
                Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(sdt1) ||
                Strings.isNullOrEmpty(edt1) || Strings.isNullOrEmpty(token)){
            LOGGER.error("EnergyAnalysServiceImpl getAnalogMonthData......params   error");
            return null;
        }

        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(apiUrl) || Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("EnergyAnalysServiceImpl getUnitDayData......apiUrl or softUrl error");
            return null;
        }


        //今年数据
        List<AnalogDataBean> analogYearData = null;
        //去年数据
        List<AnalogDataBean> analogLastYearData = null;

        Map<String,String> request = new HashMap<>();
        request.put("token",token);
        request.put("aids",aids);
        request.put("sdt",sdt);
        request.put("edt",edt);
        request.put("url",softUrl);

        //今年数据
        ResponseEntity<String> responseEntity =  genericRest.post(apiUrl + "/GetAnalogMonthData", request, new ParameterizedTypeReference<String>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getString("code").equals("0")){
                analogYearData  = resJson.getJSONArray("result").toJavaList(AnalogDataBean.class);
            }

        }

        //去年数据
        request.put("sdt",sdt1);
        request.put("edt",edt1);
        responseEntity =  genericRest.post(apiUrl + "/GetAnalogMonthData", request, new ParameterizedTypeReference<String>() {});
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            String res = responseEntity.getBody();
            JSONObject resJson = JSONObject.parseObject(res);
            if (resJson.getString("code").equals("0")){
                analogLastYearData  = resJson.getJSONArray("result").toJavaList(AnalogDataBean.class);
            }

        }

        if (!CollectionUtils.isEmpty(analogYearData)){
            result.put("analogYearData",analogYearData);
        }

        if (!CollectionUtils.isEmpty(analogLastYearData)){
            result.put("analogLastYearData",analogLastYearData);
        }
        return result;
    }

}
