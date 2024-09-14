package com.patrick.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 住客表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guest {
    private Integer gid;

    //住客姓名
    @NotBlank(message = "姓名不能为空")
    private String gname;

    //性别
    @NotBlank(message = "性别不能为空")
    private String ggender;

    //住客身份证号
    @NotBlank(message = "身份证不能为空")
    private String gno;

    //住客联系方式
    @NotBlank(message = "手机号")
    private String gphone;

    //住客入住时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Future(message = "start")
    private Date gstart;

    //住客离店时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Future(message = "end")
    private Date gend;

    //住客状态
    private Integer gstate;


    //住客入住房间
    @NotNull(message = "房间不能为空")
    private Integer rid;

    @Valid
    private Room room;

    private String ono;

}
