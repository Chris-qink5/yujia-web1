package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-12
 */
public interface OrderDetailService extends IService<OrderDetail> {

    List<OrderDetail> findOrderDetailC(String coachName);
}
