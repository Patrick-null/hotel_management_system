package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Info;
import com.patrick.excetion.MyException;

import java.util.List;

public interface InfoService {
    Boolean insert(Info info) throws MyException;
    Boolean delete(Integer aid);
    Boolean update(Info info) throws MyException;
    Info selectById(Integer aid);

    Admin selectByUsername(Integer id);

    PageInfo<Info> selectAll(Integer pageNum, String flag);
}
