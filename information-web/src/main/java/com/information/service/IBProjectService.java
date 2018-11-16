package com.information.service;

import com.github.pagehelper.Page;
import com.information.pojo.BProject;

public interface IBProjectService {
    void insertBproject(BProject insertData);

    void updateBproject(BProject insertData);

    void deleteBprojectById(BProject deleteData);

    Page selectBProject(BProject select, Integer userId);

    BProject selectByKey(BProject select, Integer userId);
}
