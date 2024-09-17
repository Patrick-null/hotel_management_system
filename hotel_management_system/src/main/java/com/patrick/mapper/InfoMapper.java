package com.patrick.mapper;

import com.patrick.bean.Admin;
import com.patrick.bean.Info;

import java.util.List;

public interface InfoMapper {
    int insert(Info info);
    int delete(Integer id);
    int update(Info info);
    Info selectById(Integer aid);
    Admin selectByUsername(Integer id);

    List<Info> selectAll(String flag);
}
