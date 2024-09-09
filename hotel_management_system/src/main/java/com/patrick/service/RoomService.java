package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Room;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

public interface RoomService {
    //添加房间
    Boolean insert(Room room);

    //删除房间
    Boolean delete(Integer rid);

    //修改房间
    Boolean update(Room room);

    //查询所有房间/根据房间类型
    PageInfo<Room> selectAll(Integer pageNum, String flag);

    List<Room> selectAll();

    //根据房间号查询房间
    Room selectByRno(Integer rno);


    //根据ID查询房间
    Room selectById(Integer rid);

    //根据状态查询房间
    List<Room> selectByState(Integer rstate);
}
