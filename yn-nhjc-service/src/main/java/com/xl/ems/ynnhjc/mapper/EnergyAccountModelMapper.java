package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnergyAccountModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnergyAccountModelMapper {
    int deleteByPrimaryKey(String dataIndex);

    int insert(EnergyAccountModel record);

    int insertSelective(EnergyAccountModel record);

    EnergyAccountModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnergyAccountModel record);

    int updateByPrimaryKey(EnergyAccountModel record);

    List<EnergyAccountModel> getAll(@Param("enterpriseCode")String enterpriseCode);
}