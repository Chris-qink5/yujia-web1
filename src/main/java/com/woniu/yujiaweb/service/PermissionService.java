package com.woniu.yujiaweb.service;

import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.PermissionVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface PermissionService extends IService<Permission> {

    //查询某个角色的所有权限菜单()
    public List<Permission> getAllPermissionByUserId(Integer rid);

    //查询后台的所有权限菜单()
    public List<Permission> getAllPermission();
    //查询用户所拥有的权限菜单id
    public List<Integer> getHasPermissionIds(Integer id);

    //查询用户所拥有的权限名称
    public List<String> getHasPermissionElements(Integer id);

    //删除用户所拥有的权限
    public void delPermissionByid(Integer id);

    //添加管理员的权限
    public void addPermissionByid(PermissionVo permissionVo);
 }
