package com.atguigu.atcrowdfunding.controller;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class DispatcherController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/dologin")
    @ResponseBody
    public Object dologin(String loginacct, String userpswd, String type, HttpSession session){
        AjaxResult result = new AjaxResult();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("loginacct",loginacct);
            map.put("userpswd", MD5Util.getMD5(userpswd));
            map.put("type",type);
            User user = userService.queryUser(map);
            //将User保存到session域
            session.setAttribute("user",user);
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("登录失败");
        }
        return result;
    }
}
