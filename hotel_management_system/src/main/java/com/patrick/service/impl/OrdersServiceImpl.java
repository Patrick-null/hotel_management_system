package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.mapper.GuestMapper;
import com.patrick.mapper.OrdersMapper;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.GuestService;
import com.patrick.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
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
        //记录有多少人
        int count=0;
        //获取orders中的住客信息
        for (Guest guest : orders.getGuests()){
            guestService.insert(guest);
            count++;
        }
        guestService.insert(orders.getGuest());
        count++;
        //获取房间金额
            //获取房间
        Integer rid = orders.getGuest().getRid();
        Room room = roomMapper.selectById(rid);
        BigDecimal rprice = room.getRprice();
        BigDecimal moneys = rprice;

        //获取时间
        Date gend = orders.getGuest().getGend();
        Date gstart = orders.getGuest().getGstart();
        LocalDate ldg = gstart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lde = gend.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        long day = ChronoUnit.DAYS.between(ldg, lde);
        System.out.println(new BigDecimal(day).multiply(moneys));

        orders.setMoneys(new BigDecimal(day).multiply(moneys));
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
    public Boolean update(Orders orders) throws MyException {
        if(ordersMapper.selectById(orders.getOid()).getOstate()==1){
            throw new MyException("订单已完成不能修改");
        }
        return ordersMapper.update(orders)==1;
    }

    @Override
    public PageInfo<Orders> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,8);
        //查询
        List<Orders> ordersList = ordersMapper.selectAll(flag);
        //创建封装查询结果
        PageInfo<Orders> ordersListPageInfo = new PageInfo<>(ordersList);

        return ordersListPageInfo;
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
