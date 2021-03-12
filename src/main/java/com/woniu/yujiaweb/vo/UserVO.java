package com.woniu.yujiaweb.vo;

import lombok.Data;

@Data
public class UserVO {
    private String username;
    private String password;
    //验证码
    private String authCode;
    //注册方式
    private String contact;
    //角色id
    private String radio;

}
