package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnergyCertificationModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EnergyCertificationModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EnergyCertificationModel record);

    int insertSelective(EnergyCertificationModel record);

    EnergyCertificationModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnergyCertificationModel record);

    int updateByPrimaryKey(EnergyCertificationModel record);

    List<EnergyCertificationModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);
}