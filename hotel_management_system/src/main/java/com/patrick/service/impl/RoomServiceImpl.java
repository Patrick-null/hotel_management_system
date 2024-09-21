package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Guest;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.mapper.GuestMapper;
import com.patrick.mapper.OrdersMapper;
import com.patrick.mapper.RoomMapper;
import com.patrick.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private GuestMapper guestMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Override
    public Boolean insert(Room room) throws MyException {
        //判断添加的房间是否存在，如果存在不允许添加成功
        Room room1 = roomMapper.selectByRno(room.getRno());
        if(room1!=null){
            throw new MyException("该房间已经存在，不能重复添加");
        }
        return roomMapper.insert(room)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer rid) throws MyException {

        //判断房间是否存在，否则不能删除
        Room room = roomMapper.selectById(rid);
        if(room==null){
            throw  new MyException("房间不存在，不能删除");
        }

        Guest[] guests = guestMapper.selectByRid2(rid);
        if (guests != null && guests.length > 0) {

            throw new MyException("该房间还有住客，不能设为异常");
        }
        return roomMapper.delete(rid)==1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(Room room) throws MyException {
        //判断修改的房间是否存在
        Room room1 = roomMapper.selectById(room.getRid());
        if(room1==null){
            throw  new MyException("房间不存在，不能修改");
        }

        //判断修改后的房间号是否存在
        if(roomMapper.selectByRno(room.getRno())!=null&&!room.getRid().equals(room1.getRid())){
            throw new MyException("修改后的房间号已经存在，无法修改");
        }

        return roomMapper.update(room)==1;
    }

    @Override
    public PageInfo<Room> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Room> roomList = roomMapper.selectAll(flag);
        System.out.println("service--------");
        roomList.stream().forEach(System.out::println);
        //创建封装查询结果
        PageInfo<Room> roomListPageInfo = new PageInfo<>(roomList);
        return roomListPageInfo;

    }

    @Override
    public List<Room> selectAll() {
        List<Room> roomList = roomMapper.selectAll("");
        return roomList;
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
