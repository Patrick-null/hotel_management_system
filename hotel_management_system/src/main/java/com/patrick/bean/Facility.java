package com.patrick.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 房间设施
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facility {
    //设施id
    private Integer fid;

    //设施名称
    private String fname;

    //设施数量
    private Integer fnum;

    //设施价值
    private BigDecimal fvalue;
}
