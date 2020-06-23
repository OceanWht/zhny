package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.AKModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AKModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AKModel record);

    int insertSelective(AKModel record);

    AKModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AKModel record);

    int updateByPrimaryKey(AKModel record);

    AKModel selectByEP(@Param("enterprisecode") String enterpriseCode);

    List<AKModel> getAll();
}