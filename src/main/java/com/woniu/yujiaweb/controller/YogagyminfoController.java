package com.woniu.yujiaweb.controller;


import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.YogagyminfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/yogagyminfo")
public class YogagyminfoController {

    @Resource
    private YogagyminfoService yogagyminfoService;

    @PostMapping("updateGym")
    public Result updateGym(@RequestBody Yogagyminfo yogagyminfo){
        System.out.println("修改方法");
        boolean b = yogagyminfoService.updateGym(yogagyminfo);
        if(b){
            return new Result(true, StatusCode.OK,"成功修改场馆信息");
        }
        return new Result(false, StatusCode.ERROR,"修改场馆信息失败");
    }


}

