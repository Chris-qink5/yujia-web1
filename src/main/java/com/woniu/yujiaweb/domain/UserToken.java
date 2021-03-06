package com.woniu.yujiaweb.domain;

import lombok.Data;

@Data
public class UserToken {
    private String username;
    private Long exp;
}
