package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.SysApplicationModelWithBLOBs;
import com.xl.ems.ynnhjc.model.SysWorkingCodeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SysWorkingCodeModelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysWorkingCodeModel record);

    int insertSelective(SysWorkingCodeModel record);

    SysWorkingCodeModel selectByPrimaryKey(Integer id);

    List<SysWorkingCodeModel> getAllWorkingCode();

    int updateByPrimaryKeySelective(SysWorkingCodeModel record);

    int updateByPrimaryKey(SysWorkingCodeModel record);

    SysWorkingCodeModel selectByEnterpriseCode(@Param("enterprisecode") String enterpriseCode);

}