package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advertisement;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController {
    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index(){
        return "advert/index";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "advert/add";
    }

    @ResponseBody
    @RequestMapping("/showPage")
    public Object showPage(@RequestParam(value = "pageno",defaultValue = "1") Integer pageno,
                           @RequestParam(value = "pagesize",defaultValue = "3")Integer pagesize) {
        AjaxResult result = new AjaxResult();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("pageno",pageno);
            map.put("pagesize",pagesize);
            Page page = advertService.queryAdvertList(map);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }

    //添加操作
    @ResponseBody
    @RequestMapping("/add")
    public Object add(HttpServletRequest request, Advertisement advert, HttpSession session) {
        AjaxResult result = new AjaxResult();
        try {
            //获取图片
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            MultipartFile file = mreq.getFile("advpic");
            //修改图片的呢称
            String orgName = file.getOriginalFilename();
            String suffixName = orgName.substring(orgName.lastIndexOf("."));
            String name = UUID.randomUUID().toString()+suffixName;
            //修改图片的保存位置
            String realPath = session.getServletContext().getRealPath("/pics");
            String path = realPath+"\\advert\\"+name;
            file.transferTo(new File(path));

            //添加操作
            advert.setIconpath(name);
            advert.setStatus("1");
            User user = (User) session.getAttribute("user");
            advert.setUserid(user.getId());
            int count = advertService.insert(advert);
            result.setSuccess(count==1);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("添加失败");
        }
        return result;
    }
}
