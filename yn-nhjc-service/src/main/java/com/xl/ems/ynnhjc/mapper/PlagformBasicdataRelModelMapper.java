package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.PlagformBasicdataRelModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlagformBasicdataRelModelMapper {
    int deleteByPrimaryKey(Integer itemindex);

    int insert(PlagformBasicdataRelModel record);

    int insertSelective(PlagformBasicdataRelModel record);

    PlagformBasicdataRelModel selectByPrimaryKey(Integer itemindex);

    int updateByPrimaryKeySelective(PlagformBasicdataRelModel record);

    int updateByPrimaryKey(PlagformBasicdataRelModel record);

    List<PlagformBasicdataRelModel> getAll();
}