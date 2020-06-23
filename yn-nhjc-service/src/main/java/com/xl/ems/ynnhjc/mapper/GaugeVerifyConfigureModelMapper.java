package com.xl.ems.ynnhjc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.xl.ems.ynnhjc.model.GaugeVerifyConfigureModel;

@Mapper
public interface GaugeVerifyConfigureModelMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(GaugeVerifyConfigureModel record);

    int insertSelective(GaugeVerifyConfigureModel record);

    GaugeVerifyConfigureModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GaugeVerifyConfigureModel record);

    int updateByPrimaryKey(GaugeVerifyConfigureModel record);
    
    List<GaugeVerifyConfigureModel> selectByEnterpriseCode(String enterpriseCode);
}