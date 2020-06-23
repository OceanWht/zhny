package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.PlatformBasicdataModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface PlatformBasicdataModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlatformBasicdataModel record);

    int insertSelective(PlatformBasicdataModel record);

    PlatformBasicdataModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlatformBasicdataModel record);

    int updateByPrimaryKey(PlatformBasicdataModel record);

    List<PlatformBasicdataModel> selectByIntemIndex(@Param("itemIndex") String s);

    int insertBanth(@Param("list") List<PlatformBasicdataModel> enterpriseTypeCodes);

    int deleteBanth(@Param("list") List<PlatformBasicdataModel> diff);

    Set<PlatformBasicdataModel> selectByCode(@Param("regioncode") String regioncode,@Param("itemIndex")String itemindex);

    String getCollectRatio(@Param("code") String energytypecode);
}