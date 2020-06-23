package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnergyMonitorModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnergyMonitorModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnergyMonitorModel record);

    int insertSelective(EnergyMonitorModel record);

    EnergyMonitorModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnergyMonitorModel record);

    int updateByPrimaryKey(EnergyMonitorModel record);

    List<EnergyMonitorModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);
}