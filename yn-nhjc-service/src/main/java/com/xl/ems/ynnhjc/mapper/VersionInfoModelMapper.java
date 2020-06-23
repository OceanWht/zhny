package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.VersionInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface VersionInfoModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(VersionInfoModel record);

    int insertSelective(VersionInfoModel record);

    VersionInfoModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(VersionInfoModel record);

    int updateByPrimaryKey(VersionInfoModel record);

    VersionInfoModel selectByDLR(@Param("enterprisecode") String enterpriseCode);

    List<Map<String,Object>> getDataCode(String enterpriseCode);
}