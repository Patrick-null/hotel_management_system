package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;

import java.util.List;

public interface UserService {
    PageInfo<Room> selectAllRoom(Integer pageNum, String flag);

    PageInfo<Orders> selectMyAll(Integer pageNum,String gno);

    boolean updatePwd(Admin userAndpwd) throws MyException;

    //注册账号
    Admin enroll(Admin enroll) throws MyException;
}
