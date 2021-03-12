package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yogagyminfo;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.mapper.YogagyminfoMapper;
import com.woniu.yujiaweb.service.YogagyminfoService;
import com.woniu.yujiaweb.vo.PageVo;
import com.woniu.yujiaweb.vo.YogagyminfoVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
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
 * @since 2021-03-10
 * @since 2021-03-09
 */
@RestController
@RequestMapping("/yogagyminfo")
public class YogagyminfoController {
    @Resource
    private YogagyminfoService yogagyminfoService;
    @Resource
    private YogagyminfoMapper yogagyminfoMapper;


    @GetMapping("page2")
    public Result find2Page(PageVo pageVO){
        Page<Yogagyminfo> filmPage = new Page<>(pageVO.getCurrent(), pageVO.getSize());
        IPage<Yogagyminfo> page = yogagyminfoService.page(filmPage, null);
        return new Result(true,StatusCode.OK,"分页查询成功",page);
    }

    @GetMapping("condition")
    public Result findByCondition(YogagyminfoVO yogagyminfoVO){
        Page<Yogagyminfo> page = yogagyminfoService.findByCondition(yogagyminfoVO);
        return new Result(true, StatusCode.OK,"根据条件分页查询成功",page);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Yogagyminfo film = yogagyminfoService.getById(id);
        return new Result(true,StatusCode.OK,"根据ID查询成功",film);
    }

    @RequestMapping("page")
    @ResponseBody
    public Result findAllCourse(){
        QueryWrapper<Yogagyminfo> wrapper = new QueryWrapper<>();
        wrapper.eq("attention",1);
        List<Yogagyminfo> yogagyminfos = yogagyminfoMapper.selectList(wrapper);
        return new Result(true, StatusCode.OK, "查询关注用户信息成功",yogagyminfos);
    }



    @RequestMapping("attention/{id}")
    @ResponseBody
    public Result attention(@PathVariable Integer id,Yogagyminfo yogagyminfo){
        yogagyminfo.setAttention(1);
        yogagyminfoService.update(yogagyminfo);
        return new Result(true,StatusCode.OK,"关注成功");
    }


    @RequestMapping("deletedAttention/{id}")
    @ResponseBody
    public Result deletedAttention(@PathVariable Integer id,Yogagyminfo yogagyminfo){
        yogagyminfo.setAttention(0);
        yogagyminfoService.update(yogagyminfo);
        return new Result(true,StatusCode.OK,"取消关注成功");
    }


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

