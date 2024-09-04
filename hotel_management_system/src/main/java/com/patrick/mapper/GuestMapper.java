package com.patrick.mapper;

import com.patrick.bean.Guest;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface GuestMapper {
    //添加住客
    int insert(Guest guest);

    //删除住客
    int delete(Integer gid);

    //修改住客
    int update(Guest guest);

    //查询所有住客
    List<Guest> selectAll();

    //根据姓名查询住客
    List<Guest> selectByName(String gname);

    //根据ID查询住客
    Guest selectById(Integer gid);

    //根据身份证查询
}
