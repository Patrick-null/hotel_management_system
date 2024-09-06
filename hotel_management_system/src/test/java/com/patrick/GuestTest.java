package com.patrick;


import com.patrick.bean.Guest;
import com.patrick.mapper.OrdersMapper;
import com.patrick.service.GuestService;
import com.patrick.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println(ordersService.selectAll());
    }

    @Test
    public void test04(){
        System.out.println(ordersMapper.selectAll());
    }
}
