package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;
import com.atguigu.atcrowdfunding.potal.dao.TicketMapper;
import com.atguigu.atcrowdfunding.potal.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TicketServiceImpl implements TicketService {
    @Resource
    private TicketMapper ticketMapper;

    @Override
    public Ticket queryTickByMemberid(Member member) {
        return ticketMapper.queryTickByMemberid(member);
    }

    @Override
    public void insertTicket(Ticket ticket) {
        ticketMapper.insertTicket(ticket);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        ticketMapper.updateTicket(ticket);
    }

    @Override
    public void updateTicket2(Ticket ticket) {
        ticketMapper.updateTicket2(ticket);
    }
}
