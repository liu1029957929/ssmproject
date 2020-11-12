package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.dao.PermissionMapper;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
