package com.patrick.mapper;

import com.patrick.bean.Facility;
import com.patrick.bean.Room;
import org.apache.ibatis.annotations.Param;

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


    //根据ID查询
    Facility selectById(Integer fid);

    //添加设施到相应的房间
    Integer insertFacilityAndRoom(@Param("fid")Integer fid,@Param("rid") Integer rid);

    //删除该房间的所有设施
    int deleteAllRoom(Integer rid);



}
