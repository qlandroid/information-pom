package com.information.controller;


import com.google.gson.Gson;
import com.information.HaltException;
import com.information.ReloginException;
import com.information.TokenHelper;
import com.information.dao.VipUserMapper;
import com.information.pojo.VipUser;
import com.information.pojo.VipUserExample;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class VipUserController {
    Logger logger = Logger.getLogger(VipUserController.class);

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

    @RequestMapping("/readXml")
    @ResponseBody
    public Object readXml(HttpServletRequest request) {
        return readCityXml();

    }

    public String readCityXml() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("classpath:district-info.xml");
        try {
            StringBuffer sb = new StringBuffer();
            Document doc = new SAXReader().read(resource.getFile());
            Element root = doc.getRootElement();
            Iterator<Element> it = root.elementIterator();
            List<Map<String, Object>> list = new ArrayList<>();
            while (it.hasNext()) {
                Element ele = it.next();
                Map<String, Object> pro = new HashMap<>();
                list.add(pro);
                String name = ele.attribute("name").getValue();
                String adcode = ele.attribute("adcode").getValue();
                pro.put("name", name);
                pro.put("adcode", adcode);

                List<Map<String, Object>> citys = new ArrayList<>();
                pro.put("child", citys);
                Iterator<Element> it1 = ele.elementIterator();
                while (it1.hasNext()) {
                    Element ele1 = it1.next();
                    Map<String, Object> city = new HashMap<>();
                    city.put("name", ele1.attributeValue("name"));
                    city.put("adcode", ele1.attributeValue("adcode"));
                    citys.add(city);
                    Iterator<Element> iterator = ele1.elementIterator();

                    List<Map<String, Object>> diss = new ArrayList<>();

                    city.put("child", diss);
                    while (iterator.hasNext()) {
                        Element next = iterator.next();
                        Map<String, Object> dis = new HashMap<>();
                        dis.put("name", next.attributeValue("name"));
                        dis.put("adcode", next.attributeValue("adcode"));
                        diss.add(dis);
                    }

                }
            }


            return new Gson().toJson(list);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "null";
    }
}
