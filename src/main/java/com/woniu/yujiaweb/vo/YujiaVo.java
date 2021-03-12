package com.woniu.yujiaweb.vo;

import com.woniu.yujiaweb.domain.Yujia;
import lombok.Data;

import java.util.Date;

@Data
public class YujiaVo extends Yujia{

    private Integer current;
    private Integer size;
    private Integer startNumber;
    private Integer endNumber;
    private String username;
}
