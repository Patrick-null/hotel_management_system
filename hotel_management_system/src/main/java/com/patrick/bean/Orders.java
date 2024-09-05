package com.patrick.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
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

    private String gno;

    //下单时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date otime;



    //下单人员
    private Guest guest;

    //住客人员
    private Guest[] guests;



    //是否已入住
    private Integer ostate;
}
