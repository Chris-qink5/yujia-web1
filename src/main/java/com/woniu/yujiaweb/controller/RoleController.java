package com.woniu.yujiaweb.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.Role;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.RoleService;

import com.woniu.yujiaweb.vo.PageVO;
import com.woniu.yujiaweb.vo.RoleVo;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;
    //获取所有的角色
    @GetMapping("findAllRole")
    public Result findAllRole(PageVO pageVo){
        IPage<Role> allRole = roleService.findAllRole(pageVo);
        return new Result(true, StatusCode.OK,"查询所有角色成功",allRole);
    }
    //获取所有的角色名称，用于角色的修改
    @GetMapping("findAllRoleName")
    public Result findAllRoleName(Role role){
        List<Role> allRole = roleService.findAllRoleName();
        return new Result(true, StatusCode.OK,"查询所有角色成功",allRole);
    }
    //查询对应用户所拥有的角色id
    @GetMapping("findRoleById/{id}")
    public  Result findRoleById(@PathVariable Integer id){
        //将用户对应的角色id返回到前端
        String roleName  = roleService.findRoleById(id);
        return new Result(true,StatusCode.OK,"查询成功",roleName);
    }
    //添加角色
    @PostMapping("addRole")
    public Result addRole(@RequestBody Role role){
        System.out.println("进来此方法");
        System.out.println(role);
        boolean b = roleService.addRole(role);
        if(b){
            return new Result(true,StatusCode.OK,"添加成功");
        }
        return new Result(false,StatusCode.ERROR,"添加失败");
    }

    //删除角色信息
    @PostMapping("delRole/{id}")
    public Result delPostById(@PathVariable Integer id){
        boolean b = roleService.delRole(id);
        if(b){
            return new Result(true,StatusCode.OK,"删除成功");
        }
        return new Result(false,StatusCode.ERROR,"删除失败");
    }

    //修改角色
    @PostMapping("updateRole")
    public Result updateRole(@RequestBody RoleVo roleVo){
        boolean b = roleService.updateRole(roleVo);
        if(b){
            return new Result(true,StatusCode.OK,"修改成功");
        }
        return new Result(false,StatusCode.ERROR,"修改失败");
    }

}

