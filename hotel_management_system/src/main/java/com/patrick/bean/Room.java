package com.patrick.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

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
    @NotNull(message = "房间号不能为空")
    private Integer rno;

    //房间照片
    @NotBlank(message = "房间照片不能为空")
    private String ravatar;

    //房间类型
    @NotBlank(message = "房间类型不能为空")
    private String rtype;

    //房间价格
    @NotNull(message = "房间价格不能为空")
    private BigDecimal rprice;

    //房间状态
    private Integer rstate;

    //房间人员
    private List<Guest> guestList;

    //房间设施
    private List<Facility> facilityList;
}
