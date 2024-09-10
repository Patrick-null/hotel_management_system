package com.patrick.mapper;

import com.patrick.bean.Guest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface GuestMapper {
    //添加住客
    int insert(Guest guest);

    //删除住客
    int delete(Integer gid);
    int delete2(Integer gid);

    //修改住客
    int update(Guest guest);

    //修改住客状态
    void updateGstate(@Param("gstate") Integer gstate, @Param("ono") String ono);

    //查询所有住客
    List<Guest> selectAll(String flag);

    //根据姓名查询住客
    List<Guest> selectByName(String gname);

    //根据ID查询住客
    Guest selectById(Integer gid);

    //根据房间ID查询住客
    Guest selectByRid(Integer rid);
    Guest[] selectByRid2(Integer rid);

    //根据身份证查询
    Guest selectByNo(String gno);

    //根据身份证查询
    Guest selectByGnoAndOno(@Param("gno")String gno,@Param("ono")String ono);
    //根据订单ID查询住客
    Guest[] selectByOno(String ono);
}
