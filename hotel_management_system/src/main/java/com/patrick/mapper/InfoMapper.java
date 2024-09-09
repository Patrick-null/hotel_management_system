package com.patrick.mapper;

import com.patrick.bean.Admin;
import com.patrick.bean.Info;

public interface InfoMapper {
    int insert(Info info);
    int delete(Integer id);
    int update(Info info);
    Info selectById(Integer aid);
    Admin selectByUsername(Integer id);
}
