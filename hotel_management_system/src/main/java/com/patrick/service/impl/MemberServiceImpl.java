package com.patrick.service.impl;

import com.patrick.bean.Member;
import com.patrick.mapper.MemberMapper;
import com.patrick.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Boolean insert(Member member) {
        return memberMapper.insert(member)==1;
    }

    @Override
    public Boolean delete(Integer mid) {
        return memberMapper.delete(mid)==1;
    }

    @Override
    public Boolean update(Member member) {
        return memberMapper.update(member)==1;
    }

    @Override
    public List<Member> selectAll() {
        return memberMapper.selectAll();
    }

    @Override
    public Member selectById(Integer mid) {
        return memberMapper.selectById(mid);
    }
}
