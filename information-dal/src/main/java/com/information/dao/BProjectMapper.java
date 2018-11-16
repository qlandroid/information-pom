package com.information.dao;

import com.information.pojo.BProject;
import com.information.pojo.BProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BProjectMapper {
    long countByExample(BProjectExample example);

    int deleteByExample(BProjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BProject record);

    int insertSelective(BProject record);

    List<BProject> selectByExampleWithBLOBsWithRowbounds(BProjectExample example, RowBounds rowBounds);

    List<BProject> selectByExampleWithBLOBs(BProjectExample example);

    List<BProject> selectByExampleWithRowbounds(BProjectExample example, RowBounds rowBounds);

    List<BProject> selectByExample(BProjectExample example);

    BProject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BProject record, @Param("example") BProjectExample example);

    int updateByExampleWithBLOBs(@Param("record") BProject record, @Param("example") BProjectExample example);

    int updateByExample(@Param("record") BProject record, @Param("example") BProjectExample example);

    int updateByPrimaryKeySelective(BProject record);

    int updateByPrimaryKeyWithBLOBs(BProject record);

    int updateByPrimaryKey(BProject record);

    List<BProject> selectPageNotVip(BProject bProject);
    List<BProject> selectPageVip(BProject bProject);
}