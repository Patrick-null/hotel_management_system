package com.patrick.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    private Integer aid;
    private String username;
    private String password;
    //角色 0:管理员 1:用户
    private Integer role;
    private Info info;
}
