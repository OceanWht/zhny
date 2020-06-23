package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.SysApplicationModel;
import com.xl.ems.ynnhjc.model.SysApplicationModelWithBLOBs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SysApplicationModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysApplicationModelWithBLOBs record);

    int insertSelective(SysApplicationModelWithBLOBs record);

    SysApplicationModelWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysApplicationModelWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysApplicationModelWithBLOBs record);

    int updateByPrimaryKey(SysApplicationModel record);

    List<SysApplicationModelWithBLOBs> getAll();

    SysApplicationModelWithBLOBs selectByEC(@Param("enterpriseCode") String enterpriseCode);
}