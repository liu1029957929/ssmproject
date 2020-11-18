package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.Page;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Override
    public Page queryRoleList(Map<String, Object> map) {
        Integer pageno = (Integer) map.get("pageno");
        Integer pagesize = (Integer) map.get("pagesize");
        Page page = new Page(pageno,pagesize);

        //获取的roleList的起始坐标
        Integer startIndex = page.getStartIndex(pageno,pagesize);
        //获取roleList
        List<Role> roleList = roleMapper.queryRoleList(startIndex,pagesize);
        //获取总的角色数目
        Integer totalsize = roleMapper.queryAllRole();
        //封装到page中
        page.setRoleList(roleList);
        page.setTotalsize(totalsize);
        return page;
    }
}
