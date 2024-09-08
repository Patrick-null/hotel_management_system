package com.patrick.mapper;

import com.patrick.bean.Admin;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    Admin login(String username);
    Admin loginTwo(String username);

    int getOrderVolume();

    int getGuestVolume();

    //根据性别获取每个房间的人数
    List<Map<String,Object>> selectRtypeByGender(String gender);

    //获取所有房间类型
    List<String> getAllRtype();
}