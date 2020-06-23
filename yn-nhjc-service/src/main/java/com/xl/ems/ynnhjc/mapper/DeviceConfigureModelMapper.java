package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.DeviceConfigureModel;
import com.xl.ems.ynnhjc.model.DeviceConfigureModelKey;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceConfigureModelMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(DeviceConfigureModel record);

    int insertSelective(DeviceConfigureModel record);

    DeviceConfigureModel selectByPrimaryKey(DeviceConfigureModelKey key);

    int updateByPrimaryKeySelective(DeviceConfigureModel record);

    int updateByPrimaryKey(DeviceConfigureModel record);
    
    List<DeviceConfigureModel> selectByEnterpriseCode(String enterpriseCode);
}