package com.woniu.yujiaweb.vo;

import lombok.Data;

@Data
public class YogagyminfoVO extends PageVo {
    private Integer minLength;
    private Integer maxLength;
    private Integer id;
    private Integer uId;
    private String gymAddress;
    private String gymName;
    private String gymTel;
    private String gymDescription;
    private String gymPhoto;
    private Integer attention;

}
