package com.patrick.controller;

import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public RespBean insert(Orders orders){
        if (ordersService.insert(orders)) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping
    public RespBean delete(Integer oid){
        if (ordersService.delete(oid)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping
    public RespBean update(Orders orders){
        if (ordersService.update(orders)) {
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean select(){
        List<Orders> ordersList = ordersService.selectAll();
        return RespBean.ok("",ordersList);
    }
}
