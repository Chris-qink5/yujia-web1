package com.woniu.yujiaweb.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CoachVo extends PageVo{
     private String tel;
     private  String idCard;
     private  double score;
     private  String sex;
     private String nickname;
     private String gymName;
     private Date birthday;
     private String description;
     private double spend;
     private String bankCard;
     private  String courseName;
     private  double courseMoney;
     private String site;
     private String headPhoto;
     private String userHeadPortrait;
}
