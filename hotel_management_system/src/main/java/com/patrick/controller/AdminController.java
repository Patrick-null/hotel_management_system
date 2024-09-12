package com.patrick.controller;

import com.patrick.bean.Admin;
import com.patrick.bean.Enroll;
import com.patrick.bean.RespBean;
import com.patrick.excetion.MyException;
import com.patrick.service.AdminService;
import com.patrick.service.UserService;
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
@Validated
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;

    //管理员登录
    @RequestMapping("/login")
    public RespBean login(String username, String password) throws MyException {
        Integer role=0;
        Admin admin = adminService.login(username, password ,role);

        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("aid",admin.getAid());
        map.put("username",admin.getUsername());
        map.put("role",admin.getRole());
        map.put("admin",0);
        String jwt = JwtUtil.generateJwt(map);

        return RespBean.ok("登录成功",jwt);
    }

    //用户登录
    @RequestMapping("/loginTwo")
    public RespBean loginTwo(String username, String password) throws MyException {
        Integer role=1;
        Admin admin = adminService.loginTwo(username, password,role);

        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("aid",admin.getAid());
        map.put("username",admin.getUsername());
        map.put("role",admin.getRole());
        map.put("admin",1);
        String jwt = JwtUtil.generateJwt(map);

        return RespBean.ok("登录成功",jwt);
    }

    @GetMapping("/admin/getUsername")
    public RespBean getUsername(String username, String password) throws MyException {


        //Admin admin = adminService.login(username, password);


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

    @GetMapping("/admin/selectRtypeByGender")
    public RespBean selectRtypeByGender(){
        System.out.println();
        List<Map<String, Object>> list = adminService.selectRtypeByGender();
        return RespBean.ok("",list);
    }

    @GetMapping("/admin/selectRtypeBy1")
    public RespBean selectRtypeBy1(){
        System.out.println();
        List<Map<String, Object>> list = adminService.selectRtypeBy1();
        return RespBean.ok("",list);
    }

    @GetMapping("/admin/getAllRtype")
    public RespBean getAllRtype(){
        String[] allRtype = adminService.getAllRtype().toArray(new String[0]);
        return RespBean.ok("",allRtype);
    }


    //修改密码
    @PutMapping("/admin/userAndpwd")
    public RespBean userAndpwd(@RequestBody @Validated Admin userAndpwd) throws MyException {
        if(userAndpwd.getUsername()==null){
            throw  new MyException("账号不能为空");
        }
        if(userAndpwd.getPassword()==null){
            throw  new MyException("密码不能为空");
        }
        boolean b = adminService.updatePwd(userAndpwd);

        return RespBean.ok("修改成功");
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
