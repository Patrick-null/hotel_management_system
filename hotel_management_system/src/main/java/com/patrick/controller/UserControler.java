package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.service.OrdersService;
import com.patrick.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserControler {
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;

    //获取所有房间
    @GetMapping("/selectAllRoom")
    public RespBean selectAllRoom(Integer pageNum,String flag){
        PageInfo<Room> roomPageInfo = userService.selectAllRoom(pageNum, flag);
        return RespBean.ok("",roomPageInfo);
    }

    //提交订单
    @PostMapping("/buyRoom")
    public RespBean buyRoom(@RequestBody @Validated Orders order) throws MyException {
        if (ordersService.insert(order)) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }
}