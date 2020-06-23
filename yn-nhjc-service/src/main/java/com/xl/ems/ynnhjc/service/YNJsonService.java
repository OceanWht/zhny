package com.xl.ems.ynnhjc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.ems.ynnhjc.mapper.JsonModelMapper;
import com.xl.ems.ynnhjc.model.JsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName YNJsonService
 * @Description TODO
 * @Author wht
 * @Date 14:57
 **/
@Service
public class YNJsonService {

    @Autowired
    JsonModelMapper jsonModelMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    public JSONObject regionList() {
        System.out.println("YNJsonService regionList begin......");
        String key = "regionList";
        JSONObject result = new JSONObject();
        JSONObject data = new JSONObject();
       /* if (redisTemplate.opsForValue().get(key) != null){
            data.put("errno",0);
            data.put("data",JSONArray.parseObject(redisTemplate.opsForValue().get(key)));
            result.put("data",data);
            return result;
        }*/
        JsonModel jsonModel = jsonModelMapper.selectByPrimaryKey(1);
        String value = jsonModel.getValue();
        JSONObject reginDataJson = JSONObject.parseObject(value);
        JSONArray regionList =  reginDataJson.getJSONArray("regionList");
        if (regionList != null){
           // redisTemplate.opsForValue().set(key,JSONArray.toJSONString(result));
           // redisTemplate.expire(key,30, TimeUnit.MINUTES);
            data.put("errno",0);
            data.put("data",regionList);
            result.put("data",data);

        }else {
            System.out.println("YNJsonService regionList error......");
            data.put("errno",1);
            result.put("data",data);
        }
        System.out.println("YNJsonService regionList end......");
        return result;
    }

    public JSONObject EnergyTypeUnitCollectCodes() {
        System.out.println("YNJsonService EnergyTypeUnitCollectCodes begin......");
        JSONObject result = new JSONObject();
        JsonModel jsonModel = jsonModelMapper.selectByPrimaryKey(1);
        String value = jsonModel.getValue();
        JSONObject reginDataJson = JSONObject.parseObject(value);
        JSONArray EnergyTypeUnitCollectCodes =  reginDataJson.getJSONArray("EnergyTypeUnitCollectCodes");
        if (EnergyTypeUnitCollectCodes != null){
            // redisTemplate.opsForValue().set(key,JSONArray.toJSONString(result));
            // redisTemplate.expire(key,30, TimeUnit.MINUTES);
            result.put("errno",0);
            result.put("data",EnergyTypeUnitCollectCodes);

        }else {
            System.out.println("YNJsonService EnergyTypeUnitCollectCodes error......");
            result.put("errno",1);
        }
        System.out.println("YNJsonService EnergyTypeUnitCollectCodes end......");
        return result;
    }

    public JSONObject MetertypeJsonCodes() {
        System.out.println("YNJsonService MetertypeJsonCodes begin......");
        JSONObject result = new JSONObject();
        JsonModel jsonModel = jsonModelMapper.selectByPrimaryKey(1);
        String value = jsonModel.getValue();
        JSONObject reginDataJson = JSONObject.parseObject(value);
        JSONArray MetertypeJsonCodes =  reginDataJson.getJSONArray("MetertypeJsonCodes");
        if (MetertypeJsonCodes != null){
            // redisTemplate.opsForValue().set(key,JSONArray.toJSONString(result));
            // redisTemplate.expire(key,30, TimeUnit.MINUTES);
            result.put("errno",0);
            result.put("data",MetertypeJsonCodes);

        }else {
            System.out.println("YNJsonService MetertypeJsonCodes error......");
            result.put("errno",1);
        }
        System.out.println("YNJsonService MetertypeJsonCodes end......");
        return result;
    }
}
