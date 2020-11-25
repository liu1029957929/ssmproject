package com.atguigu.atcrowdfunding.potal.dao;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.bean.Ticket;

public interface TicketMapper {

    int deleteByPrimaryKey(Integer id);

    Ticket selectByPrimaryKey(Integer id);

    Ticket queryTickByMemberid(Member member);

    void insertTicket(Ticket ticket);

    void updateTicket(Ticket ticket);
}