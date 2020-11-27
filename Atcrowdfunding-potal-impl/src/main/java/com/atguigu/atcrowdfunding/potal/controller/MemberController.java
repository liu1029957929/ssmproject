package com.atguigu.atcrowdfunding.potal.controller;

import com.atguigu.atcrowdfunding.bean.*;
import com.atguigu.atcrowdfunding.manager.service.CerttypeService;
import com.atguigu.atcrowdfunding.potal.listener.PassListener;
import com.atguigu.atcrowdfunding.potal.listener.RefuseListener;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.DataList;
import com.atguigu.atcrowdfunding.util.MD5Util;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CerttypeService certtypeService;
    @Autowired
    private ProcessEngine processEngine;

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


    @RequestMapping("/emailConfirm")
    public String emailConfirm() {
        return "member/emailconfirm";
    }


    @RequestMapping("/applyconfirm")
    public String applyconfirm() {
        return "member/applyconfirm";
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
        }else if("uploadfile".equals(ticket.getPstep())){
            return "redirect:/member/emailConfirm.htm";
        }else if("emailConfirm".equals(ticket.getPstep())){
            return "redirect:/member/applyconfirm.htm";
        }else if("finishapply".equals(ticket.getPstep())){
            return "redirect:/member/member.htm";
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
    @RequestMapping("/doUploadfile")
    public Object doUploadfile(HttpSession session, DataList dl){

        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member) session.getAttribute("member");
            //获取本地文件pics路径
            String realPath = session.getServletContext().getRealPath("/pics");
            //将图片存储到本地
            List<MemberCert> memberCertList = dl.getCertImgs();
            for(MemberCert memberCert:memberCertList){
                MultipartFile file = memberCert.getFile();
                String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String fileName= UUID.randomUUID().toString()+exName;
                String Name=realPath+"/apply/"+fileName;
                file.transferTo(new File(Name));

                memberCert.setMemberid(member.getId());
                memberCert.setIconpath(fileName);
            }
            //更新数据库
            certtypeService.insertMemberCert(memberCertList);

            //更新ticket的状态
            Ticket ticket = ticketService.queryTickByMemberid(member);
            ticket.setPstep("uploadfile");
            ticketService.updateTicket(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("提交数据失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/doEmailConfirm")
    public Object doEmailConfirm(HttpSession session,String email){

        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member) session.getAttribute("member");
            //验证邮箱是否已经更改
            if(!member.getEmail().equals(email)){
                member.setEmail(email);
                memberService.updateEmail(member);
            }
            //获取验证码
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<4;i++){
                sb.append(new Random().nextInt(10));
            }

            //启动申请流程实例，发送邮件
            RepositoryService repositoryService = processEngine.getRepositoryService();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("auth").latestVersion().singleResult();
            RuntimeService runtimeService = processEngine.getRuntimeService();
            Map<String,Object> map = new HashMap<>();
            map.put("toEmail",email);
            map.put("authcode",sb.toString());
            map.put("loginacct",member.getLoginacct());
            map.put("passListener",new PassListener());
            map.put("refuseListener",new RefuseListener());
            ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),map);


            //更新ticket的状态
            Ticket ticket = ticketService.queryTickByMemberid(member);
            ticket.setPstep("emailConfirm");
            ticket.setPiid(processInstance.getId());
            ticket.setAuthcode(sb.toString());
            ticketService.updateTicket2(ticket);

            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("加载失败");
        }
        return result;
    }


    @ResponseBody
    @RequestMapping("/finishApply")
    public Object finishApply(HttpSession session,String authcode){

        AjaxResult result = new AjaxResult();
        try {
            Member member = (Member) session.getAttribute("member");

            //查询数据库中的验证码
            Ticket ticket = ticketService.queryTickByMemberid(member);
            //验证验证码是否正确
            if(ticket.getAuthcode().equals(authcode)){
                //完成审核验证码流程
                TaskService taskService = processEngine.getTaskService();
                Task task = taskService.createTaskQuery().processInstanceId(ticket.getPiid())
                        .taskAssignee(member.getLoginacct()).singleResult();
                taskService.complete(task.getId());

                //设置member表的状态
                member.setAuthstatus("1");
                memberService.updateAuthstatus(member);
                //更新ticket的状态
                ticket.setPstep("finishapply");
                ticketService.updateTicket(ticket);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setMessage("验证码错误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询失败");
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
