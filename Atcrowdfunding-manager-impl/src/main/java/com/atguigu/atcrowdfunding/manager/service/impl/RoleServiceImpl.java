package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.DataList;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;

    @Override
    public int insertPermission(Integer roleid, DataList data) {
        //删除原来已经存在的权限
        roleMapper.deleteByPrimaryKey(roleid);
        //添加权限
        return roleMapper.insertPermission(roleid,data);
    }
}
