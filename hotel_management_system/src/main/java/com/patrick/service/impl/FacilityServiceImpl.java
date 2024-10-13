package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Facility;
import com.patrick.bean.Guest;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.mapper.FacilityMapper;
import com.patrick.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityMapper facilityMapper;
    @Override
    public Boolean insert(Facility facility) throws MyException {
        if(facilityMapper.selectByFname(facility.getFname())!=null){
            throw new MyException("该设施已经存在，请勿重复添加");
        }
        return facilityMapper.insert(facility)==1;
    }

    @Override
    public Boolean delete(Integer fid) {
        return facilityMapper.delete(fid)==1;
    }

    @Override
    public Boolean updata(Facility facility) throws MyException {
        if(facilityMapper.selectById(facility.getFid())==null){
            throw new MyException("该设施不存在，无法修改");
        }

        if(facilityMapper.selectByFname(facility.getFname())!=null&&!facilityMapper.selectById(facility.getFid()).equals(facilityMapper.selectByFname(facility.getFname()))){
            throw new MyException("该设施已经存在，不能重复添加");
        }
        return facilityMapper.updata(facility)==1;
    }

    @Override
    public PageInfo<Facility> selectAll(Integer pageNum,String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Facility> facilityList = facilityMapper.selectAll(flag);
        //创建封装查询结果
        PageInfo<Facility> facilityPageInfo = new PageInfo<>(facilityList);

        return facilityPageInfo;
    }

    public List<Facility> selectAll() {


        List<Facility> facilityList = facilityMapper.selectAll("");


        return facilityList;
    }
    @Override
    public Facility selectById(Integer fid) {
        return facilityMapper.selectById(fid);
    }

    @Override
    public Facility selectByFname(String fname) {
        return facilityMapper.selectByFname(fname);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertFacilityAndRoom(Integer[] fids, Integer rid) {
        //删除之前选的
        facilityMapper.deleteAllRoom(rid);

        //添加刚添加的
        int count=0;
        for (Integer fid:fids){
            facilityMapper.insertFacilityAndRoom(fid,rid);
            count++;
        }
        return fids.length == count;
    }
}
