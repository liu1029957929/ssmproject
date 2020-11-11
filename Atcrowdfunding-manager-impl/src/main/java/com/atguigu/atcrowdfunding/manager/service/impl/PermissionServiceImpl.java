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
}
