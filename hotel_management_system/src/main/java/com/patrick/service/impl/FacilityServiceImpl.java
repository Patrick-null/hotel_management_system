package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Facility;
import com.patrick.bean.Guest;
import com.patrick.mapper.FacilityMapper;
import com.patrick.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityMapper facilityMapper;
    @Override
    public Boolean insert(Facility facility) {
        return facilityMapper.insert(facility)==1;
    }

    @Override
    public Boolean delete(Integer fid) {
        return facilityMapper.delete(fid)==1;
    }

    @Override
    public Boolean updata(Facility facility) {
        return facilityMapper.updata(facility)==1;
    }

    @Override
    public PageInfo<Facility> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,8);
        //查询
        List<Facility> facilityList = facilityMapper.selectAll(flag);
        //创建封装查询结果
        PageInfo<Facility> facilityPageInfo = new PageInfo<>(facilityList);

        return facilityPageInfo;
    }
}
