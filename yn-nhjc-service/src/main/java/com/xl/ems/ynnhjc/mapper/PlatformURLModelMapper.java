package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.PlatformURLModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PlatformURLModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlatformURLModel record);

    int insertSelective(PlatformURLModel record);

    PlatformURLModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlatformURLModel record);

    int updateByPrimaryKey(PlatformURLModel record);

    PlatformURLModel selectByETP(@Param("enterpriseCode") String enterprisecode);
}