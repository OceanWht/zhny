package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnergyConservationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnergyConservationModelMapper {
    int deleteByPrimaryKey(String dataIndex);

    int insert(EnergyConservationModel record);

    int insertSelective(EnergyConservationModel record);

    EnergyConservationModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnergyConservationModel record);

    int updateByPrimaryKey(EnergyConservationModel record);

    List<EnergyConservationModel> selectByCode(@Param("enterpriseCode") String code);
}