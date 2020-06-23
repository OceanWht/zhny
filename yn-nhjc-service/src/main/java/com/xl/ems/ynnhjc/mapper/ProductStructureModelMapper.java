package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.ProductStructureModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductStructureModelMapper {
    int deleteByPrimaryKey(ProductStructureModel record);

    int insert(ProductStructureModel record);

    int insertSelective(ProductStructureModel record);

    List<ProductStructureModel> selectByPrimaryKey(String enterpriseCode);

    int updateByPrimaryKeySelective(ProductStructureModel record);

    int updateByPrimaryKey(ProductStructureModel record);
}