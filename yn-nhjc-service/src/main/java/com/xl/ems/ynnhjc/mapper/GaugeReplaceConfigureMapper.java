package com.xl.ems.ynnhjc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xl.ems.ynnhjc.model.GaugeReplaceConfigure;

@Mapper
public interface GaugeReplaceConfigureMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(GaugeReplaceConfigure record);

    int insertSelective(GaugeReplaceConfigure record);

    GaugeReplaceConfigure selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GaugeReplaceConfigure record);

    int updateByPrimaryKey(GaugeReplaceConfigure record);
    
    List<GaugeReplaceConfigure> selectByEnterpriseCode(String enterpriseCode);
}