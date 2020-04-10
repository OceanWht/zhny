package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.EmsCompanyModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmsCompanyModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmsCompanyModel record);

    int insertSelective(EmsCompanyModel record);

    EmsCompanyModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmsCompanyModel record);

    int updateByPrimaryKey(EmsCompanyModel record);
}