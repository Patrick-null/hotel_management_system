package com.patrick.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.service.OrdersService;
import com.sun.deploy.net.URLEncoder;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;



@RestController
@CrossOrigin
@Validated
@RequestMapping("/admin/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("全部订单", "UTF-8").replaceAll("\\+", "%50");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Orders.class).sheet("模板").doWrite(ordersService.selectAll());
    }

    @PostMapping("/upload")
    public RespBean upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Orders.class, new PageReadListener<Orders>(dataList -> {
            for (Orders order : dataList) {
                try {
                    ordersService.insert(order);
                } catch (MyException e) {
                    // 处理异常
                    throw new RuntimeException(e.getMessage()); // 可以选择抛出 RuntimeException 或者记录错误
                }
            }
        })).sheet().doRead();
        return RespBean.ok("上传成功");
    }

    @PostMapping
    public RespBean insert(@RequestBody @Validated Orders orders) throws MyException {
        if (ordersService.insert(orders)) {
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @DeleteMapping("{oid}")
    public RespBean delete(@PathVariable("oid") Integer oid) throws MyException {
        Integer ostate = ordersService.selectById(oid).getOstate();
        if (ostate == 1) {

            throw  new MyException("该订单已使用，不能重复使用");
        }
        if (ordersService.delete(oid)) {
            return RespBean.ok("使用成功");
        }
        return RespBean.error("使用失败");
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Orders orders) throws MyException {
        if (ordersService.update(orders)) {
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean select(Integer pageNum,String flag){
        PageInfo<Orders> ordersList = ordersService.selectAll(pageNum,flag);
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
