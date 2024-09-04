package com.patrick.service.impl;

import com.patrick.bean.Room;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Override
    public Boolean insert(Room room) {
        return roomMapper.insert(room)==1;
    }

    @Override
    public Boolean delete(Integer rid) {
        return roomMapper.delete(rid)==1;
    }

    @Override
    public Boolean update(Room room) {
        return roomMapper.update(room)==1;
    }

    @Override
    public List<Room> selectAll(String rtype) {
        return roomMapper.selectAll(rtype);
    }

    @Override
    public Room selectByRno(Integer rno) {
        return roomMapper.selectByRno(rno);
    }

    @Override
    public Room selectById(Integer rid) {
        return roomMapper.selectById(rid);
    }

    @Override
    public List<Room> selectByState(Integer rstate) {
        return roomMapper.selectByState(rstate);
    }
}
