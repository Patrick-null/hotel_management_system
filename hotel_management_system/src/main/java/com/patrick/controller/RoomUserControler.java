package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.RespBean;
import com.patrick.bean.Room;
import com.patrick.service.RoomService;
import com.patrick.service.UserService;
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
    private UserService userService;

    //获取所有房间
    @GetMapping("/selectAllRoom")
    public RespBean selectAllRoom(Integer pageNum,String flag){
        PageInfo<Room> roomPageInfo = userService.selectAllRoom(pageNum, flag);
        return RespBean.ok("",roomPageInfo);
    }
}
