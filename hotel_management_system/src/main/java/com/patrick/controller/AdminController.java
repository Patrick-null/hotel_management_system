package com.patrick.controller;

import com.patrick.bean.Admin;
import com.patrick.bean.RespBean;
import com.patrick.excetion.MyException;
import com.patrick.service.AdminService;
import com.patrick.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    //管理员登录
    @RequestMapping("/login")
    public RespBean login(String username, String password) throws MyException {
        Admin admin = adminService.login(username, password);

        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("username",admin.getUsername());
        map.put("role",admin.getRole());
        String jwt = JwtUtil.generateJwt(map);

        return RespBean.ok("登录成功",jwt);
    }

    //用户登录
    @RequestMapping("/loginTwo")
    public RespBean loginTwo(String username, String password) throws MyException {
        Admin admin = adminService.loginTwo(username, password);

        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("username",admin.getUsername());
        map.put("role",admin.getRole());
        String jwt = JwtUtil.generateJwt(map);

        return RespBean.ok("登录成功",jwt);
    }

    @GetMapping("/admin/getUsername")
    public RespBean getUsername(String username, String password) throws MyException {


        Admin admin = adminService.login(username, password);


        return RespBean.ok("登录成功",username);
    }

    @GetMapping("/admin/getOrderVolume")
    public  RespBean getOrderVolume(){
        int volume = adminService.getOrderVolume();
        return RespBean.ok("",volume);
    }

    @GetMapping("/admin/getGuestVolume")
    public  RespBean getGuestVolume(){
        int volume = adminService.getGuestVolume();
        return RespBean.ok("",volume);
    }

    @GetMapping("/admin/selectRtypeByGender/{gender}")
    public RespBean selectRtypeByGender(@PathVariable("gender") String gender){
        System.out.println(gender);
        List<Map<String, Object>> list = adminService.selectRtypeByGender(gender);
        return RespBean.ok("",list);
    }

    @GetMapping("/admin/getAllRtype")
    public RespBean getAllRtype(){
        List<String> allRtype = adminService.getAllRtype();
        return RespBean.ok("",allRtype);
    }

}
