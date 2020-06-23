package com.xl.ems.ynnhjc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xl.ems.ynnhjc.model.ArganEnergyWarmModel;

@Mapper
public interface ArganEnergyWarmModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArganEnergyWarmModel record);

    int insertSelective(ArganEnergyWarmModel record);

    ArganEnergyWarmModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArganEnergyWarmModel record);

    int updateByPrimaryKey(ArganEnergyWarmModel record);
    
    List<ArganEnergyWarmModel> selectByEnterpriseCode(String enterpriseCode);
    
    List<ArganEnergyWarmModel> selectUnLoadingData(Map<String, Object> params);
    
    int updateById_Warm(Map<String, Object> params);
}