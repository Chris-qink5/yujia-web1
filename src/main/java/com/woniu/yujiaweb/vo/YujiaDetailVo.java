package com.woniu.yujiaweb.vo;

import lombok.Data;

import java.util.List;

@Data
public class YujiaDetailVo {

    //项目教练
    private String cName;
    //项目学员集合
    private List<String> sName;
    //项目场馆
    private String pName;
    private Double coursePrice;

    private String courseDescribe;


}
