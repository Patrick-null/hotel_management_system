package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Enroll;
import com.patrick.bean.Guest;
import com.patrick.bean.Info;
import com.patrick.bean.RespBean;
import com.patrick.excetion.MyException;
import com.patrick.service.GuestService;
import com.patrick.service.InfoService;
import com.patrick.service.UserService;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/admin/guest")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Autowired
    private InfoService infoService;
    @Autowired
    private UserService userService;

    @PostMapping
    public RespBean insert(@RequestBody @Validated Guest guest){
        if(guestService.insert(guest)){
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }


    @DeleteMapping("{gid}")
    public RespBean delete(@PathVariable("gid") Integer gid){
        if(guestService.delete(gid)){
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Guest guest){
        if(guestService.update(guest)){
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum,String flag){
        PageInfo<Guest> guestList = guestService.selectAll(pageNum,flag);
        return RespBean.ok("",guestList);
    }

    @GetMapping("/selectById/{gid}")
    public RespBean selectById(@PathVariable("gid") Integer gid){
        Guest guest = guestService.selectById(gid);
        return RespBean.ok("",guest);
    }

    @GetMapping("/selectByName")
    public RespBean selectByName(String gname){
        List<Guest> guestList = guestService.selectByName(gname);
        return RespBean.ok("",guestList);
    }

    @GetMapping("/selectAllInfo")
    public RespBean selectAllInfo(Integer pageNum,String flag){
        PageInfo<Info> infoList = infoService.selectAll(pageNum,flag);
        return RespBean.ok("",infoList);
    }

    //注册账号
    @PostMapping("/enroll")
    public RespBean enroll(@RequestBody @Validated Enroll enroll) throws MyException {
        System.out.println(enroll);
        if(enroll.getEnroll().getUsername().equals("")){
            throw  new MyException("账号不能为空");
        }
        if(enroll.getEnroll().getPassword().equals("")){
            throw  new MyException("密码不能为空");
        }
        if(userService.enroll(enroll)){
            return RespBean.ok("注册成功",enroll);
        }else {
            return RespBean.error("注册失败");
        }
    }
}
