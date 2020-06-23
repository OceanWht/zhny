package com.xl.ems.ynnhjc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.xl.ems.ynnhjc.model.ArganEnergyIdcModel;

@Mapper
public interface ArganEnergyIdcModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArganEnergyIdcModel record);

    int insertSelective(ArganEnergyIdcModel record);

    ArganEnergyIdcModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArganEnergyIdcModel record);

    int updateByPrimaryKey(ArganEnergyIdcModel record);
    
    List<ArganEnergyIdcModel> selectByEnterpriseCode(String enterpriseCode);
    
    List<ArganEnergyIdcModel> selectUnLoadingData(Map<String,Object> params);
    
    int updateById_IDC(Map<String,Object> params);
}