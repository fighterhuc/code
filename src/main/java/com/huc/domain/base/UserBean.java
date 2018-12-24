package com.huc.domain.base;

import lombok.Data;

@Data
public class UserBean {
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //科室
    private String department;
    //电话
    private String phone;
}
