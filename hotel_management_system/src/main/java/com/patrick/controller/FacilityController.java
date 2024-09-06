package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Facility;
import com.patrick.bean.RespBean;
import com.patrick.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;

    @PostMapping
    public RespBean insert(@RequestBody @Validated Facility facility){
        if (facilityService.insert(facility)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @DeleteMapping("{fid}")
    public RespBean delete(@PathVariable("fid") Integer fid){
        if (facilityService.delete(fid)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Facility facility){
        if (facilityService.updata(facility)) {
            return RespBean.ok("");
        }else {
            return RespBean.error("");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum,String flag ){
        PageInfo<Facility> facilityList = facilityService.selectAll(pageNum,flag);
        return RespBean.ok("",facilityList);
    }
}
