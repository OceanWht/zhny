package com.xl.ems.userservice.controller;


import com.xl.ems.userservice.common.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestConntroller {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConntroller.class);

    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/test")
    @ResponseBody
    public RestResponse<String> test(){

        redisTemplate.opsForValue().set("key1","value1");

        LOGGER.info(redisTemplate.opsForValue().get("key1"));
        return RestResponse.success("Test 成功: "+redisTemplate.opsForValue().get("key1"));
    }
}
