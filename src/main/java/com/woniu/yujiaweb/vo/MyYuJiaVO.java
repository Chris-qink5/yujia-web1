package com.woniu.yujiaweb.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyYuJiaVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String courseName;
    private String sponsor;
    private Date starTime;
    private Date endTime;
    private double coursePrice;
    private String courseDescribe;
    private String coach;
}
