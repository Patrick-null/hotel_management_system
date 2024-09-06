package com.patrick.mapper;

import com.patrick.bean.Guest;
import com.patrick.bean.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomMapper {

    //添加房间
    int insert(Room room);

    //删除房间
    int delete(Integer rid);

    //修改房间
    int update(Room room);

    //修改房间状态
    void updateRstate(@Param("rstate") Integer rstate,@Param("rid") Integer rid);

    //查询所有房间/根据房间类型
    List<Room> selectAll(String flag);

    //根据房间号查询房间
    Room selectByRno(Integer rno);


    //根据ID查询房间
    Room selectById(Integer rid);

    //根据状态查询房间
    List<Room> selectByState(Integer rstate);



}
