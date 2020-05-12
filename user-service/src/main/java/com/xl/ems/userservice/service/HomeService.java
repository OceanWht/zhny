package com.xl.ems.userservice.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.xl.ems.userservice.bean.UnitGroupMounthDataBean;
import com.xl.ems.userservice.common.RestCode;
import com.xl.ems.userservice.common.RestResponse;
import com.xl.ems.userservice.config.GenericRest;
import com.xl.ems.userservice.mapper.XlUnitCalcGroupModelMapper;
import com.xl.ems.userservice.model.XlUnitCalcGroupModel;
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

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class HomeService {


    @Autowired
    XlUnitCalcGroupModelMapper xlUnitCalcGroupModelMapper;


    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    GetUrlUtils getUrlUtils;

    @Autowired
    GenericRest genericRest;

    @Value("${user.api_gateway_surface}")
    private String apiUrlType;

    @Value("${user.8008_surface}")
    private String softUrlType;


    private static final Logger LOGGER = LoggerFactory.getLogger(HomeService.class);

    /**
     * 根据企业UID获取计算组，从而获取能源类型
     * @param uid
     * @return
     */
    public List<XlUnitCalcGroupModel> getAllEnergyType(String uid) {

        return xlUnitCalcGroupModelMapper.selectByUid(Integer.valueOf(uid));
    }

    public List<UnitGroupMounthDataBean> getEnergyMonthData(Map<String, String> requestMap) {
        LOGGER.info("HomeService getEnergyMonthData......begin");

        List<UnitGroupMounthDataBean> result = null;
        String token = requestMap.get("token");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String dataid = requestMap.get("dataid");
        String uid = requestMap.get("uid");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(sdt)
                ||Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(uid)){

            LOGGER.error("HomeService getEnergyMonthData......params error");
            return null;
        }

        String key = uid+":"+sdt+":"+edt+":"+dataid;
        if (redisTemplate.opsForValue().get(key) != null){
            return JSONArray.parseArray(redisTemplate.opsForValue().get(key),UnitGroupMounthDataBean.class);
        }
        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("HomeService getEnergyMonthData......apiUrl error");
            return null;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("HomeService getEnergyMonthData......softUrl error");
            return null;
        }
        requestMap.put("url",softUrl);

        ResponseEntity<RestResponse<List<UnitGroupMounthDataBean>>> responseEntity = genericRest.post(apiUrl + "/GetUnitGroupMonthData", requestMap,
                new ParameterizedTypeReference<RestResponse<List<UnitGroupMounthDataBean>>>() {});

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            result = responseEntity.getBody().getResult();
            //放到缓存里，提高访问速率
            if (redisTemplate.opsForValue().get(key) == null){
                redisTemplate.opsForValue().set(key,JSONArray.toJSONString(result));
                redisTemplate.expire(key,1, TimeUnit.HOURS);
            }

        }else {
            LOGGER.error("HomeService getEnergyMonthData......responseEntity error");
            return null;
        }

        LOGGER.info("HomeService getEnergyMonthData......end");
        return result;
    }

    public List<UnitGroupMounthDataBean> getEnergyDayData(Map<String, String> requestMap) {
        LOGGER.info("HomeService getEnergyDayData......begin");
        List<UnitGroupMounthDataBean> result = null;
        String token = requestMap.get("token");
        String sdt = requestMap.get("sdt");
        String edt = requestMap.get("edt");
        String dataid = requestMap.get("dataid");
        String uid = requestMap.get("uid");
        String io = requestMap.get("io");
        if (Strings.isNullOrEmpty(token) || Strings.isNullOrEmpty(sdt)
                ||Strings.isNullOrEmpty(edt) || Strings.isNullOrEmpty(dataid) || Strings.isNullOrEmpty(uid)
                ||Strings.isNullOrEmpty(io)){

            LOGGER.error("HomeService getEnergyDayData......params error");
            return null;
        }

        String key = uid+":"+sdt+":"+edt+":"+dataid;
        if (redisTemplate.opsForValue().get(key) != null){
            return JSONArray.parseArray(redisTemplate.opsForValue().get(key),UnitGroupMounthDataBean.class);
        }
        //调用接口，修改密码
        //api接口服务
        String apiUrl = getUrlUtils.getUrl(apiUrlType);
        if (Strings.isNullOrEmpty(apiUrl)) {
            LOGGER.error("HomeService getEnergyDayData......apiUrl error");
            return null;
        }
        String softUrl = getUrlUtils.getUrl(softUrlType);
        if (Strings.isNullOrEmpty(softUrl)) {
            LOGGER.error("HomeService getEnergyDayData......softUrl error");
            return null;
        }
        requestMap.put("url",softUrl);

        ResponseEntity<RestResponse<List<UnitGroupMounthDataBean>>> responseEntity = genericRest.post(apiUrl + "/GetUnitGroupDayData", requestMap,
                new ParameterizedTypeReference<RestResponse<List<UnitGroupMounthDataBean>>>() {});

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)){
            result = responseEntity.getBody().getResult();
            //放到缓存里，提高访问速率
            if (redisTemplate.opsForValue().get(key) == null){
                redisTemplate.opsForValue().set(key,JSONArray.toJSONString(result));
                redisTemplate.expire(key,1, TimeUnit.HOURS);
            }

        }else {
            LOGGER.error("HomeService getEnergyDayData......responseEntity error");
            return null;
        }

        LOGGER.info("HomeService getEnergyDayData......end");
        return result;
    }
}
