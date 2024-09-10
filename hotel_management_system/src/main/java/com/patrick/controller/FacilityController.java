package com.patrick.controller;

import com.github.pagehelper.PageInfo;
import com.patrick.bean.Facility;
import com.patrick.bean.Guest;
import com.patrick.bean.Orders;
import com.patrick.bean.RespBean;
import com.patrick.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/admin/facility")
public class FacilityController {
    @Autowired
    private FacilityService facilityService;


    @PostMapping
    public RespBean insert(@RequestBody @Validated Facility facility){
        if (facilityService.insert(facility)) {
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("");
        }
    }

    @DeleteMapping("{fid}")
    public RespBean delete(@PathVariable("fid") Integer fid){
        if (facilityService.delete(fid)) {
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Facility facility){
        if (facilityService.updata(facility)) {
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(Integer pageNum,String flag ){
        PageInfo<Facility> facilityList = facilityService.selectAll(pageNum,flag);
        return RespBean.ok("",facilityList);
    }

    @GetMapping("/selectAllT")
    public RespBean selectAll(){
        List<Facility> facilityList = facilityService.selectAll();
        Facility[] facilities = (Facility[]) facilityList.toArray(new Facility[0]);

        return RespBean.ok("",facilities);
    }

    @GetMapping("/selectById/{fid}")
    public RespBean selectById(@PathVariable("fid") Integer fid){
        Facility facility = facilityService.selectById(fid);
        return RespBean.ok("修改成功",facility);
    }

    //将选择的设施添加到房间
    @PostMapping("/insertFacilityAndRoom")
    public RespBean insertFacilityAndRoom(@RequestBody Map<String,Object> map){

        //获取房间id
        Integer rid = (Integer) map.get("rid");

        //获取添加设施的id
        System.out.println(map.get("facilitys"));
        List<Integer> fidList =  (List<Integer>) map.get("facilitys");
        Integer[] fids = fidList.toArray(new Integer[0]);
        if (facilityService.insertFacilityAndRoom(fids,rid)) {
            return RespBean.ok("添加成功");
        }


        return RespBean.error("");
    }

}
