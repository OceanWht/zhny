package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.CompanyContacterModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyContacterModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CompanyContacterModel record);

    int insertSelective(CompanyContacterModel record);

    CompanyContacterModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompanyContacterModel record);

    int updateByPrimaryKey(CompanyContacterModel record);

    List<CompanyContacterModel> selectByEnterpriseCode(@Param("enterpriseCode") String enterpriseCode);

    List<CompanyContacterModel> getAll(@Param("enterpriseCode") String enterpriseCode);
}