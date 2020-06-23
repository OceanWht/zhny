package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.JsonModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JsonModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JsonModel record);

    int insertSelective(JsonModel record);

    JsonModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JsonModel record);

    int updateByPrimaryKeyWithBLOBs(JsonModel record);

    int updateByPrimaryKey(JsonModel record);
}