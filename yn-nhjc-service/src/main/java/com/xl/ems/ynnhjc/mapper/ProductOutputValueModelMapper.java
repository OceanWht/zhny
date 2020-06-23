package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.ProductOutputValueModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductOutputValueModelMapper {
    int deleteByPrimaryKey(ProductOutputValueModel record);

    int insert(ProductOutputValueModel record);

    int insertSelective(ProductOutputValueModel record);

    List<ProductOutputValueModel> selectByPrimaryKey(String enterpriseCode);

    int updateByPrimaryKeySelective(ProductOutputValueModel record);

    int updateByPrimaryKey(ProductOutputValueModel record);
}