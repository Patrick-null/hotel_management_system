package com.patrick.service;

import com.patrick.bean.Orders;

import java.util.List;

public interface OrdersService {
    //添加订单
    Boolean insert(Orders orders);

    //删除订单
    Boolean delete(Integer oid);

    //修改订单
    Boolean update(Orders orders);

    //查询所有订单
    List<Orders> selectAll();

    //根据订单号查询订单
    Orders selectByOno(Integer ono);


    //根据ID查询订单
    Orders selectById(Integer oid);
}
