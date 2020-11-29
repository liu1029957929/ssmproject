package com.atguigu.atcrowdfunding.potal.service;

import com.atguigu.atcrowdfunding.bean.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {
    Member queryMember(Map<String, Object> map);

    int updateAccttype(Member member);

    int updateBasicInfo(Member mem);

    void updateEmail(Member member);

    void updateAuthstatus(Member member);

    Member queryMemberById(String processInstanceId);

    List<Map<String, Object>> queryMemberCert(String memberid);

    Member queryMemberById2(String memberid);
}
