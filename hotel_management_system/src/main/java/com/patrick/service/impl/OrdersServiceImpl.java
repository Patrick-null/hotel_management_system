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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

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
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(@Validated Orders orders) throws MyException {
        //获取orders中的住客信息
        for (Guest guest : orders.getGuests()){
            guest.setOno(orders.getOno());
            guestService.insert(guest);
        }
        if(orders.getGuest().getGname()==null||
                orders.getGuest().getGno()==null||
                orders.getGuest().getGgender()==null||
                orders.getGuest().getGphone()==null||
                orders.getGuest().getRid()==null){
            throw new MyException("请填写完整数据");
        }
        //guestService.insert(orders.getGuest());
        //获取房间金额
            //获取房间
        Integer rid = orders.getGuest().getRid();
        Room room = roomMapper.selectById(rid);
        BigDecimal rprice = room.getRprice();
        BigDecimal moneys = rprice;

        //获取时间
        Date gend = orders.getGuest().getGend();
        Date gstart = orders.getGuest().getGstart();
        LocalDate start = gstart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = gend.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        long day = ChronoUnit.DAYS.between(start, end);
        System.out.println(new BigDecimal(day).multiply(moneys));

        orders.setMoneys(new BigDecimal(day).multiply(moneys));
        orders.setRid(room.getRid());
        return ordersMapper.insert(orders)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer oid) {
        //获取住客ono

        String ono = ordersMapper.selectById(oid).getOno();
        //System.out.println(ono);
        ////获取rid
        //System.out.println("ridridridrid");
        Integer rid1 = ordersMapper.selectById(oid).getRid();
        //Integer rid = guestMapper.selectByNo(ono).getRid();
        //根据rid修改房间为已入住
        roomMapper.updateRstate(2,rid1);
        //根据gno修改用户为已入住
        guestMapper.updateGstate(1,ono);
        return ordersMapper.delete(oid)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(@Validated Orders orders) throws MyException {
        if(ordersMapper.selectById(orders.getOid()).getOstate()==1){
            throw new MyException("订单已完成，无法修改");
        }

        //获取到之前的房间类型

        //修改订单
        //修改订单中原来的房间为0
        //获取之前房间的id
        Guest[] guests = guestMapper.selectByOno(orders.getOno());

        //Integer rnum = roomMapper.selectById(orders.getRid()).getRnum();
        Integer rnum = roomMapper.selectById(orders.getRid()).getRnum();
        System.out.println("--------------------");
        System.out.println(orders.getGuest().getRid());
        System.out.println("房间人数"+rnum);
        System.out.println("客人人数"+guests.length);
        if(guests.length>rnum){
            throw new MyException("订单人数大于房间人数，无法修改");
        }

        if(guests.length>0){
            Integer rid = guests[0].getRid();
            for (int i = 0; i < guests.length; i++) {
                guestMapper.delete2(guests[i].getGid());
                guests[i].setRid(orders.getRid());
                guestMapper.insert(guests[i]);
            }
            roomMapper.updateRstate(0,rid);
            BigDecimal rprice = roomMapper.selectById(orders.getRid()).getRprice();

            //获取时间
            Date gstart = guests[0].getGstart();
            Date gend = guests[0].getGend();
            LocalDate start = gstart.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = gend.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


            long day = ChronoUnit.DAYS.between(start, end);


            orders.setMoneys(new BigDecimal(day).multiply(rprice));

            roomMapper.updateRstate(1,orders.getRid());
        }else {

            throw new MyException("修改失败");
        }
        //修改原来房间中的住客为0

        //修改订单中后来的房间为1
            //修改后来房间的住客为1
        //删除原来的订单
        ordersMapper.delete2(orders.getOid());

        System.out.println("00000000000000000");
        System.out.println(orders);
        //新增现在的订单
        return ordersMapper.insert(orders)==1;
    }

    @Override
    public PageInfo<Orders> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Orders> ordersList = ordersMapper.selectAll(flag);
        for (Orders orders : ordersList) {
            Date date = new Date();
            if(orders.getOend().before(date)){
                orders.setOstate(1);
            }
        }
        //创建封装查询结果
        PageInfo<Orders> ordersListPageInfo = new PageInfo<>(ordersList);

        return ordersListPageInfo;
    }
    @Override
    public List<Orders> selectAll() {

        List<Orders> orders = ordersMapper.selectAll("");


        return orders;
    }

    @Override
    public Orders selectByOno(String  ono) {
        return ordersMapper.selectByOno(ono);
    }

    @Override
    public Orders selectByOnoAndGno(String ono, String gno) {
        Orders orders = ordersMapper.selectByOno(ono);
        Guest guest = guestMapper.selectByGnoAndOno(gno, ono);
        Guest[] guests = guestMapper.selectByOno(ono);
        orders.setGuest(guest);
        orders.setGuests(guests);
        return orders;
    }

    @Override
    public Orders selectById(Integer oid) {
        return ordersMapper.selectById(oid);
    }
}
