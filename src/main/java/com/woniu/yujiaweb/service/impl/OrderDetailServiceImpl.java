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
 * @author yym
 * @since 2021-03-11
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<OrderDetail> findOrderDetail(String username) {
        List<OrderDetail> orderDetails = orderDetailMapper.findOrderDetail(username);
        return orderDetails;
    }
}
