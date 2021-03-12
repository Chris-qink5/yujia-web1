package com.woniu.yujiaweb.vo;

import lombok.Data;


@Data
public class UserInfoVO extends PageVO {
    private Integer id;
    private String username;
    private String sex;
    private String tel;
    private String email;
    private String score;
    private Integer attention;
    private Integer minLength;
    private Integer maxLength;
}
