package com.patrick.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.*;
import com.patrick.excetion.MyException;
import com.patrick.mapper.*;
import com.patrick.service.InfoService;
import com.patrick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoService infoService;
    @Override
    public PageInfo<Room> selectAllRoom(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Room> roomList = roomMapper.selectByState2(0,flag);
        roomList.stream().forEach(System.out::println);
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
        String md5 = SecureUtil.md5(SecureUtil.md5(userAndpwd.getPassword()));
        userAndpwd.setPassword(md5);
        return adminMapper.updatePwd(userAndpwd.getUsername(),userAndpwd.getPassword())==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean enroll(Enroll enroll) throws MyException {
        Admin admin = enroll.getEnroll();
        Info info = enroll.getInfo();
        if(adminMapper.loginTwo(admin.getUsername()) != null){
            throw new MyException("用户名已存在，请重新输入");
        }
        String md5 = SecureUtil.md5(SecureUtil.md5(admin.getPassword()));
        admin.setPassword(md5);
        adminMapper.insert(admin);
        Integer aid = admin.getAid();
        info.setAid(aid);
        infoService.insert(info);
        return true;
    }


    /*//修改订单
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
        //新增现在的订单
        return ordersMapper.insert(orders)==1;
    }*/
}
