package com.woniu.yujiaweb.controller;


import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.OrderDetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-12
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {
    @Resource
    private OrderDetailService orderDetailService;
    @GetMapping("/findOrderDetailC")
    @ResponseBody
    public Result findOrderDetail(String coachName){
        return new Result(true, StatusCode.OK,"查询订单成功",orderDetailService.findOrderDetailC(coachName));
    }
}

