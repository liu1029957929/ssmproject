package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DispatcherController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private MemberService memberService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        //清除session域的东西
        session.invalidate();
        return "index";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/main")
    public String main(HttpSession session){
        return "main";
    }


    /*@ResponseBody
    @RequestMapping("/doLoginMember")
    public Object doLoginMember(String loginacct, String userpswd, String type, HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("loginacct",loginacct);
            map1.put("userpswd", MD5Util.getMD5(userpswd));
            map1.put("type",type);

            Member member = memberService.queryMember(map1);
            session.setAttribute("member",member);
            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("登录失败");
        }
        return ajaxResult;
    }*/


    @ResponseBody
    @RequestMapping("/dologin")
    public Object loginToMain(String loginacct, String userpswd, String type, HttpSession session){
        AjaxResult ajaxResult = new AjaxResult();
        try{
            Map<String,Object> map1 = new HashMap<String,Object>();
            map1.put("loginacct",loginacct);
            map1.put("userpswd", MD5Util.getMD5(userpswd));
            map1.put("type",type);

            //判断是会员登录还是管理员登录
            User user = userService.queryUser(map1);
            //将User保存到session域
            session.setAttribute("user",user);

            //通过userid获取roleid然后在获取permissionid来查找根permissionRoot
            Permission permissionRoot = null;
            List<Permission> permissionList = permissionService.queryPermissionRoot(user.getId());
            Map<Integer,Permission> map = new HashMap<>();
            //存储url的集合
            Set<String> url = new HashSet<>();
            //将chilePermission保存到map集合中，key为该permission的id
            for(Permission child:permissionList){
                map.put(child.getId(),child);
                url.add("/"+child.getUrl());
            }

            for(Permission innerPermission :permissionList){
                if(innerPermission.getPid()==null){
                    permissionRoot = innerPermission;
                }else{
                    Permission parent = map.get(innerPermission.getPid());
                    parent.getChildren().add(innerPermission);
                }

            }
            session.setAttribute("url",url);
            session.setAttribute("permissionRoot",permissionRoot);

            ajaxResult.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            ajaxResult.setSuccess(false);
            ajaxResult.setMessage("登录失败");
        }
        return ajaxResult;
    }
}
