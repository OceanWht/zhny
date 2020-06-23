package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnterpriseEnergyHistoryModel;
import com.xl.ems.ynnhjc.model.EnterpriseEnergyModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnterpriseEnergyHistoryModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnterpriseEnergyHistoryModel record);

    int insertSelective(EnterpriseEnergyHistoryModel record);

    EnterpriseEnergyHistoryModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseEnergyHistoryModel record);

    int updateByPrimaryKey(EnterpriseEnergyHistoryModel record);

    List<EnterpriseEnergyHistoryModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);
}