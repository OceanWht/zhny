package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.SysEnergyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysEnergyModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysEnergyModel record);

    int insertSelective(SysEnergyModel record);

    SysEnergyModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysEnergyModel record);

    int updateByPrimaryKey(SysEnergyModel record);

    List<SysEnergyModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);
}