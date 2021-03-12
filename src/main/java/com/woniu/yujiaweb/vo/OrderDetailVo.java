package com.woniu.yujiaweb.vo;

import lombok.Data;

import java.util.Date;
@Data
public class OrderDetailVo extends PageVO {
    private Integer id;
    private String uname;
    private String cname;
    private String courseName;
    private double courseMoney;
    private Date createTime;
    private int status;
}
