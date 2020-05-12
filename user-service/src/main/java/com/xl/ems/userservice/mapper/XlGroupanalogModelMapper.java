package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlGroupanalogModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface XlGroupanalogModelMapper {
    int deleteByPrimaryKey(Integer analogid);

    int insert(XlGroupanalogModel record);

    int insertSelective(XlGroupanalogModel record);

    XlGroupanalogModel selectByPrimaryKey(Integer analogid);

    int updateByPrimaryKeySelective(XlGroupanalogModel record);

    int updateByPrimaryKey(XlGroupanalogModel record);

    CopyOnWriteArrayList<XlGroupanalogModel> selectByGroupIds(List<String> groupIds);

    int deleteByGroupIds(List<String> groupIds);

    void insertBantch(@Param("list") CopyOnWriteArrayList<XlGroupanalogModel> xlGroupanalogModels);

    List<Integer> getAids(@Param("list") List<String> groupIds);

    List<XlGroupanalogModel> getMetersTotal(@Param("uid")Integer uid,@Param("dataid")String dataid);

    int getFenLuMetersTotal(@Param("uid")Integer uid,@Param("dataid")String dataid);

    List<XlGroupanalogModel> getGroupAnalogbyUidDataid(HashMap<String,Integer> map);

    int banthUpdateByUidDataid(@Param("list")List<XlGroupanalogModel> xlGroupanalogModels);

    List<XlGroupanalogModel> selectGroupAnalog(@Param("uid")Integer uid,@Param("dataid")Integer dataid);
}