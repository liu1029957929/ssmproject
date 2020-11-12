package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "permission/index";
    }


    //跳转到修改页面
    @RequestMapping("/toEdit")
    public String toEdit(Integer id,HttpServletRequest request){

        List<Permission> permissionList = permissionService.getPermissionNotInId(id);
        Permission permission = permissionService.getPermissionByid(id);

        request.setAttribute("permissionList",permissionList);
        //将permissionList封装到Request中
        request.setAttribute("permission",permission);
        return "permission/edit";
    }


    //进行修改操作
    @ResponseBody
    @RequestMapping("/edit")
    public Object edit(Permission permission){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.updataPermission(permission);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    //进行删除操作

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(Integer id){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.deletePermission(id);
            result.setSuccess(count==1);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }


    //跳转到add页面，获取icon数据，铺页面
    @RequestMapping("/toAdd")
    public String toAdd(HttpServletRequest request){
        List<Permission> permissionList = permissionService.getAllPermission();
        //将permissionList封装到Request中
        request.setAttribute("permissionList",permissionList);
        return "permission/add";
    }

    //进行添加操作
    @ResponseBody
    @RequestMapping("/add")
    public Object add(Permission permission){
        AjaxResult result = new AjaxResult();
        try{
            int count = permissionService.insert(permission);
            result.setSuccess(count==1);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    //Demo5-关于demo4的优化
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<Permission>();
            //获取全部permission
            List<Permission> childrenPermission = permissionService.getAllPermission();
            Map<Integer,Permission> map = new HashMap<Integer,Permission>();
            //将全部的permission全部放进map中，key为id
            for(Permission innerPermission:childrenPermission){
                map.put(innerPermission.getId(),innerPermission);
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

            result.setData(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }


    //Demo4-一次加载全部数据
/*    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<Permission>();
            //获取全部permission
            List<Permission> childrenPermission = permissionService.getAllPermission();
            for(Permission permission:childrenPermission){
                Permission child = permission;
                if(child.getPid()==null){
                    //当前节点为父节点
                    child.setOpen(true);
                    root.add(child);
                }else{
                    //当前节点不是父节点
                    //遍历全部permission，通过判断该permission的id与当前外层循环permission的pid是否一致，如一致，则该permission为父节点
                    for(Permission innerPermission :childrenPermission){
                        if(innerPermission.getId()==child.getPid()){
                            //innerPermission为child的父节点
                            innerPermission.setOpen(true);
                            innerPermission.getChildren().add(child);
                            break;
                        }
                    }
                }
            }

            result.setData(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }*/


    //Demo3-利用递归
/*    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<Permission>();
            //父节点
            Permission permission = permissionService.getRootPermisson();
            permission.setOpen(true);
            root.add(permission);

            //利用递归找子节点
            queryChildrenPermission(permission);


            result.setData(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }

    private void queryChildrenPermission(Permission permission) {
        List<Permission> children = permissionService.getChildrenPermissionBypid(permission.getId());
        permission.setChildren(children);

        for(Permission child:children){
            child.setOpen(true);
            queryChildrenPermission(child);
        }
    }*/


    //Demo2-从数据库t_permisson查询数据显示许可树
/*    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<Permission>();
            //父节点
            Permission permission = permissionService.getRootPermisson();
            permission.setOpen(true);
            root.add(permission);

            //子节点
            List<Permission> children = permissionService.getChildrenPermissionBypid(permission.getId());
            permission.setChildren(children);

            //对子节点进行遍历
            for(Permission child:children){
                child.setOpen(true);
                List<Permission> innerchildren = permissionService.getChildrenPermissionBypid(child.getId());
                child.setChildren(innerchildren);
            }

            result.setData(root);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        return result;
    }*/



    //Demo1-模拟数据生成树
    /*@ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        AjaxResult result = new AjaxResult();
        try{
            List<Permission> root = new ArrayList<Permission>();
            //父节点
            Permission permission = new Permission();
            permission.setName("父节点");
            permission.setOpen(true);
            List<Permission> children = new ArrayList<>();
            permission.setChildren(children);

            root.add(permission);

            //子节点
            Permission permission1 = new Permission();
            Permission permission2 = new Permission();
            permission1.setName("子节点1");
            permission2.setName("子节点2");

            children.add(permission1);
            children.add(permission2);

            result.setData(permission);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setMessage(e.getMessage());
            result.setSuccess(false);
        }

        *//*
        {"success":true,"message":null,"page":null,
        "data":[{"id":null,"pid":null,"name":"父节点","icon":null,"url":null,"open":true,
        "children":[{"id":null,"pid":null,"name":"子节点1","icon":null,"url":null,"open":false,"children":null},
        {"id":null,"pid":null,"name":"子节点2","icon":null,"url":null,"open":false,"children":null}]}]}
         *//*
        return result;
    }*/
}
