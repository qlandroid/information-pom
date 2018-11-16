package com.information.dao;

import com.information.pojo.BProjectType;
import com.information.pojo.BProjectTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BProjectTypeMapper {
    long countByExample(BProjectTypeExample example);

    int deleteByExample(BProjectTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BProjectType record);

    int insertSelective(BProjectType record);

    List<BProjectType> selectByExampleWithRowbounds(BProjectTypeExample example, RowBounds rowBounds);

    List<BProjectType> selectByExample(BProjectTypeExample example);

    BProjectType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BProjectType record, @Param("example") BProjectTypeExample example);

    int updateByExample(@Param("record") BProjectType record, @Param("example") BProjectTypeExample example);

    int updateByPrimaryKeySelective(BProjectType record);

    int updateByPrimaryKey(BProjectType record);
}