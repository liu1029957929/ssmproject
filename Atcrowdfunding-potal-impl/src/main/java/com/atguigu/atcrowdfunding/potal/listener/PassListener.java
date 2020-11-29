package com.atguigu.atcrowdfunding.potal.listener;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import com.atguigu.atcrowdfunding.util.ApplicationContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

public class PassListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution delegateExecution) throws Exception {

        //获取memberService和ticketService
        ApplicationContext ioc = ApplicationContextUtils.applicationContext;
        MemberService memberService = ioc.getBean(MemberService.class);
        TicketService ticketService = ioc.getBean(TicketService.class);
        //修改member表的authstatus，1->2
        String memberid = (String) delegateExecution.getVariable("memberid");
        Member member = memberService.queryMemberById2(memberid);
        member.setAuthstatus("2");
        memberService.updateAuthstatus(member);
        //修改ticket表的status,0->1
        ticketService.updateTicketByMemberId(member.getId());
    }
}
