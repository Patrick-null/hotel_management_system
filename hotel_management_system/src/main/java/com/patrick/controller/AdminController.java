package com.patrick.controller;

import com.patrick.bean.Admin;
import com.patrick.bean.RespBean;
import com.patrick.excetion.MyException;
import com.patrick.service.AdminService;
import com.patrick.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;
import java.util.HashMap;

@RestController
@RequestMapping
@CrossOrigin
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login")
    public RespBean login(String username, String password) throws MyException {
        Admin admin = adminService.login(username, password);

        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("username",admin.getUsername());
        map.put("role",admin.getRole());
        String jwt = JwtUtil.generateJwt(map);

        return RespBean.ok("登录成功",jwt);
    }

    @RequestMapping("/admin/getUsername")
    public RespBean getUsername(String username, String password) throws MyException {


        Admin admin = adminService.login(username, password);


        return RespBean.ok("登录成功",username);
    }
}
