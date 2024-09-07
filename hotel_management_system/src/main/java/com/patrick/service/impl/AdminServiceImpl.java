package com.patrick.service.impl;

import com.patrick.bean.Admin;
import com.patrick.excetion.MyException;
import com.patrick.mapper.AdminMapper;
import com.patrick.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Admin login(String username,String password) throws MyException {
        Admin admin = adminMapper.login(username);

        if(admin == null){
            throw new MyException("没找到该用户，请确认后重新输入");
        }
        if(admin.getPassword().equals(password)){
            return admin;
        }else {

            throw new MyException("密码错误，请确认后重新输入");
        }

    }
}
