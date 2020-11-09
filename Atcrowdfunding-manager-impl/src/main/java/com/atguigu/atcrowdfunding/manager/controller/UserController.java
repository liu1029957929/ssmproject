package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "user/index";
    }

/*    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno",defaultValue ="1") Integer pageno,
                          @RequestParam(value = "pagesize",defaultValue ="10")Integer pagesize){
        AjaxResult result = new AjaxResult();
        try{
            Page page = userService.queryUserList(pageno,pagesize);
            result.setSuccess(true);
            result.setPage(page);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }*/

    //条件查询
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno",defaultValue ="1") Integer pageno,
                          @RequestParam(value = "pagesize",defaultValue ="10")Integer pagesize,
                            String searchCondition ){
        AjaxResult result = new AjaxResult();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("pageno",pageno);
            map.put("pagesize",pagesize);
            map.put("searchCondition",searchCondition);
            Page page = userService.queryUserList(map);
            result.setSuccess(true);
            result.setPage(page);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }
}
