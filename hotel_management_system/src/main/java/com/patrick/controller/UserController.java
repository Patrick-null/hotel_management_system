package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
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
public class UserController {
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

    //查询我的订单
    @GetMapping("/selectMyAll")
    public RespBean selectMyAll(Integer pageNum,String gno){
        PageInfo<Orders> ordersPageInfo = userService.selectMyAll(pageNum, gno);
        return RespBean.ok("",ordersPageInfo);
    }

    //使用订单
    @PostMapping("/useOrder/{oid}")
    public RespBean useOrder(@PathVariable("oid") Integer oid){

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

}
