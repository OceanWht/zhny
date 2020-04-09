package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.SurfaceUrlModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SurfaceUrlModelMapper {
    int deleteByPrimaryKey(String urlType);

    int insert(SurfaceUrlModel record);

    int insertSelective(SurfaceUrlModel record);

    SurfaceUrlModel selectByPrimaryKey(String urlType);

    int updateByPrimaryKeySelective(SurfaceUrlModel record);

    int updateByPrimaryKey(SurfaceUrlModel record);
}