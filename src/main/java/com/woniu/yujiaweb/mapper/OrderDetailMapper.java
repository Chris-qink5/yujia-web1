package com.woniu.yujiaweb.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.OrderDetailVo;
import com.woniu.yujiaweb.vo.PageGymVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-12
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    @Select("select  od.* " +
            "from t_order_detail od " +
            "where od.coach_name=#{coachName}")
    List<OrderDetail> findOrderDetailC(String coachName);

 @Select("SELECT uo.id,uo.uname,uo.cname,c.course_name,c.course_money,od.create_time,od.status FROM " +
            "t_user_order AS uo " +
            "JOIN t_order_detail AS od " +
            "on uo.id = od.order_id " +
            "JOIN t_course AS c " +
            "ON c.id = uo.course_id " +
            "${ew.customSqlSegment}")
    public List<OrderDetailVo> selectAllOrderDetail(Page<OrderDetailVo> page,@Param(Constants.WRAPPER) QueryWrapper<OrderDetailVo> queryWrapper);
}
