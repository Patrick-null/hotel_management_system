package com.patrick.service;

import com.patrick.bean.Facility;

import java.util.List;

public interface FacilityService {
    //添加设施
    Boolean insert(Facility facility);

    //删除设施
    Boolean delete(Integer fid);

    //修改设施
    Boolean updata(Facility facility);

    //查询所有设施
    List<Facility> selectAll();
}
