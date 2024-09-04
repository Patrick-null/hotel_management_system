package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import com.patrick.mapper.GuestMapper;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class GuestServiceImpl implements GuestService {
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insert(Guest guest) {
        roomMapper.updateRstate(1,guest.getRid());

        return guestMapper.insert(guest)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer gid) {
        roomMapper.updateRstate(0,selectById(gid).getRid());
        return guestMapper.delete(gid)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(Guest guest) {
        //将本来房间变成空闲
        //roomMapper.updateRstate();

        //将修改后的房间变成已预订

        //将用户修改为已
        return guestMapper.update(guest)==1;
    }

    @Override
    public PageInfo<Guest> selectAll() {
        //导包
        //设置分页信息
        PageHelper.startPage(1,5);
        //查询
        List<Guest> guestList = guestMapper.selectAll();
        //创建封装查询结果
        PageInfo<Guest> guestPageInfo = new PageInfo<>(guestList);
        return guestPageInfo;
    }

    @Override
    public List<Guest> selectByName(String gname) {
        return guestMapper.selectByName(gname);
    }

    @Override
    public Guest selectById(Integer gid) {
        return guestMapper.selectById(gid);
    }
}
