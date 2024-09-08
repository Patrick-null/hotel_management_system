package com.patrick.controller.user;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class RoomUserControler {
    @Autowired
    private RoomService roomService;

    //获取所有房间
    @GetMapping("/selectAllRoom")
    public RespBean selectAllRoom(Integer pageNum,String flag){
        PageInfo<Room> roomPageInfo = roomService.selectAll(pageNum, flag);
        return RespBean.ok("",roomPageInfo);
    }
}
