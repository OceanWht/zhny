package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.ArganEnergyConsumeModel;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArganEnergyConsumeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArganEnergyConsumeModel record);

    int insertSelective(ArganEnergyConsumeModel record);

    ArganEnergyConsumeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArganEnergyConsumeModel record);

    int updateByPrimaryKey(ArganEnergyConsumeModel record);
    
    List<ArganEnergyConsumeModel> selectByEnterpriseCode(String enterpriseCode);
    
    List<ArganEnergyConsumeModel> selectUnLoadingData(Map<String,Object> params);
    
    int updateById_Energy(Map<String,Object> params);
}