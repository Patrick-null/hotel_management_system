package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GuestService {
    //添加住客
    Boolean insert(Guest guest);

    //删除住客
    Boolean delete(Integer gid);

    //修改住客
    Boolean update(Guest guest);

    //查询所有住客
    PageInfo<Guest> selectAll();

    //根据姓名查询住客
    List<Guest> selectByName(String gname);

    //根据ID查询住客
    Guest selectById(Integer gid);

    //根据身份证查询
}
