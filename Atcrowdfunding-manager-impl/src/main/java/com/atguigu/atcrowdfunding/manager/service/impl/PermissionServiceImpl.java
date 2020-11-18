package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionMapper permissionMapper;

    @Override
    public Permission getRootPermisson() {
        return permissionMapper.getRootPermisson();
    }

    @Override
    public List<Permission> getChildrenPermissionBypid(Integer id) {
        return permissionMapper.getChildrenPermissionBypid(id);
    }

    @Override
    public List<Permission> getAllPermission() {
        return permissionMapper.getAllPermission();
    }

    @Override
    public int insert(Permission permission) {
        return permissionMapper.insert(permission);
    }

    @Override
    public List<Permission> getPermissionNotInId(Integer id) {
        return permissionMapper.getPermissionNotInId(id);
    }

    @Override
    public Permission getPermissionByid(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updataPermission(Permission permission) {
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public int deletePermission(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Integer> selectPermissionIdsbyRoleid(Integer roleid) {
        return permissionMapper.selectPermissionIdsbyRoleid(roleid);
    }

    @Override
    public List<Permission> queryPermissionRoot(Integer id) {

        //查找该id下的所有permission
        List<Permission> permissionList = permissionMapper.getPermissionById(id);
        return permissionList;
    }

    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.queryAllPermission();
    }
}
