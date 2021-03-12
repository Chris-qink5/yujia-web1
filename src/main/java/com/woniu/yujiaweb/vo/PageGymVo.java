package com.woniu.yujiaweb.vo;

import lombok.Data;
import org.apache.catalina.User;

import java.util.List;

//场馆的vo类
@Data
public class PageGymVo extends PageVo{
    private Integer id;
    private String username;
    //场馆电话
    private String gymTel;
    //场馆邮箱
    private String email;
    //场馆地址
    private String gymAddress;
    //场馆秒速
    private String gymDescription;
    //场馆照片
    private String gymPhoto;
    //场馆名称
    private String gymName;
}
