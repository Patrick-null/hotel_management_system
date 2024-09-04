package com.patrick.controller;

import com.patrick.bean.Facility;
import com.patrick.bean.RespBean;
import com.patrick.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public RespBean insert(Facility facility){
        if (facilityService.insert(facility)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @DeleteMapping
    public RespBean delete(Integer fid){
        if (facilityService.delete(fid)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @PutMapping
    public RespBean update(Facility facility){
        if (facilityService.updata(facility)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(){
        List<Facility> facilityList = facilityService.selectAll();
        return RespBean.ok("",facilityList);
    }
}
