package com.patrick.service.impl;

import com.patrick.bean.Orders;
import com.patrick.mapper.OrdersMapper;
import com.patrick.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public Boolean insert(Orders orders) {
        return ordersMapper.insert(orders)==1;
    }

    @Override
    public Boolean delete(Integer oid) {
        return ordersMapper.delete(oid)==1;
    }

    @Override
    public Boolean update(Orders orders) {
        return ordersMapper.update(orders)==1;
    }

    @Override
    public List<Orders> selectAll() {
        return ordersMapper.selectAll();
    }

    @Override
    public Orders selectByOno(Integer ono) {
        return ordersMapper.selectByOno(ono);
    }

    @Override
    public Orders selectById(Integer oid) {
        return ordersMapper.selectById(oid);
    }
}
