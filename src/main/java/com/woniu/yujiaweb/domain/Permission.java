package com.woniu.yujiaweb.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("t_permission")
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

        @ApiModelProperty(value = "主键")
        @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        @ApiModelProperty(value = "权限名称")
        private String element;

        @ApiModelProperty(value = "权限的等级")
        private Integer perssionLevel;

        @ApiModelProperty(value = "权限的等级")
        private String url;

        @ApiModelProperty(value = "上一级权限的id")
        private Integer pid;


}
