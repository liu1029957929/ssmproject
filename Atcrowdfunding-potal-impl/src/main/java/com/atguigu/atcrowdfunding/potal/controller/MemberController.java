package com.atguigu.atcrowdfunding.potal.controller;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @RequestMapping("/member")
    public String member() {
        return "member/member";
    }

    @RequestMapping("/accttype")
    public String accttype() {
        return "member/accttype";
    }


    @RequestMapping("/apply")
    public String apply() {
        return "member/apply";
    }


    @ResponseBody
    @RequestMapping("/updateBasicInfo")
    public Object updateBasicInfo(Member member,HttpSession session){

        AjaxResult result = new AjaxResult();
        try {
            Member mem = (Member) session.getAttribute("member");
            mem.setRealname(member.getRealname());
            mem.setCardnum(member.getCardnum());
            mem.setTel(member.getTel());
            int count = memberService.updateBasicInfo(mem);
            result.setSuccess(count==1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("更新资料失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doApplicationForAuth")
    public Object doApplicationForAuth(String acctype,HttpSession session){

        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member) session.getAttribute("member");
            member.setAccttype(acctype);
            int count = memberService.doApplicationForAuth(member);
            result.setSuccess(count==1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("申请失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    public Object doLogin(HttpSession session){

        AjaxResult result = new AjaxResult();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("loginacct", "zhangsan");
            map.put("userpswd", MD5Util.getMD5("123"));
            Member member = memberService.queryMember(map);
            session.setAttribute("member", member);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("跳转失败");
        }
        return result;
    }

}
