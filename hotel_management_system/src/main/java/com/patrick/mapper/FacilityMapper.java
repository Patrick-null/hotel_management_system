package com.patrick.mapper;

import com.patrick.bean.Facility;

import java.util.List;

public interface FacilityMapper {
    //添加设施
    int insert(Facility facility);

    //删除设施
    int delete(Integer fid);

    //修改设施
    int updata(Facility facility);

    //查询所有设施
    List<Facility> selectAll(String flag);

    //查询根据

}
