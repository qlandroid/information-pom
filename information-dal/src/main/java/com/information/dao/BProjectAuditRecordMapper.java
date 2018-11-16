package com.information.dao;

import com.information.pojo.BProjectAuditRecord;
import com.information.pojo.BProjectAuditRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BProjectAuditRecordMapper {
    long countByExample(BProjectAuditRecordExample example);

    int deleteByExample(BProjectAuditRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BProjectAuditRecord record);

    int insertSelective(BProjectAuditRecord record);

    List<BProjectAuditRecord> selectByExampleWithRowbounds(BProjectAuditRecordExample example, RowBounds rowBounds);

    List<BProjectAuditRecord> selectByExample(BProjectAuditRecordExample example);

    BProjectAuditRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BProjectAuditRecord record, @Param("example") BProjectAuditRecordExample example);

    int updateByExample(@Param("record") BProjectAuditRecord record, @Param("example") BProjectAuditRecordExample example);

    int updateByPrimaryKeySelective(BProjectAuditRecord record);

    int updateByPrimaryKey(BProjectAuditRecord record);
}