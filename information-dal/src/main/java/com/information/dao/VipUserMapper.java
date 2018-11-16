package com.information.dao;

import com.information.pojo.VipUser;
import com.information.pojo.VipUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface VipUserMapper {
    long countByExample(VipUserExample example);

    int deleteByExample(VipUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VipUser record);

    int insertSelective(VipUser record);

    List<VipUser> selectByExampleWithRowbounds(VipUserExample example, RowBounds rowBounds);

    List<VipUser> selectByExample(VipUserExample example);

    VipUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VipUser record, @Param("example") VipUserExample example);

    int updateByExample(@Param("record") VipUser record, @Param("example") VipUserExample example);

    int updateByPrimaryKeySelective(VipUser record);

    int updateByPrimaryKey(VipUser record);
}