package com.patrick.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.service.RoomService;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/room")
@Validated
public class RoomController {
    @Autowired
    private RoomService roomService;



    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("全部订单", "UTF-8").replaceAll("\\+", "%50");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Room.class).sheet("模板").doWrite(roomService.selectAll());
    }

    @PostMapping("/upload")
    public RespBean upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), Room.class, new PageReadListener<Room>(dataList -> {
            for (Room room : dataList) {
                roomService.insert(room);
            }
        })).sheet().doRead();
        return RespBean.ok("上传成功");
    }


    @PostMapping
    public RespBean insert(@RequestBody @Validated Room room){
        if (roomService.insert(room)) {
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }
    @DeleteMapping("{rid}")
    public RespBean delete(@PathVariable("rid") Integer rid){
        if (roomService.delete(rid)) {
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }
    @PutMapping
    public RespBean update(@RequestBody @Validated Room room){
        if (roomService.update(room)) {
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum,String flag){
        PageInfo<Room> roomList = roomService.selectAll(pageNum,flag);
        roomList.getList().stream().forEach(System.out::println);
        return RespBean.ok("",roomList);
    }

    @GetMapping("/selectById/{rid}")
    public RespBean selectById(@PathVariable("rid") Integer rid){
        Room room = roomService.selectById(rid);
        if(room != null){
            return RespBean.ok("",room);
        }else {
            return RespBean.error("没有找到该房间");
        }
    }

    @GetMapping("/selectByState/{rstate}")
    public RespBean selectByState(@PathVariable("rstate") Integer rstate){
        List<Room> roomList = roomService.selectByState(rstate);
        return RespBean.ok("",roomList);
    }


}
