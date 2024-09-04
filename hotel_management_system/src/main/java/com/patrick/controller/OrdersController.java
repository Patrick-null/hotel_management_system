package com.patrick.controller;

import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.service.OrdersService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public RespBean insert(@RequestBody @Validated Orders orders){
        if (ordersService.insert(orders)) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("{oid}")
    public RespBean delete(@PathVariable("oid") Integer oid){
        if (ordersService.delete(oid)) {
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Orders orders){
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
    @GetMapping("/selectById/{oid}")
    public RespBean selectById(@PathVariable("oid") Integer oid){
        Orders orders = ordersService.selectById(oid);
        if(orders != null){
            return RespBean.ok("",orders);
        }else {
            return RespBean.error("没有找到该订单");
        }
    }
}
