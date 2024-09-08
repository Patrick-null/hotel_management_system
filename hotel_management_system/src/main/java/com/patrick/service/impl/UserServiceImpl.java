package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Room;
import com.patrick.mapper.UserMapper;
import com.patrick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public PageInfo<Room> selectAllRoom(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Room> roomList = userMapper.selectAll(flag);


        //创建封装查询结果
        PageInfo<Room> roomListPageInfo = new PageInfo<>(roomList);
        return roomListPageInfo;

    }
}
