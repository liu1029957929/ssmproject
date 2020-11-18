package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    @RequestMapping("index")
    public String index(){
        return "role/index";
    }

    //分页查询
    @ResponseBody
    @RequestMapping("/showPage")
    public Object showPage(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                           @RequestParam(value = "pagesize",defaultValue = "3")Integer pagesize) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("pageno",pageno);
            map.put("pagesize",pagesize);
            Page page = roleService.queryRoleList(map);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }


    //跳转许可分配页面
    @RequestMapping("/toAssignPermission")
    public String assignPermission(){
        return "role/assignPermission";
    }
    //异步查询分配权限数据
    @ResponseBody
    @RequestMapping("/loadDataAsync")
    public Object loadDataAsync(Integer roleid){
            List<Permission> root = new ArrayList<Permission>();
            //获取全部permission
            List<Permission> childrenPermission = permissionService.getAllPermission();
            //查找roleid对应分配了权限的id
            List<Integer> ids = permissionService.selectPermissionIdsbyRoleid(roleid);

            Map<Integer,Permission> map = new HashMap<Integer,Permission>();
            //将全部的permission全部放进map中，key为id
            for(Permission innerPermission:childrenPermission){
                map.put(innerPermission.getId(),innerPermission);
                if(ids.contains(innerPermission.getId())){
                    innerPermission.setChecked(true);
                }
            }

            for(Permission permission:childrenPermission){
                Permission child = permission;
                if(child.getPid()==null){
                    //当前节点为父节点
                    child.setOpen(true);
                    root.add(permission);
                }else{
                    //通过map来找父节点
                    Permission parent = map.get(child.getPid());
                    parent.setOpen(true);
                    parent.getChildren().add(child);
                }
            }

        return root;
    }


    //删除原来已经分配的权限，添加新的权限
    @ResponseBody
    @RequestMapping("/doAssignPermission")
    public Object doAssignPermission(Integer roleid, DataList data) {
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.insertPermission(roleid,data);
            result.setSuccess(true);
            result.setMessage("分配成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("分配失败");
        }
        return result;
    }

}
