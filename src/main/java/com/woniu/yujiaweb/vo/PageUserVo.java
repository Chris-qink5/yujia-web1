package com.woniu.yujiaweb.vo;

import lombok.Data;
@Data
public class PageUserVo extends PageVO{
    private Integer id;
    private String username;
    private String password;
    private String tel;
    private String roleName;
    private Integer uid;
    private Integer rid;
    private String email;
    private String sex;
    //昵称
    private String nickname;
    //用户的积分
    private double score;
    //用户查询的开始积分
    private String startScore;
    //用户查询的终止积分
    private String endScore;
    //用户的消费金额
    private double spend;
    private Integer deleted;
}
