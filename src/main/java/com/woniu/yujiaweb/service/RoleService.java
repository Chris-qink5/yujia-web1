package com.woniu.yujiaweb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.woniu.yujiaweb.domain.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniu.yujiaweb.vo.PageVO;
import com.woniu.yujiaweb.vo.RoleVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
public interface RoleService extends IService<Role> {

    //分页查询所有的角色信息
    public IPage<Role> findAllRole(PageVo pageVo);

    //查看所有的角色名称
    public List<Role> findAllRoleName();

    //添加新的角色信息
    public boolean addRole(Role role);

    //删除角色信息
    public boolean delRole(Integer id);

    //查询用户对应的角色名称
    public String findRoleById(Integer id);

    //根据用户姓名查询角色姓名
    public String findRolenameByUserName(String username);

    //修改用户的角色
    public boolean updateRole(RoleVo roleVo);
}
