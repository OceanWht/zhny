package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.ProcessConfigureModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProcessConfigureModelMapper {
    int deleteByPrimaryKey(String dataIndex);

    int insert(ProcessConfigureModel record);

    int insertSelective(ProcessConfigureModel record);

    ProcessConfigureModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProcessConfigureModel record);

    int updateByPrimaryKey(ProcessConfigureModel record);

    List<ProcessConfigureModel> getList(@Param("enterpriseCode")String enterpriseCode);
}