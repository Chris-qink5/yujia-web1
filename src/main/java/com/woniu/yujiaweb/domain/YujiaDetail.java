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
 * @since 2021-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_yujia_detail")
@ApiModel(value="YujiaDetail对象", description="")
public class YujiaDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer yid;
    private Integer detailId;

    private Double coursePrice;

    private String courseDescribe;

    private Integer cId;

    private Integer uId;

    private Integer plId;
    private String coach;

    private Integer uId;



    private String picture;


}
