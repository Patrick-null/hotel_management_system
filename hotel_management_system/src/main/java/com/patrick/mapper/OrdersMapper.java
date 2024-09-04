package com.patrick.mapper;

import com.patrick.bean.Orders;
import com.patrick.bean.Room;

import java.util.List;

public interface OrdersMapper {

    //添加订单
    int insert(Orders orders);

    //删除订单
    int delete(Integer oid);

    //修改订单
    int update(Orders orders);

    //查询所有订单
    List<Orders> selectAll();

    //根据订单号查询订单
    Orders selectByOno(Integer ono);


    //根据ID查询订单
    Orders selectById(Integer oid);
}
