package com.patrick.service;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Facility;
import com.patrick.excetion.MyException;

import java.util.List;

public interface FacilityService {
    //添加设施
    Boolean insert(Facility facility) throws MyException;

    //删除设施
    Boolean delete(Integer fid);

    //修改设施
    Boolean updata(Facility facility) throws MyException;

    //查询所有设施
    PageInfo<Facility> selectAll(Integer pageNum,String flag);
    //查询所有设施
    List<Facility> selectAll();

    //根据ID查询设施
    Facility selectById(Integer fid);

    Facility selectByFname(String fname);


    //添加多个设施到房间
    Boolean insertFacilityAndRoom(Integer[] fids,Integer rid);
}
