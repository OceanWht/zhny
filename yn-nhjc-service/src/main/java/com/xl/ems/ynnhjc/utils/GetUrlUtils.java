package com.xl.ems.ynnhjc.utils;

import com.xl.ems.ynnhjc.mapper.SurfaceUrlModelMapper;
import com.xl.ems.ynnhjc.model.SurfaceUrlModel;
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
