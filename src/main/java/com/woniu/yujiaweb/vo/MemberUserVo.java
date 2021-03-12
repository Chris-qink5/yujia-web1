package com.woniu.yujiaweb.vo;

import lombok.Data;
//用户会员等级信息
@Data
public class MemberUserVo extends PageVO{
    private Integer id;
    private String username;
    private Double score;
    private String levelName;
    private Double spend;
    private String tel;
}
