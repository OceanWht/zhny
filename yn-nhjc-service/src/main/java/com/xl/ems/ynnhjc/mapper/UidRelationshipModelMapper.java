package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.UidRelationshipModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UidRelationshipModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UidRelationshipModel record);

    int insertSelective(UidRelationshipModel record);

    UidRelationshipModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UidRelationshipModel record);

    int updateByPrimaryKey(UidRelationshipModel record);

    UidRelationshipModel selectByUID(@Param("uid") String uid);

    List<UidRelationshipModel> getAll();
}