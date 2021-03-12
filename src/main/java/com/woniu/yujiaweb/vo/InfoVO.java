package com.woniu.yujiaweb.vo;

import lombok.Data;

@Data
public class InfoVO {
    private String username;
    private String nickname;
    private String userHeadPortrait;
    private String sex;
    private String bankCard;
    //是否保密
    private Integer secret;
    //是否公开
    private Integer open;
}
