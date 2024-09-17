package com.patrick.bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @ExcelProperty("订单id")
    private Integer oid;

    //订单编号
    @ExcelProperty("订单编号")
    @NotBlank(message = "订单号不能为空")
    private String ono;

    @ExcelProperty("身份证号")
    @NotBlank(message = "身份证不能为空")
    private String gno;

    @ExcelProperty("房间id")
    private Integer rid;

    //下单时间
    @ExcelProperty("下单时间")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(30)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date otime;

    //住房时间
    @ExcelProperty("住房时间")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(30)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date ostart;


    //离房时间
    @ExcelProperty("离房时间")
    @DateTimeFormat("yyyy-MM-dd")
    @ColumnWidth(30)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oend;

    @ExcelProperty("订单金额")
    private BigDecimal moneys;

    //下单人员
    @ExcelIgnore
    @Valid
    @NotNull(message = "数据不能为空")
    private Guest guest;

    //住客人员
    @ExcelIgnore
    @Valid
    private Guest[] guests;

    //是否已入住
    @ExcelIgnore
    private Integer ostate;

    @ExcelIgnore
    @Valid
    private Room room;

}
