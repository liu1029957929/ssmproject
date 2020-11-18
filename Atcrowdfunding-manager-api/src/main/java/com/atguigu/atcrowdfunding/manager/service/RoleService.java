package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.Page;

import java.util.Map;

public interface RoleService {
    int insertPermission(Integer roleid, DataList data);

    Page queryRoleList(Map<String, Object> map);
}
