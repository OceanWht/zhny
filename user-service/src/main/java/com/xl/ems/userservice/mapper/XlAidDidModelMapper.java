package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlAidDidModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface XlAidDidModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(XlAidDidModel record);

    int insertSelective(XlAidDidModel record);

    XlAidDidModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(XlAidDidModel record);

    int updateByPrimaryKey(XlAidDidModel record);

    List<XlAidDidModel> selectByUid(@Param("uid") Integer uid);

    void bantchInsert(@Param("list") List<XlAidDidModel> xlAidDidModels);

    int bantchDelete(@Param("uid") int uid);

    List<XlAidDidModel> getByUidDataid(HashMap<String,Integer> map);

    List<XlAidDidModel> getByUidDataid2(HashMap<String,Integer> map);

    int updateBanthAidDid(@Param("list")List<XlAidDidModel> xlAidDidModels);

    List<XlAidDidModel> selectByUidDataid(@Param("uid")Integer uid,@Param("dataid")Integer dataid);
}