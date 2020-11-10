package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "user/index";
    }

    //同步请求，跳转到添加页面
    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    //添加分配角色
    @ResponseBody
    @RequestMapping("/doAssignRole")
    public Object doAssignRole(Integer userid, DataList data) {
        AjaxResult result = new AjaxResult();
        try {
            userService.saveUserRoleRelationship(userid,data);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("分配角色失败");
        }
        return result;
    }

    //删除分配角色
    @ResponseBody
    @RequestMapping("/doUnAssignRole")
    public Object doUnAssignRole(Integer userid, DataList data) {
        AjaxResult result = new AjaxResult();
        try {
            userService.deleteUserRoleRelationship(userid,data);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("取消分配角色失败");
        }
        return result;
    }

    //查询分配角色列表
    @RequestMapping("/assignrole")
    public String assignrole(Integer id,HttpServletRequest request) {
        //查询总的role列表
        List<Role> roleList= userService.queryAllRole();
        //查询useid对应的roleid
        List<Integer> roleIdList = userService.queryRoleId(id);

        List<Role> rightRoleList = new ArrayList<>();
        List<Role> leftRoleList = new ArrayList<>();

        for(Role role:roleList){
            if(roleIdList.contains(role.getId())){
                //划分到已经分配角色列表
                rightRoleList.add(role);
            }else{
                //划分到未分配角色列表
                leftRoleList.add(role);
            }
        }
        //添加到request域
        request.setAttribute("rightRoleList",rightRoleList);
        request.setAttribute("leftRoleList",leftRoleList);
        return "user/assignrole";
    }

    //条件查询
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize", defaultValue = "10") Integer pagesize,
                          String searchCondition) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageno", pageno);
            map.put("pagesize", pagesize);
            map.put("searchCondition", searchCondition);
            Page page = userService.queryUserList(map);
            result.setSuccess(true);
            result.setPage(page);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }

    //添加用户
    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(User user) {
        AjaxResult result = new AjaxResult();
        try {
            user.setCreatetime(DateTimeUtil.getSystime());
            user.setUserpswd(MD5Util.getMD5("123"));
            int count = userService.saveUser(user);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加失败");
        }
        return result;
    }

    //删除用户（单条删除）
    @ResponseBody
    @RequestMapping("/doDelete")
    public Object doDelete(Integer id) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUser(id);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }


    //删除用户（删除多条）
    @ResponseBody
    @RequestMapping("/doDeleteBatch")
    public Object doDeleteBatch(@Param("userList") DataList dataList) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.deleteUserBatch(dataList);
            result.setSuccess(count == dataList.getUserlist().size());
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }

    //同步请求，到修改页面，铺数据
    @RequestMapping("/toEdit")
    public String toEdit(Integer id, HttpServletRequest request) {
        User user = userService.queryUserById(id);
        request.setAttribute("editUser", user);
        System.out.println(user);
        return "user/edit";
    }


    //异步请求，修改页面
    @ResponseBody
    @RequestMapping("/doEdit")
    public Object doEdit(User user) {
        AjaxResult result = new AjaxResult();
        try {
            int count = userService.editUser(user);
            result.setSuccess(count == 1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("修改失败");
        }
        return result;
    }

}

