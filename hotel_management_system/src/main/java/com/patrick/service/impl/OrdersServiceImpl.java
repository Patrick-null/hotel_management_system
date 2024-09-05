package com.patrick.service.impl;

import com.patrick.bean.Guest;
import com.patrick.bean.Orders;
import com.patrick.mapper.GuestMapper;
import com.patrick.mapper.OrdersMapper;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.GuestService;
import com.patrick.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GuestService guestService;
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Boolean insert(Orders orders) {
        //获取orders中的住客信息
        for (Guest guest : orders.getGuests()){
            guestService.insert(guest);
        }
        guestService.insert(orders.getGuest());

        return ordersMapper.insert(orders)==1;
    }

    @Override
    public Boolean delete(Integer oid) {
        //获取gno
        String gno = ordersMapper.selectById(oid).getGno();
        //获取rid
        Integer rid = guestMapper.selectByNo(gno).getRid();
        //根据rid修改房间为已入住
        roomMapper.updateRstate(2,rid);
        //根据gno修改用户为已入住
        guestMapper.updateGstate(1,gno);
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
