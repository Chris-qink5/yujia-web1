package com.woniu.yujiaweb.controller;


import com.woniu.yujiaweb.domain.YujiaDetail;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.YujiaDetailService;
import com.woniu.yujiaweb.vo.YujiaDetailVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qk
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/yujiaDetail")
public class YujiaDetailController {

    @Resource
    private YujiaDetailService yujiaDetailService;

    //查询众筹项目的详情
    @GetMapping("detail/{id}")
    public Result getDetail(@PathVariable Integer id){
        YujiaDetailVo yujiaDetailVo = yujiaDetailService.queryYijiaDetail(id);
        return new Result(true, StatusCode.OK,"查询成功",yujiaDetailVo);
    }

}

