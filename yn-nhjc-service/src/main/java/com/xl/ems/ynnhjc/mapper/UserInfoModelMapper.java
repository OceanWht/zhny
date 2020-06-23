package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.UserInfoModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoModel record);

    int insertSelective(UserInfoModel record);

    UserInfoModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoModel record);

    int updateByPrimaryKey(UserInfoModel record);

    UserInfoModel selectByUserId(@Param("userid") String userid);

    String getToken(@Param("enterpriseCode")String code);
}