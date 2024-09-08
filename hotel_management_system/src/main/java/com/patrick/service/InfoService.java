package com.patrick.service;

import com.patrick.bean.Info;

public interface InfoService {
    Boolean insert(Info info);
    Boolean delete(Integer aid);
    Boolean update(Info info);
    Info selectById(Integer aid);

    Info selectByUsername(String username);
}
