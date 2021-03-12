package com.woniu.yujiaweb.vo;

import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

@Data
public class PageVo {
    private Integer current;
    private Integer size;
    private Integer pages;
    private Integer total;
    private List<User> recods;
}
