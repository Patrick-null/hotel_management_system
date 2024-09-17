package com.patrick.controller;

import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.UUID;
import com.patrick.bean.Admin;
import com.patrick.bean.Info;
import com.patrick.bean.RespBean;
import com.patrick.service.AdminService;
import com.patrick.service.InfoService;
import com.patrick.utils.JwtUtil;
import com.patrick.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/info")
public class InfoController {
    @Autowired
    private InfoService infoService;
    @Autowired
    private AdminService adminService;



    @PostMapping
    public RespBean insert(@RequestBody @Validated Info info){
        if (infoService.insert(info)) {
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }
    @DeleteMapping("{aid}")
    public RespBean delete(@PathVariable("aid") Integer aid){
        if (infoService.delete(aid)) {
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }
    @PutMapping
    public RespBean update(@RequestBody @Validated Info info){
        if (infoService.update(info)) {
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }
    @GetMapping("{aid}")
    public RespBean selectById(@PathVariable("aid") Integer aid){
        Info info = infoService.selectById(aid);
        if (info!=null) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @GetMapping("/selectByUsername/")
    public RespBean selectByUsername(@RequestHeader("token") String token){
        //解析jwt
        Map<String, Object> map = JwtUtil.parseJwtToMap(token);
        //获取id
        Integer id = (Integer)map.get("aid");
        Admin admin = infoService.selectByUsername(id);
        if (admin!=null) {
            return RespBean.ok("",admin);
        }else {
            return RespBean.error("");
        }
    }
}
