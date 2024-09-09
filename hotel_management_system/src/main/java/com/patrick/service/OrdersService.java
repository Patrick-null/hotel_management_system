package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Orders;
import com.patrick.excetion.MyException;

import java.util.List;

public interface OrdersService {
    //添加订单
    Boolean insert(Orders orders) throws MyException;

    //删除订单
    Boolean delete(Integer oid);

    //修改订单
    Boolean update(Orders orders) throws MyException;

    //查询所有订单
    PageInfo<Orders> selectAll(Integer pageNum, String flag);
    List<Orders> selectAll();

    //根据订单号查询订单
    Orders selectByOno(Integer ono);


    //根据ID查询订单
    Orders selectById(Integer oid);
}
