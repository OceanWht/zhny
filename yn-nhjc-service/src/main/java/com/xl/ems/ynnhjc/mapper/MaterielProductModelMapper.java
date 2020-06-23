package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.MaterielProductModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaterielProductModelMapper {
    int deleteByPrimaryKey(String dataIndex);

    int insert(MaterielProductModel record);

    int insertSelective(MaterielProductModel record);

    MaterielProductModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MaterielProductModel record);

    int updateByPrimaryKey(MaterielProductModel record);

    List<MaterielProductModel> getList(@Param("enterpriseCode")String enterpriseCode);
}