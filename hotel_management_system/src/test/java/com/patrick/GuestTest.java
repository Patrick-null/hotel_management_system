package com.patrick;


import cn.hutool.crypto.digest.MD5;
import com.patrick.bean.Admin;
import com.patrick.bean.Guest;
import com.patrick.bean.Info;
import com.patrick.bean.Orders;
import com.patrick.mapper.*;
import com.patrick.service.AdminService;
import com.patrick.service.GuestService;
import com.patrick.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.tunnel.server.SocketTargetServerConnection;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class GuestTest {
    @Autowired
    private GuestService guestService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private FacilityMapper facilityMapper;


    @Test
    public  void test14(){
        facilityMapper.selectAll("").stream().forEach(System.out::println);
    }
    @Test
    public void test13(){
        System.out.println(MD5.create().digestHex(MD5.create().digestHex("admin")));
    }

    @Test
    public void test12(){
        Guest guest = guestMapper.selectByGnoAndOno("10010", "1725862262190");
        System.out.println(guest);
    }

    @Test
    public  void test11(){
        ordersMapper.selectAll("").stream().forEach(System.out::println);
    }

    @Test
    public void test10(){
        //userMapper.selectMyAll("10010").stream().forEach(System.out::println);
    }

    @Test
    public void test09(){
        Admin admin = infoMapper.selectByUsername(2);
        System.out.println(admin);
    }
    @Test
    public void test08(){
        userMapper.selectAll("").stream().forEach(System.out::println);
    }

    @Test
    public  void test07(){
        adminService.getAllRtype().stream().forEach(System.out::println);

    }


    @Test
    public void test06(){
        //adminMapper.selectRtypeByGender("男").stream().forEach(System.out::println);
    }
    @Test
    public void test05(){
        roomMapper.selectAll("").stream().forEach(System.out::println);
    }
    @Test
    public void test01(){
        Guest aa = new Guest();
        aa.setGname("tom");
        aa.setGphone("1231");
        aa.setGno("12321");
        aa.setRid(101);
        System.out.println(guestService.insert(aa));
    }

    @Test
    public void test02(){
        System.out.println(guestService.selectAll(1,"t"));
    }

    @Test
    public void test03(){
        System.out.println(ordersService.selectAll(1,"1"));
    }

    @Test
    public void test04(){
        System.out.println(ordersMapper.selectAll("1"));
    }
}
