package com.patrick.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "性别不能为空")
    private String gender;

    @NotBlank(message = "身份证号不能为空")
    private String no;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "地址不能为空")
    private String addr;
    //头像
    private String avatar;

    private Integer aid;

    private Admin admin;

    private List<String> oid;
}
