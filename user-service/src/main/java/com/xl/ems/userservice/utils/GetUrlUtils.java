package com.xl.ems.userservice.utils;

import com.xl.ems.userservice.mapper.SurfaceUrlModelMapper;
import com.xl.ems.userservice.model.SurfaceUrlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUrlUtils {


    @Autowired
    SurfaceUrlModelMapper surfaceUrlModelMapper;

    public   String getUrl(String type) {
        SurfaceUrlModel surfaceUrlModel = surfaceUrlModelMapper.selectByPrimaryKey(type);
        if (surfaceUrlModel == null){
            return  null;
        }
        return surfaceUrlModel.getUrl();
    }
}
