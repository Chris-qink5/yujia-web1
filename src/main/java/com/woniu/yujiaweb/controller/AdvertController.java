package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.Advert;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.AdvertService;
import com.woniu.yujiaweb.vo.PageVO;

import org.apache.ibatis.executor.ResultExtractor;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@RequestMapping("/advert")
public class AdvertController {

    @Resource
    private AdvertService advertService;
    //查询所有的广告信息
    @GetMapping("allAdvert")
    public Result getAllAdvert(PageVO pageVo){
        IPage<Advert> allAdver = advertService.getAllAdver(pageVo);
        return new Result(true, StatusCode.OK,"查询广告内容成功",allAdver);
    }

    //删除指定的广告信息
    @PostMapping("del/{id}")
    public Result delAdvertById(@PathVariable Integer id){
        boolean b = advertService.delAdvert(id);
        if(b){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    //修改指定的广告信息
    @PostMapping("update")
    public Result updateAdvert(@RequestBody Advert advert){
        boolean b = advertService.updateAdvert(advert);
        if(b){
            return new Result(true,StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }

    //新增广告信息
    @PostMapping("addAdvert")
    public Result addAdvert(@RequestBody Advert advert){
        boolean b = advertService.addAdvert(advert);
        if(b){
            return new Result(true,StatusCode.OK,"新增广告成功");
        }
        return new Result(false,StatusCode.ERROR,"删除广告成功");
    }
}

