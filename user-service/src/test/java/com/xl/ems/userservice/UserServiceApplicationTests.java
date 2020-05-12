package com.xl.ems.userservice;

import com.xl.ems.userservice.mapper.XlGroupanalogModelMapper;
import com.xl.ems.userservice.model.XlGroupanalogModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class UserServiceApplicationTests {

    @Autowired
    XlGroupanalogModelMapper xlGroupanalogModelMapper;

    @Test
    void contextLoads() {
        System.out.println(111);
        HashMap<String,Integer> map = new HashMap<>();
        map.put("uid",110236);
        map.put("dataid",5);
        List<XlGroupanalogModel> xlGroupanalogModels = xlGroupanalogModelMapper.getGroupAnalogbyUidDataid(map);
        System.out.println(xlGroupanalogModels);
    }

}
