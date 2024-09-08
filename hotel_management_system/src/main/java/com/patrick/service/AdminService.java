package com.patrick.service;

import com.patrick.bean.Admin;
import com.patrick.excetion.MyException;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Admin login(String username, String password) throws MyException;
    Admin loginTwo(String username, String password) throws MyException;

    int getOrderVolume();

    int getGuestVolume();

    List<Map<String,Object>> selectRtypeByGender(String gender);

    List<String> getAllRtype();
}
