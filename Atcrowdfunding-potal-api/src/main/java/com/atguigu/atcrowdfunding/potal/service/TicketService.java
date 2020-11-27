package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface TicketService {
    Ticket queryTickByMemberid(Member member);

    void insertTicket(Ticket ticket);

    void updateTicket(Ticket ticket);

    void updateTicket2(Ticket ticket);
}
