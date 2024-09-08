package com.patrick.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private Integer id;
    private String name;
    private String no;
    private String phone;
    private String addr;
    private Integer aid;
}
