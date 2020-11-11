package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {

    Permission getRootPermisson();

    List<Permission> getChildrenPermissionBypid(Integer id);

    List<Permission> getAllPermission();
}
