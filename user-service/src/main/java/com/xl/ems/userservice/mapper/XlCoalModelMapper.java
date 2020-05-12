package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlCoalModel;
import com.xl.ems.userservice.model.XlCoalModelKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface XlCoalModelMapper {
    int deleteByPrimaryKey(XlCoalModelKey key);

    int insert(XlCoalModel record);

    int insertSelective(XlCoalModel record);

    XlCoalModel selectByPrimaryKey(XlCoalModelKey key);

    int updateByPrimaryKeySelective(XlCoalModel record);

    int updateByPrimaryKey(XlCoalModel record);
}