package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.woniu.yujiaweb.vo.YuJiaVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

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

    @ApiOperation(value = "查看课程详情",notes = "<span style='color:red;'>点击查看后，显示课程介绍</span>")
    //@ApiImplicitParams用于描述接口参数
    @ApiResponses({
            @ApiResponse(code =20000,message = "详情查询成功"),

    })
    @ApiImplicitParams({
            //dataType:参数类型
            //paramType:参数由哪里获取     path->从路径中获取，query->?传参，body->ajax请求
            @ApiImplicitParam(name = "yuJiaVO",value = "瑜伽vo",dataType = "YuJiaVO",example = "{yid：1，uname:xxx}"),


    })
    @PostMapping("/findDetail")
    @ResponseBody
    public Result findDetail(@RequestBody YuJiaVO yuJiaVO){
        System.out.println("进入详情查询，课程id："+yuJiaVO.getYid());
        QueryWrapper<YujiaDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("yid",yuJiaVO.getYid());
        List<YujiaDetail> list = yujiaDetailService.list(queryWrapper);
        return new Result(true, StatusCode.OK,"详情查询成功",list);
    }

    //查询众筹项目的详情
    @GetMapping("detail/{id}")
    public Result getDetail(@PathVariable Integer id){
        YujiaDetailVo yujiaDetailVo = yujiaDetailService.queryYijiaDetail(id);
        return new Result(true, StatusCode.OK,"查询成功",yujiaDetailVo);
    }

}

