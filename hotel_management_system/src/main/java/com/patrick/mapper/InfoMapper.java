package com.patrick.mapper;

import com.patrick.bean.Admin;
import com.patrick.bean.Info;

import java.util.List;

public interface InfoMapper {
    int insert(Info info);
    int delete(Integer id);
    int update(Info info);
    Info selectById(Integer aid);
    Info selectByID(Integer id);
    Info selectByNo(String no);
    Admin selectByUsername(Integer id);

    List<Info> selectAll(String flag);
}
