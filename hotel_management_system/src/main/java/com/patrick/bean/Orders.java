package com.patrick.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private Integer oid;

    //订单编号
    private Integer ono;

    //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date otime;

    //下单人员
    private Integer gid;

    //房间号
    private Room[] rids;

    //是否已入住
    private Integer ostate;
}
