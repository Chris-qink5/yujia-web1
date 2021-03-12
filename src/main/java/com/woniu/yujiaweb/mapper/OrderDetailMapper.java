package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yym
 * @since 2021-03-11
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("select  od.* " +
            "from t_order_detail od " +
            "where od.username=#{username}")
    List<OrderDetail> findOrderDetail(String username);
}
