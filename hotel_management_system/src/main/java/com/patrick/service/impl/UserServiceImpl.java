package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.mapper.AdminMapper;
import com.patrick.mapper.UserMapper;
import com.patrick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
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

    @Override
    public PageInfo<Orders> selectMyAll(Integer pageNum,String gno) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Orders> ordersList = userMapper.selectMyAll(gno);

        System.out.println("1231231231");
        ordersList.stream().forEach(System.out::println);
        //创建封装查询结果
        PageInfo<Orders> ordersListPageInfo = new PageInfo<>(ordersList);
        return ordersListPageInfo;
    }

    @Override
    public boolean updatePwd(Admin userAndpwd) throws MyException {
        if(adminMapper.loginTwo(userAndpwd.getUsername())==null) {
            throw new MyException("没有找到该用户名");
        }
        return adminMapper.updatePwd(userAndpwd.getUsername(),userAndpwd.getPassword())==1;
    }
}
