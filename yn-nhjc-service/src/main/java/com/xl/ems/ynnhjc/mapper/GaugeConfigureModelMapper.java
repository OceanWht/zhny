package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.GaugeConfigureModel;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GaugeConfigureModelMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(GaugeConfigureModel record);

    int insertSelective(GaugeConfigureModel record);

    GaugeConfigureModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GaugeConfigureModel record);

    int updateByPrimaryKey(GaugeConfigureModel record);
    
    List<GaugeConfigureModel> selectByEnterpriseCode(String enterprisecode);
}