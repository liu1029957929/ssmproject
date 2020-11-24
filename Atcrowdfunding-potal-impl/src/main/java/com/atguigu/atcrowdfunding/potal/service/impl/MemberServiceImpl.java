package com.atguigu.atcrowdfunding.potal.service.impl;

import com.atguigu.atcrowdfunding.bean.Member;
import com.atguigu.atcrowdfunding.potal.dao.MemberMapper;
import com.atguigu.atcrowdfunding.potal.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Override
    public Member queryMember(Map<String, Object> map) {
        Member member = memberMapper.queryMember(map);
        if(member==null){
            throw new RuntimeException("查询失败");
        }
        return member;
    }

    @Override
    public int doApplicationForAuth(Member member) {
        return memberMapper.doApplicationForAuth(member);
    }

    @Override
    public int updateBasicInfo(Member member) {
        return memberMapper.updateBasicInfo(member) ;
    }
}
