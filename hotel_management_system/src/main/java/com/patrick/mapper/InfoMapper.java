package com.patrick.mapper;

import com.patrick.bean.Info;

public interface InfoMapper {
    int insert(Info info);
    int delete(Integer id);
    int update(Info info);
    Info selectById(Integer aid);
    Info selectByUsername(String username);
}
