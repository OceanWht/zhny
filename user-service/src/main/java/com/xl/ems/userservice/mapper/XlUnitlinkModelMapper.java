package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlUnitlinkModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface XlUnitlinkModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XlUnitlinkModel record);

    int insertSelective(XlUnitlinkModel record);

    XlUnitlinkModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(XlUnitlinkModel record);

    int updateByPrimaryKey(XlUnitlinkModel record);

    List<XlUnitlinkModel> getAll();

    List<XlUnitlinkModel> getByUids(@Param("uid") String uid);
    List<Integer> getUidsByUid(@Param("uid") Integer uid);

    int deleteByList(@Param("list") List<Integer> uids);

    int banthUpdate(@Param("list")List<XlUnitlinkModel> xlUnitlinkModels);

    List<XlUnitlinkModel> selectUnitLink(@Param("uid")Integer uid,@Param("dataid")String dataid);
}