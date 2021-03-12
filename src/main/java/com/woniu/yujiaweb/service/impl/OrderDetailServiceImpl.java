package com.woniu.yujiaweb.service.impl;

import com.woniu.yujiaweb.domain.OrderDetail;
import com.woniu.yujiaweb.mapper.OrderDetailMapper;
import com.woniu.yujiaweb.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-12
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetail> findOrderDetailC(String coachName) {
        List<OrderDetail> orderDetails = orderDetailMapper.findOrderDetailC(coachName);
        return orderDetails;
    }
}
