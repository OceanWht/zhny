package com.xl.ems.userservice.mapper;

import com.xl.ems.userservice.model.XlUnitCalcGroupModel;
import com.xl.ems.userservice.model.XlUnitCalcGroupModelKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mapper
public interface XlUnitCalcGroupModelMapper {
    int deleteByPrimaryKey(XlUnitCalcGroupModelKey key);

    int insert(XlUnitCalcGroupModel record);

    int insertSelective(XlUnitCalcGroupModel record);

    XlUnitCalcGroupModel selectByPrimaryKey(XlUnitCalcGroupModelKey key);

    int updateByPrimaryKeySelective(XlUnitCalcGroupModel record);

    int updateByPrimaryKey(XlUnitCalcGroupModel record);

    CopyOnWriteArrayList<XlUnitCalcGroupModel> getAll();

    List<String> getGroupIds(@Param("list") List<Integer> uids);

    List<XlUnitCalcGroupModel> selectByUid(@Param("uid") Integer uid);

    /**
     * 根据能源类型获得企业下得输入输出计算组
     * @param uid
     * @param dataid
     * @return
     */
    List<XlUnitCalcGroupModel> selectUnitByUid(@Param("uid") Integer uid,@Param("dataid")String dataid,@Param("ioo")String ioo);
}