package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.UnitConfigureModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UnitConfigureModelMapper {
    int deleteByPrimaryKey(String dataIndex);

    int insert(UnitConfigureModel record);

    int insertSelective(UnitConfigureModel record);

    UnitConfigureModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UnitConfigureModel record);

    int updateByPrimaryKey(UnitConfigureModel record);

    List<UnitConfigureModel> getList(@Param("enterpriseCode")String enterpriseCode);
}