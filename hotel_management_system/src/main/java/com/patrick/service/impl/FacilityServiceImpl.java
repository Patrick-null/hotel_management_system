package com.patrick.service.impl;

import com.patrick.bean.Facility;
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
    public List<Facility> selectAll() {
        return facilityMapper.selectAll();
    }
}
