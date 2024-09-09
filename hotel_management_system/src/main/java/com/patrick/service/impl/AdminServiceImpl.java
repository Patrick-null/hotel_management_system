package com.patrick.service.impl;

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
        if(!admin.getPassword().equals(password)){
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

        if(!admin.getPassword().equals(password)){
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
    public List<Map<String, Object>> selectRtypeByGender(String gender) {
        return adminMapper.selectRtypeByGender(gender);
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
        return adminMapper.updatePwd(userAndpwd.getUsername(),userAndpwd.getPassword())==1;
    }
}
