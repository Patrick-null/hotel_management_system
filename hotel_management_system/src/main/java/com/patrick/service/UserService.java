package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;

import java.util.List;

public interface UserService {
    PageInfo<Room> selectAllRoom(Integer pageNum, String flag);

    PageInfo<Orders> selectMyAll(Integer pageNum,Integer aid);
}
