package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.OrderDetail;
import com.woniu.yujiaweb.mapper.OrderDetailMapper;
import com.woniu.yujiaweb.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.OrderDetailVo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    private OrderDetailMapper orderDetailMapper;
    //查询所有订单信息
    @Override
    public Page<OrderDetailVo> queryAllDetail(OrderDetailVo orderDetailVo) {
        Page<OrderDetailVo> orderDetailPage = new Page<>(orderDetailVo.getCurrent(),orderDetailVo.getSize());

        QueryWrapper<OrderDetailVo> orderDetailVoQueryWrapper = new QueryWrapper<>();
        if(!ObjectUtils.isEmpty(orderDetailVo.getCname())){
            orderDetailVoQueryWrapper.like("uo.cname",orderDetailVo.getCname());
        }
        List<OrderDetailVo> orderDetails = orderDetailMapper.selectAllOrderDetail(orderDetailPage, orderDetailVoQueryWrapper);
        System.out.println("查询出来的数据" + orderDetails);
        orderDetailPage.setRecords(orderDetails);
        return orderDetailPage;
    }
    //删除指定的订单信息
    @Override
    public boolean delOrder(Integer id) {
        int i = orderDetailMapper.deleteById(id);
        if(i > 0){
            return true;
        }
        return false;
    }


}
