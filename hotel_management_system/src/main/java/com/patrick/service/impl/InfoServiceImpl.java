package com.patrick.service.impl;

import com.patrick.bean.Admin;
import com.patrick.bean.Info;
import com.patrick.mapper.InfoMapper;
import com.patrick.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;
    @Override
    public Boolean insert(Info info) {
        return infoMapper.insert(info)==1;
    }

    @Override
    public Boolean delete(Integer aid) {
        return infoMapper.delete(aid)==1;
    }

    @Override
    public Boolean update(Info info) {
        return infoMapper.update(info)==1;
    }

    @Override
    public Info selectById(Integer aid) {
        return infoMapper.selectById(aid);
    }

    @Override
    public Admin selectByUsername(Integer id) {
        return infoMapper.selectByUsername(id);
    }
}
