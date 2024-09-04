package com.patrick.controller;

import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

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
    public RespBean selectAll(String rtype){
        List<Room> roomList = roomService.selectAll(rtype);
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
