package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.CalculaterModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalculaterModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CalculaterModel record);

    int insertSelective(CalculaterModel record);

    CalculaterModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CalculaterModel record);

    int updateByPrimaryKeyWithBLOBs(CalculaterModel record);

    int updateByPrimaryKey(CalculaterModel record);

    List<CalculaterModel> selectByEnterpriseCode(String enterpriseCode);
}