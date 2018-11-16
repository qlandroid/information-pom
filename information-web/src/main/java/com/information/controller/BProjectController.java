package com.information.controller;


import com.github.pagehelper.Page;
import com.information.HaltException;
import com.information.TokenHelper;
import com.information.base.Result;
import com.information.pojo.BProject;
import com.information.service.IBProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/bproject", method = RequestMethod.POST)
public class BProjectController {


    @Autowired
    IBProjectService bProjectService;


    @RequestMapping("/insert")
    @ResponseBody
    public Object insertBProject(HttpServletRequest request, @RequestBody BProject insertData) {
        Integer userId = TokenHelper.checkToken(request);
        insertData.setUserId(userId);
        insertData.setAuditStatus(null);
        bProjectService.insertBproject(insertData);
        return Result.ok();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object updateBProject(HttpServletRequest request, @RequestBody BProject updateData) {
        Integer userId = TokenHelper.checkToken(request);
        updateData.setUserId(userId);
        updateData.setAuditStatus(null);
        if (updateData.getId() == null) {
            throw new HaltException("信息异常");
        }
        bProjectService.updateBproject(updateData);
        return Result.ok();
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object deleteBproject(HttpServletRequest request, @RequestBody BProject deleteData) {
        Integer userId = TokenHelper.checkToken(request);
        deleteData.setUserId(userId);
        if (deleteData.getId() == null) {
            throw new HaltException("信息异常");
        }
        bProjectService.deleteBprojectById(deleteData);
        return Result.ok();
    }

    @RequestMapping("/select")
    @ResponseBody
    public Object selectBProject(HttpServletRequest request, @RequestBody BProject select) {
        Integer userId = TokenHelper.checkToken(request);
        Page page = bProjectService.selectBProject(select,userId);
        return Result.okPageList(page);
    }

    @RequestMapping("/selectByKey")
    @ResponseBody
    public Object selectByKey(HttpServletRequest request, @RequestBody BProject select) {
        Integer userId = TokenHelper.checkToken(request);
        BProject data = bProjectService.selectByKey(select,userId);
        return Result.ok(data);
    }

}
