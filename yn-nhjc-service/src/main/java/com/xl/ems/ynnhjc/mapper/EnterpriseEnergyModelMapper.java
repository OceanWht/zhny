package com.xl.ems.ynnhjc.mapper;

import com.xl.ems.ynnhjc.model.EnterpriseEnergyModel;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EnterpriseEnergyModelMapper {
    int deleteByPrimaryKey(String dataindex);

    int insert(EnterpriseEnergyModel record);

    int insertSelective(EnterpriseEnergyModel record);

    EnterpriseEnergyModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EnterpriseEnergyModel record);

    int updateByPrimaryKey(EnterpriseEnergyModel record);
    
    List<EnterpriseEnergyModel> selectByEnterpriseCode(String enterpriseCode);

    EnterpriseEnergyModel selectByBackupField2(@Param("backupField2") String dataIndex);

    int deleteByBackupField2(@Param("backupField2") String dataindex);

    List<EnterpriseEnergyModel> getList(@Param("enterpriseCode") String s);

    /**
     * 获取准备上传的数据
     * @return
     */
    List<EnterpriseEnergyModel> getReadyUploadList();
}