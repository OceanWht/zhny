package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.BasicdataOtherModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BasicdataOtherModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BasicdataOtherModel record);

    int insertSelective(BasicdataOtherModel record);

    BasicdataOtherModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasicdataOtherModel record);

    int updateByPrimaryKey(BasicdataOtherModel record);

    List<BasicdataOtherModel> getAll();
}