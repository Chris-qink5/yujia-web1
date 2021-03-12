package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.woniu.yujiaweb.domain.CMessage;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.CMessageService;
import com.woniu.yujiaweb.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@Controller
@RequestMapping("/cMessage")
public class CMessageController {
    @Resource
    private CMessageService cmessageService;


    @RequestMapping("findOneById")
    @ResponseBody
    public Result findOneById(int cid){
        System.out.println("进入find"+cid);
        QueryWrapper<CMessage> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",cid);
        List<CMessage> list = cmessageService.list(wrapper);
        return new Result(true,StatusCode.OK,"详细信息查询成功",list);
    }

    @RequestMapping("findAllCMessage")
    public Result findAllUser(){
        return new Result(true, StatusCode.OK, "查询所有信息成功", cmessageService.list(null));
    }


    @PostMapping("/updateCMessage")
    @ResponseBody
    public Result updateCMessage(@RequestBody CMessage cmessage){
        return new Result(true,StatusCode.OK,"修改成功",cmessageService.updateCMessage(cmessage));
    }




}

