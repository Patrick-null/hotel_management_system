package com.patrick;


import com.patrick.bean.Guest;
import com.patrick.service.GuestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class GuestTest {
    @Autowired
    private GuestService guestService;
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
        System.out.println(guestService.selectAll());
    }
}
