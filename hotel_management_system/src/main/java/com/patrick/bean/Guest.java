package com.patrick.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String gname;

    //性别
    private String ggender;

    //住客身份证号
    private String gno;

    //住客联系方式
    private String gphone;

    //住客入住时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gstart;

    //住客离店时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date gend;

    //住客状态
    private Integer gstate;


    //住客入住房间
    private Integer rid;

    private Room room;

}
