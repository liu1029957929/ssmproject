package com.atguigu.atcrowdfunding.manager.service;

import com.atguigu.atcrowdfunding.bean.Permission;

import java.util.List;

public interface PermissionService {

    Permission getRootPermisson();

    List<Permission> getChildrenPermissionBypid(Integer id);

    List<Permission> getAllPermission();

    int insert(Permission permission);

    List<Permission> getPermissionNotInId(Integer id);

    Permission getPermissionByid(Integer id);

    int updataPermission(Permission permission);

    int deletePermission(Integer id);

    List<Integer> selectPermissionIdsbyRoleid(Integer roleid);

    List<Permission> queryPermissionRoot(Integer id);

    List<Permission> queryAllPermission();
}
