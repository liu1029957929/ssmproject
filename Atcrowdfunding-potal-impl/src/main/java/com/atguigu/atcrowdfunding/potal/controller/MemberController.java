package com.atguigu.atcrowdfunding.potal.controller;

import com.atguigu.atcrowdfunding.bean.AccountTypeCert;
import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CerttypeService certtypeService;

    @RequestMapping("/member")
    public String member() {
        return "member/member";
    }

    @RequestMapping("/accttype")
    public String accttype() {
        return "member/accttype";
    }

    @RequestMapping("/basicInfo")
    public String basicinfo() {
        return "member/basicinfo";
    }


    @RequestMapping("/uploadfile")
    public String uploadfile(HttpSession session) {
        Member member = (Member) session.getAttribute("member");
        List<AccountTypeCert> accountTypeCertList = certtypeService.queryAccountTypeCertByAccttype(member.getAccttype());
        List<Integer> certid = new ArrayList<>();
        for(AccountTypeCert ac:accountTypeCertList){
            certid.add(ac.getCertid());
        }
        List<Cert> cert = certtypeService.queryCertByid(certid);
        session.setAttribute("cert",cert);
        return "member/uploadfile";
    }

    @RequestMapping("/apply")
    public String apply(HttpSession session) {
        //进行跳转控制
        Member member = (Member) session.getAttribute("member");

        Ticket ticket = ticketService.queryTickByMemberid(member);
        if(ticket==null){
            //刚刚开始流程
            ticket = new Ticket();
            ticket.setMemberid(member.getId());
            ticket.setStatus("0");
            ticket.setPstep("apply");
            ticketService.insertTicket(ticket);
        }else if("accttype".equals(ticket.getPstep())){
            return "redirect:/member/basicInfo.htm";
        }else if("basicinfo".equals(ticket.getPstep())){
            return "redirect:/member/uploadfile.htm";
        }

        return "member/accttype";
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

            //更新ticket的状态
            Ticket ticket = ticketService.queryTickByMemberid(mem);
            ticket.setPstep("basicinfo");
            ticketService.updateTicket(ticket);

            result.setSuccess(count==1);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("更新资料失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/updateAccttype")
    public Object doApplicationForAuth(String acctype,HttpSession session){

        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member) session.getAttribute("member");
            member.setAccttype(acctype);
            int count = memberService.updateAccttype(member);

            //更新ticket的状态
            Ticket ticket = ticketService.queryTickByMemberid(member);
            ticket.setPstep("accttype");
            ticketService.updateTicket(ticket);

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
