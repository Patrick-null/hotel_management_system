package com.patrick.controller;


import com.patrick.bean.Member;
import com.patrick.bean.RespBean;
import com.patrick.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/admin/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping
    public RespBean insert(@RequestBody @Validated Member member){
        if(memberService.insert(member)){
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }


    @DeleteMapping("{mid}")
    public RespBean delete(@PathVariable("mid") Integer mid){
        if(memberService.delete(mid)){
            return RespBean.ok("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }

    @PutMapping
    public RespBean update(@RequestBody @Validated Member member){
        if(memberService.update(member)){
            return RespBean.ok("修改成功");
        }else {
            return RespBean.error("修改失败");
        }
    }

    @GetMapping("/selectAll")
    public RespBean selectAll(){
        List<Member> memberList = memberService.selectAll();
        return RespBean.ok("",memberList);
    }

    @GetMapping("/selectById/{mid}")
    public RespBean selectById(@PathVariable("mid") Integer mid){
        Member member = memberService.selectById(mid);
        return RespBean.ok("",member);
    }
}
