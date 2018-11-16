package com.information.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.information.HaltException;
import com.information.ReloginException;
import com.information.dao.BProjectMapper;
import com.information.dao.VipUserMapper;
import com.information.pojo.BProject;
import com.information.pojo.VipUser;
import com.information.service.IBProjectService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("bProjectService")
public class BProjectServiceImpl implements IBProjectService {

    @Autowired
    BProjectMapper bProjectMapper;
    @Autowired
    VipUserMapper userMapper;

    @Override
    public void insertBproject(BProject insertData) {
        checkBProjectData(insertData);

        insertData.setCreateDate(new Date());
        insertData.setChangeDate(new Date());
        bProjectMapper.insertSelective(insertData);

    }

    @Override
    public void updateBproject(BProject insertData) {
        checkBProjectData(insertData);
        insertData.setChangeDate(new Date());
        bProjectMapper.updateByPrimaryKey(insertData);
    }

    private void checkBProjectData(BProject insertData) {
        VipUser user = userMapper.selectByPrimaryKey(insertData.getId());
        if (user == null) {
            throw new ReloginException("请重新登录");
        }

        String details = insertData.getDetails();
        if (StringUtils.isNullOrEmpty(details)) {
            throw new HaltException("请填写详细信息");
        }
        if (StringUtils.isNullOrEmpty(insertData.getProvince())
                || StringUtils.isNullOrEmpty(insertData.getCity())
                || StringUtils.isNullOrEmpty(insertData.getDistrict())) {
            throw new HaltException("必须填写省市区");
        }

        if (StringUtils.isNullOrEmpty(insertData.getTitle())) {
            throw new HaltException("必须填写标题");
        }
        if (StringUtils.isNullOrEmpty(insertData.getContactsUser())) {
            throw new HaltException("请填写联系人姓名");
        }
        if (StringUtils.isNullOrEmpty(insertData.getContactsMobile())) {
            throw new HaltException("请填写联系人手机号");
        }
    }

    @Override
    public void deleteBprojectById(BProject deleteData) {
        VipUser user = userMapper.selectByPrimaryKey(deleteData.getId());
        if (user == null) {
            throw new ReloginException("请重新登录");
        }
        bProjectMapper.deleteByPrimaryKey(deleteData.getId());
    }

    @Override
    public Page selectBProject(BProject select, Integer userId) {

        VipUser user = userMapper.selectByPrimaryKey(userId);
        Date vipEndDate = user.getVipEndDate();


        Page page = PageHelper.startPage(select.getPageNum(), select.getPageSize());
        if (vipEndDate != null && vipEndDate.before(new Date())) {
            //现在是vip状态
            bProjectMapper.selectPageVip(select);
        } else {
            bProjectMapper.selectPageNotVip(select);
        }

        return page;
    }

    @Override
    public BProject selectByKey(BProject select, Integer userId) {
        VipUser user = userMapper.selectByPrimaryKey(userId);
        Date vipEndDate = user.getVipEndDate();

        BProject bProject = bProjectMapper.selectByPrimaryKey(select.getId());
        if (bProject == null) {
            throw new HaltException("未找到信息");
        }
        if (vipEndDate == null || new Date().after(vipEndDate)) {
            //非会员状态
            bProject.setUserId(null);
            bProject.setAuditStatus(null);
            bProject.setSendStatus(null);
            bProject.setContactsMobile(null);
            bProject.setContactsUser(null);
        }

        return bProject;
    }
}
