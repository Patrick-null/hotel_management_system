package com.patrick.mapper;

import com.patrick.bean.Member;

import java.util.List;

public interface MemberMapper {
    //添加会员
    int insert(Member member);

    //删除会员
    int delete(Integer mid);
    //修改会员
    int update(Member member);
    //查询所有会员
    List<Member> selectAll();
    //根据id查询会员
    Member selectById(Integer mid);
}
