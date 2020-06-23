package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.DataCollectConfigureModel;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataCollectConfigureModelMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(DataCollectConfigureModel record);

    int insertSelective(DataCollectConfigureModel record);

    DataCollectConfigureModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataCollectConfigureModel record);

    int updateByPrimaryKey(DataCollectConfigureModel record);
    
    List<DataCollectConfigureModel> selectByEnterpriseCode(String enterprisecode);


    DataCollectConfigureModel selectByDataIndex(@Param("dataIndex") String dataIndex);
}