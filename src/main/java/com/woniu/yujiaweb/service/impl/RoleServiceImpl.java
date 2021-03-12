package com.woniu.yujiaweb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniu.yujiaweb.domain.Role;
import com.woniu.yujiaweb.domain.User;
import com.woniu.yujiaweb.mapper.RoleMapper;
import com.woniu.yujiaweb.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniu.yujiaweb.vo.PageVO;
import com.woniu.yujiaweb.vo.RoleVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    //查询所有的角色信息
    @Override
    public IPage<Role> findAllRole(PageVO pageVo) {
        Page<Role> rolePage = new Page<>(pageVo.getCurrent(),pageVo.getSize());
        IPage<Role> allRole = roleMapper.selectPage(rolePage, null);
        return allRole;
    }

    //查询所有的角色名称
    @Override
    public List<Role> findAllRoleName() {
        List<Role> roles = roleMapper.selectList(null);
        return roles;
    }

    //新增角色信息
    @Override
    public boolean addRole(Role role) {
        int insert = roleMapper.insert(role);
        if(insert > 0){
            return  true;
        }
        return false;
    }

    @Override
    public boolean delRole(Integer id) {
        int i = roleMapper.deleteById(id);
        if(i > 0){
            return true;
        }
        return false;
    }

    //查询对应用户id所对应的角色id
    @Override
    public String findRoleById(Integer id) {
       String roleName = roleMapper.findRoleById(id);
        return roleName;
    }
    //根据用户姓名查询用户的角色名称
    @Override
    public String findRolenameByUserName(String username) {
        String rolename = roleMapper.findRolenameByUserName(username);
        return rolename;
    }

    //修改用户的角色
    @Override
    public boolean updateRole(RoleVo roleVo) {
        Integer integer = roleMapper.updateRole(roleVo);
        if(integer > 0){
            return true;
        }
        return false;
    }

}
