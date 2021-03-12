package com.woniu.yujiaweb.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，并自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "如果用户没填写，系统自动生成一个")
    private String username;

    @ApiModelProperty(value = "需要对密码加密，不能明文")
    private String password;

    @ApiModelProperty(value = "密码加密")
    private String salt;

    @ApiModelProperty(value = "电话号码不能为null，注册需要使用电话号码")
    private String tel;

    @ApiModelProperty(value = "邮箱可以为null")
    private String email;


        @ApiModelProperty(value = "默认为0,1为删除")
        @TableLogic
        private Integer deleted; //逻辑删除

        @TableField(fill = FieldFill.INSERT)
        private Date gmtCreate;

        @TableField(fill = FieldFill.INSERT_UPDATE)
        private Date gmtModifified;

    @ApiModelProperty(value = "版本号，格式：version_数字|从1开始记")
    // TODO 版本号控制
    private Date version;

    private String sex;


    @ApiModelProperty(value = "积分默认为0")
    private Integer score;

    private Integer attention;
}
