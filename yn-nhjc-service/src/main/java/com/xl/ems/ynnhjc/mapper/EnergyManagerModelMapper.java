package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnergyManagerModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnergyManagerModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnergyManagerModel record);

    int insertSelective(EnergyManagerModel record);

    EnergyManagerModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnergyManagerModel record);

    int updateByPrimaryKey(EnergyManagerModel record);

    List<EnergyManagerModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);
}