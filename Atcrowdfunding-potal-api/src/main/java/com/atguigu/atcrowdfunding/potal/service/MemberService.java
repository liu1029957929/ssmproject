package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;

import java.util.Map;

public interface MemberService {
    Member queryMember(Map<String, Object> map);

    int updateAccttype(Member member);

    int updateBasicInfo(Member mem);

    void updateEmail(Member member);

    void updateAuthstatus(Member member);

    Member queryMemberById(String processInstanceId);
}
