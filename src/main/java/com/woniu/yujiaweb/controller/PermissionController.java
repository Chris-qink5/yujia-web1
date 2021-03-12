package com.woniu.yujiaweb.controller;


import com.woniu.yujiaweb.domain.Permission;
import com.woniu.yujiaweb.dto.Result;
import com.woniu.yujiaweb.dto.StatusCode;
import com.woniu.yujiaweb.service.PermissionService;
import com.woniu.yujiaweb.vo.PermissionVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
@RequestMapping("/permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;
    //首页展示该管理员登录进来自己所拥有的权限 展示在左边菜单
    @GetMapping("/getAllPermission")
    public Result getAllPermission(){
        Integer rid = 4;
        List<Permission> allPermissions = permissionService.getAllPermissionByUserId(rid);
        return new Result(true, StatusCode.OK,"查询成功",allPermissions);
    }

    //获取后台所有权限菜单
    @GetMapping("/getAdminAllPermission")
    public Result getAdminAllPermission(){
        System.out.println("执行后台所有权限菜单方法");
        List<Permission> allPermissions = permissionService.getAllPermission();
        return new Result(true, StatusCode.OK,"查询成功",allPermissions);
    }

    //获取对应的管理员权限菜单id
    @GetMapping("/AllPermission/{id}")
    public Result getAllPermissionIdsById(@PathVariable Integer id){
        List<Integer> allPermissionIds = permissionService.getHasPermissionIds(id);
        return new Result(true, StatusCode.OK,"查询成功",allPermissionIds);
    }

    //修改对应管理员的权限
    @PostMapping("setPermission")
    public Result setPermissionByid(@RequestBody PermissionVo permissionVo){
        //先删除该管理员对应的权限
        permissionService.delPermissionByid(permissionVo.getRid());
        //在添加此管理员对应的权限
        permissionService.addPermissionByid(permissionVo);
        return  new Result(true,StatusCode.OK,"修改权限成功");
    }

}

