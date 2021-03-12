package com.woniu.yujiaweb.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_c_message")
@ApiModel(value="CMessage对象", description="")
public class CMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uId;

    private String headPhoto;

    private String nickname;

    private String username;

    private String site;

    private String description;

    @TableField("idCard")
    private String idCard;

    @TableField("bankCard")
    private String bankCard;

    private String sex;

    private Date birthday;

        @ApiModelProperty(value = "默认全部公开（1），可设置：好友公开（2），保密（0）")
        private Integer secrecy;

    private Integer attentionId;

        @ApiModelProperty(value = "默认余额为0")
        private Double account;


}
