package com.patrick.bean;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {
    private Integer id;
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "性别不能为空")
    private String gender;
    @NotNull(message = "身份证号不能为空")
    private String no;
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "地址不能为空")
    private String addr;
    @NotNull(message = "123不能为空")
    private Integer aid;
    @Valid
    @NotNull(message = "456不能为空")
    private List<String> oid;
}
