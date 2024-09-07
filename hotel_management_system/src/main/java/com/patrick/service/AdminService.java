package com.patrick.service;

import com.patrick.bean.Admin;
import com.patrick.excetion.MyException;

public interface AdminService {
    Admin login(String username, String password) throws MyException;
}
