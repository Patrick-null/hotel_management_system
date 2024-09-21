package com.patrick.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.patrick.bean.Admin;
import com.patrick.bean.Info;
import com.patrick.bean.Room;
import com.patrick.excetion.MyException;
import com.patrick.mapper.InfoMapper;
import com.patrick.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {
    @Autowired
    private InfoMapper infoMapper;
    @Override
    public Boolean insert(Info info) throws MyException {
        Info info1 = infoMapper.selectByNo(info.getNo());
        if(info1!=null){
            throw new MyException("身份证号已存在，不能添加");
        }
        info.setAvatar("moren.jpg");
        return infoMapper.insert(info)==1;
    }

    @Override
    public Boolean delete(Integer aid) {
        return infoMapper.delete(aid)==1;
    }

    @Override
    public Boolean update(Info info) throws MyException {
        Info newInfo = info;
        Info oldInfo = infoMapper.selectByID(info.getId());
        if(infoMapper.selectByNo(newInfo.getNo())!=null && !infoMapper.selectByNo(newInfo.getNo()).getId().equals(oldInfo.getId())){
            throw new MyException("修改后的身份证号已经存在，不能修改");
        }
        return infoMapper.update(info)==1;
    }

    @Override
    public Info selectById(Integer aid) {
        return infoMapper.selectById(aid);
    }

    @Override
    public Info selectByID(Integer id) {
        return infoMapper.selectByID(id);
    }



    @Override
    public Admin selectByUsername(Integer id) {
        return infoMapper.selectByUsername(id);
    }

    @Override
    public PageInfo<Info> selectAll(Integer pageNum, String flag) {
        //导包
        //设置分页信息
        PageHelper.startPage(pageNum,5);
        //查询
        List<Info> infoList = infoMapper.selectAll(flag);
        //创建封装查询结果
        PageInfo<Info> infoListPageInfo = new PageInfo<>(infoList);
        return infoListPageInfo;
    }
}
