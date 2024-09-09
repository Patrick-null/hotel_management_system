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
import org.springframework.transaction.annotation.Transactional;

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
    public PageInfo<Orders> selectMyAll(Integer pageNum,String gno,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Orders> ordersList = userMapper.selectMyAll(gno,flag);

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Admin enroll(Admin enroll) throws MyException {
        if(adminMapper.loginTwo(enroll.getUsername()) != null){
            throw new MyException("用户名已存在，请重新输入");
        }
        if(adminMapper.insert(enroll.getUsername(),enroll.getPassword())==1){

            Integer aid = adminMapper.loginTwo(enroll.getUsername()).getAid();
            enroll.setAid(aid);
            return enroll;
        }
        return null;
    }
}
