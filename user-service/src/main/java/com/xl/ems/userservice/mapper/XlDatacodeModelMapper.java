package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlDatacodeModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface XlDatacodeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XlDatacodeModel record);

    int insertSelective(XlDatacodeModel record);

    XlDatacodeModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(XlDatacodeModel record);

    int updateByPrimaryKey(XlDatacodeModel record);

    CopyOnWriteArrayList<XlDatacodeModel> getAllDataCode();

    List<String> getAllDataCode2();
}