package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import com.patrick.bean.RespBean;
import com.patrick.service.GuestService;
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

}
