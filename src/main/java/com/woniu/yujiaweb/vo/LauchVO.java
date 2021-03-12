package com.woniu.yujiaweb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LauchVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseName ;
    private String desc ;
    private String[] cities ;
    private Date date1;
    private Date date1a;
    private double coursePrice;
    private Integer joinerTar;
    private String sponsor;
}
