package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.OrderDetailService;
import com.woniu.yujiaweb.vo.OrderDetailVo;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-08
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Resource
    private OrderDetailService orderDetailService;
    @GetMapping("/findOrderDetailC")
    @ResponseBody
    public Result findOrderDetailC(String coachName){
        return new Result(true, StatusCode.OK,"查询订单成功",orderDetailService.findOrderDetailC(coachName));
    }



    //查询所有的订单信息
    @GetMapping("queryAllOrder")
    public Result queryAllOrder(OrderDetailVo orderDetailVo){
        Page<OrderDetailVo> orderDetailVoPage = orderDetailService.queryAllDetail(orderDetailVo);

        return new Result(true, StatusCode.OK,"查询订单信息成功",orderDetailVoPage);
    }

    //删除所指定的订单信息
    @PostMapping("delOrder/{id}")
    public Result delOrderBy(@PathVariable Integer id){
        //先删除订单详情的数据
        boolean b = orderDetailService.delOrder(id);
        //删除
        if(b){
            return new Result(true,StatusCode.OK,"成功删除订单");
        }
        return new Result(false,StatusCode.ERROR,"删除订单失败");
    }
    @GetMapping("/findOrderDetail")
    @ResponseBody
    public Result findOrderDetail(String username){
        return new Result(true, StatusCode.OK,"查询订单成功",orderDetailService.findOrderDetail(username));
    }
}

