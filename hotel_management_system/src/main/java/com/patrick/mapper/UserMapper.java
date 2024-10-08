package com.patrick.mapper;

import com.patrick.bean.Admin;
import com.patrick.bean.Orders;
import com.patrick.bean.Room;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //查询所有房间/根据房间类型
    List<Room> selectAll(String flag);

    //查询我的订单
    List<Orders> selectMyAll(@Param("gno") String gno,@Param("flag") String flag);


}
