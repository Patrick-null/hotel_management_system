package com.patrick.service;


import com.patrick.bean.Member;

import java.util.List;

public interface MemberService {
    //添加住客
    Boolean insert(Member member);

    //删除住客
    Boolean delete(Integer mid);

    //修改住客
    Boolean update(Member member);

    //查询所有住客
    List<Member> selectAll();


    //根据ID查询住客
    Member selectById(Integer mid);
}
