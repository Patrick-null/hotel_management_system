package com.patrick.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.patrick.bean.Admin;
import com.patrick.excetion.MyException;
import com.patrick.mapper.AdminMapper;
import com.patrick.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(String username,String password,Integer role) throws MyException {
        Admin admin = adminMapper.login(username);



        if(admin == null){
            throw new MyException("没找到该用户，请确认后重新输入");
        }
        if(!role.equals(admin.getRole())){
            throw new MyException("请按角色登录");
        }


        //md5加密
        String md5Pwd = SecureUtil.md5(SecureUtil.md5(password));

        if(!admin.getPassword().equals(md5Pwd)){
            throw new MyException("密码错误，请确认后重新输入");
        }


        return admin;

    }

    @Override
    public Admin loginTwo(String username, String password,Integer role) throws MyException {
        Admin admin = adminMapper.login(username);


        if(admin == null){
            throw new MyException("没找到该用户，请确认后重新输入");
        }

        if(!(role.equals(admin.getRole()))){
            throw new MyException("请按角色登录");
        }

        //md5加密
        String md5Pwd = SecureUtil.md5(SecureUtil.md5(password));
        if(!admin.getPassword().equals(md5Pwd)){
            throw new MyException("密码错误，请确认后重新输入");
        }




        return admin;

    }

    @Override
    public int getOrderVolume() {
        return adminMapper.getOrderVolume();
    }

    @Override
    public int getGuestVolume() {
        return adminMapper.getGuestVolume();
    }

    @Override
    public List<Map<String, Object>> selectRtypeByGender( ) {
        return adminMapper.selectRtypeByGender();
    }
    public List<Map<String, Object>> selectRtypeBy1( ) {
        return adminMapper.selectRtypeBy1();
    }

    @Override
    public List<String> getAllRtype() {
        return adminMapper.getAllRtype();
    }


    @Override
    public boolean updatePwd(Admin userAndpwd) throws MyException {
        if(adminMapper.login(userAndpwd.getUsername())==null) {
            throw new MyException("没有找到该用户名");
        }
        String md5 = SecureUtil.md5(SecureUtil.md5(userAndpwd.getPassword()));
        userAndpwd.setPassword(md5);
        return adminMapper.updatePwd(userAndpwd.getUsername(),userAndpwd.getPassword())==1;
    }


}
