package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import com.patrick.bean.Orders;
import com.patrick.excetion.MyException;
import com.patrick.mapper.GuestMapper;
import com.patrick.mapper.OrdersMapper;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service

public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    //添加住客
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert( Guest guest) {
        roomMapper.updateRstate(1,guest.getRid());
        return guestMapper.insert(guest)==1;
    }

    //删除住客
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer gid) {
        //将房间设为空闲
        roomMapper.updateRstate(0,selectById(gid).getRid());
        return guestMapper.delete(gid)==1;
    }

    //修改住客
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(@Validated Guest guest) {

        Integer rid = guestMapper.selectByNo(guest.getGno()).getRid();

        //将本来房间变成空闲
        roomMapper.updateRstate(0,rid);

        //删除该住户
        guestMapper.delete2(guest.getGid());
        System.out.println(guestMapper.selectByRid(guest.getGid()));
        System.out.println(123123);
        //添加该住户
        guestMapper.insert(guest);
        //将新房间设为已预订
        roomMapper.updateRstate(1, guest.getRid());
        //修改订单中的房间修改
        if(guest.getOno() != null){

            String ono = guest.getOno();
            Orders orders = ordersMapper.selectByOno(ono);
            orders.setRid(guest.getRid());
            ordersMapper.delete2(orders.getOid());
            ordersMapper.insert(orders);
        }
        return true;
    }


    //查询所有住客
    @Override
    public PageInfo<Guest> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,8);
        //查询
        List<Guest> guestList = guestMapper.selectAll(flag);
        //创建封装查询结果
        PageInfo<Guest> guestPageInfo = new PageInfo<>(guestList);
        return guestPageInfo;
    }

    //根据姓名查询住客
    @Override
    public List<Guest> selectByName(String gname) {
        return guestMapper.selectByName(gname);
    }

    //根据id查询住客
    @Override
    public Guest selectById(Integer gid) {
        return guestMapper.selectById(gid);
    }
}
