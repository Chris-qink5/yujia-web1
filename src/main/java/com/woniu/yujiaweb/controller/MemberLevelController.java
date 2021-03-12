package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.MemberLevel;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.MemberLevelService;
import com.woniu.yujiaweb.vo.MemberUserVo;
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
 * @since 2021-03-10
 */
@RestController
@RequestMapping("/memberLevel")
public class MemberLevelController {

    @Resource
    private MemberLevelService memberLevelService;

    //查询所有的会员等级
    @GetMapping("findAllLevel")
    public Result findAllMemberLevel(){
        List<MemberLevel> allLevel = memberLevelService.findAllLevel();
        return new Result(true, StatusCode.OK,"查询成功",allLevel);
    }

    //修改会员等级信息
    @PostMapping("updateLevel")
    public Result updateLevel(@RequestBody MemberLevel memberLevel){
        boolean b = memberLevelService.updateLevel(memberLevel);
        if(b){
            return new Result(true,StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }

    //删除会员等级信息
    @PostMapping("del/{id}")
    public Result delLevle(@PathVariable Integer id){
        boolean b = memberLevelService.delLevelByid(id);
        if(b){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(true,StatusCode.OK,"删除失败");
    }

    //新增会员等级信息
    @PostMapping("addLevel")
    public Result addLevel(@RequestBody MemberLevel memberLevel){
        boolean b = memberLevelService.addLevel(memberLevel);
        if(b){
            return new Result(true,StatusCode.OK,"新增成功");
        }
        return new Result(false,StatusCode.ERROR,"新增失败");
    }

    //查询所有的会员信息
    @GetMapping("findAllMemberInfo")
    public Result  findAllMemberInfo(MemberUserVo memberUserVo){
        IPage<MemberUserVo> aLlMember = memberLevelService.findALlMember(memberUserVo);
        System.out.println(aLlMember);
        return new Result(true,StatusCode.OK,"查询成功",aLlMember);
    }

}

