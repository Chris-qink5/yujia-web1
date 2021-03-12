package com.woniu.yujiaweb.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RowVO {
    //教练名字
    private String coachName;
    //课程金额
    private Double courseMoney;
    //课程名称
    private String courseName;
    //开课时间
    private Date courseTime;
    //教练id
    private Integer uid;
}
