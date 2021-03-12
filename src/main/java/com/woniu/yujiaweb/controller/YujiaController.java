package com.woniu.yujiaweb.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Yujia;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.YujiaService;
import com.woniu.yujiaweb.vo.PageVo;
import com.woniu.yujiaweb.vo.YujiaVo;
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
@RequestMapping("/yujia")
public class YujiaController {

    //管理员对众筹项目进行管理
    @Resource
    private YujiaService yujiaService;

    //查询所有的瑜伽项目
    @GetMapping("queryAllCF")
    public Result findAllCj(YujiaVo yujiaVo){
        Page<YujiaVo> allCJ = yujiaService.findAllCJ(yujiaVo);
        return new Result(true, StatusCode.OK,"成功查询所有的众筹项目",allCJ);
    }

    //对所选中的项目进行修改
    @PostMapping("update")
    public Result updateCF(@RequestBody YujiaVo yujiaVo){
        boolean b = yujiaService.updateCj(yujiaVo);
        if(b){
            return new Result(true,StatusCode.OK,"修改项目信息成功");
        }
        return new Result(false,StatusCode.ERROR,"修改项目信息失败");
    }

    //对所选中的项目进行删除
    @PostMapping("delCF/{id}")
    public Result delCF(@PathVariable Integer id){
        boolean b = yujiaService.delCJ(id);
        if(b){
            return new Result(true,StatusCode.OK,"成功删除订单");
        }
        return new Result(false,StatusCode.ERROR,"删除订单失败");
    }

    //对未审核的众筹项目进行审核 将0变为1
    @PostMapping("aud/{id}")
    public Result aud(@PathVariable Integer id){
        boolean aud = yujiaService.aud(id);
        if(aud){
            return new Result(true,StatusCode.OK,"审核成功");
        }
        return new Result(false,StatusCode.ERROR,"审核失败");
    }

}

