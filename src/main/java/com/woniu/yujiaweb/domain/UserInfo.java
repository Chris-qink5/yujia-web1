package com.woniu.yujiaweb.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("t_user_info")
@ApiModel(value="UserInfo对象", description="")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uId;

    @TableField("bankCard")
    private String bankCard;

    private Double score;

    private Double spend;

        @ApiModelProperty(value = "用户没有填写，默认保密")
        private String sex;

        @ApiModelProperty(value = "默认为0保密，1为公开")
        private Integer secret;

        @ApiModelProperty(value = "默认为0好友公开，1为全部公开")
        private Integer open;

    private String userHeadPortrait;

    private String nickname;


}
