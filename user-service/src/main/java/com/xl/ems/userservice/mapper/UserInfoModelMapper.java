package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.UserInfoModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserInfoModel record);

    int insertSelective(UserInfoModel record);

    UserInfoModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserInfoModel record);

    int updateByPrimaryKey(UserInfoModel record);

    UserInfoModel getUserInfoByNP(UserInfoModel userInfoModel);

    int updateUserInfo(UserInfoModel userInfoModel);
}