package com.information.controller;


import com.information.HaltException;
import com.information.ReloginException;
import com.information.TokenHelper;
import com.information.base.Result;
import com.information.dao.VipUserMapper;
import com.information.pojo.VipUser;
import com.information.pojo.VipUserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VipUserController {


    @Autowired
    VipUserMapper vipUserMapper;

    @RequestMapping("login")
    @ResponseBody
    public Object login(@RequestBody VipUser vipUser, HttpServletResponse response) {
        VipUserExample v = new VipUserExample();
        v.createCriteria().andAccountEqualTo(vipUser.getAccount())
                .andPwEqualTo(vipUser.getPw());
        List<VipUser> vipUsers = vipUserMapper.selectByExample(v);
        if (vipUsers.size() == 0) {
            throw new HaltException("账号或密码不正确");
        }
        TokenHelper.putHeaderVipToken(vipUsers.get(0), response);

        Map<String, Object> map = new HashMap<>();
        map.put("a", response.getHeader(TokenHelper.PRO_ACCESS_TOKEN));
        map.put("b", response.getHeader(TokenHelper.PRO_AUTHOR));
        map.put("user", vipUsers.get(0));
        return map;

    }

    @RequestMapping("/userDetails")
    @ResponseBody
    public Object getUserDetails(HttpServletRequest request) {
        Integer integer = TokenHelper.checkToken(request);
        VipUser vipUser = vipUserMapper.selectByPrimaryKey(integer);
        if (vipUser == null) {
            throw new ReloginException("请重新登录");
        }

        vipUser.setPw(null);
        vipUser.setIdCard(null);

        return vipUser;

    }
}
