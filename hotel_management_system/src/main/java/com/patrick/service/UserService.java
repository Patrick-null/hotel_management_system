package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Enroll;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface UserService {
    PageInfo<Room> selectAllRoom(Integer pageNum, String flag);

    PageInfo<Orders> selectMyAll(Integer pageNum,String gno,String flag);

    boolean updatePwd(Admin userAndpwd) throws MyException;

    //注册账号
    Boolean enroll(Enroll enroll) throws MyException;

    //修改订单
   // Boolean update(@Validated Orders orders) throws MyException;
}
