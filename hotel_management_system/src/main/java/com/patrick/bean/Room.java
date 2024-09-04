package com.patrick.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 房间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    //房间id
    private Integer rid;

    //房间号
    private Integer rno;

    //房间类型
    private String rtype;

    //房间价格
    private BigDecimal rprice;

    //房间状态
    private Integer rstate;

    //房间人员
    private Guest[] guests;
}
