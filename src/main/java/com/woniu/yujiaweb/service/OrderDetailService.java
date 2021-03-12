package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.OrderDetailVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
public interface OrderDetailService extends IService<OrderDetail> {

    //查询所有的订单信息
    public Page<OrderDetailVo> queryAllDetail(OrderDetailVo orderDetailVo);

    //删除指定的订单信息
    public boolean delOrder(Integer id);


}
