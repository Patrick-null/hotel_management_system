package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.service.OrdersService;
import com.patrick.service.RoomService;
import com.patrick.service.UserService;
import com.sun.javafx.logging.PulseLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private RoomService roomService;

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

    //查询我的订单
    @GetMapping("/selectMyAll")
    public RespBean selectMyAll(Integer pageNum,String gno,String flag){
        PageInfo<Orders> ordersPageInfo = userService.selectMyAll(pageNum, gno,flag);
        return RespBean.ok("",ordersPageInfo);
    }

    //使用订单
    @PostMapping("/useOrder/{oid}")
    public RespBean useOrder(@PathVariable("oid") Integer oid) throws MyException {
        Integer ostate = ordersService.selectById(oid).getOstate();
        if (ostate == 1) {

            throw  new MyException("该订单已使用，不能重复使用");
        }
        if (ordersService.delete(oid)) {
            return RespBean.ok("使用成功");
        }else {
            return RespBean.error("使用失败");
        }
    }

    //修改密码
    @PutMapping("/userAndpwd")
    public RespBean userAndpwd(@RequestBody @Validated Admin userAndpwd) throws MyException {
        if(userAndpwd.getUsername()==null){
            throw  new MyException("账号不能为空");
        }
        if(userAndpwd.getPassword()==null){
            throw  new MyException("密码不能为空");
        }
        boolean b = userService.updatePwd(userAndpwd);

        return RespBean.ok("修改成功");
    }

    //修改订单
    @PutMapping("/updateOrder")
    public  RespBean updateOrder(@RequestBody @Validated Orders orders) throws MyException {

        System.out.println("123123123123123123---");
        System.out.println("999999999999999999999999");
        System.out.println(orders);
        Boolean update = userService.update(orders);
        if(update){
            return RespBean.ok("修改成功");
        }

        return RespBean.error("修改失败");
    }

    //根据订单id查询
    @GetMapping("/selectById/{oid}")
    public RespBean selectById(@PathVariable("oid") Integer oid){
        Orders orders = ordersService.selectById(oid);
        if(orders != null){
            return RespBean.ok("",orders);
        }else {
            return RespBean.error("没有找到该订单");
        }
    }

    //查询所有空闲房间
    @GetMapping("/selectByState/{rstate}")
    public RespBean selectByState(@PathVariable("rstate") Integer rstate){
        List<Room> roomList = roomService.selectByState(rstate);
        roomList.stream().forEach(System.out::println);
        return RespBean.ok("",roomList);
    }



}
