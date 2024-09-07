package com.patrick.service.impl;

import com.patrick.bean.Admin;
import com.patrick.mapper.AdminMapper;
import com.patrick.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public Boolean login(String username,String password) {
        Admin admin = adminMapper.login(username);
        if(admin.getPassword().equals(password)){
            return true;
        }
        return false;
    }
}
