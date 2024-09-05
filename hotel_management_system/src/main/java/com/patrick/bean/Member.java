package com.patrick.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 会员表
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    private Integer mid;
    private String mname;
    private String mgender;
    private String mno;
    private String mphone;
    private Date mstart;
    private Date mend;
}
