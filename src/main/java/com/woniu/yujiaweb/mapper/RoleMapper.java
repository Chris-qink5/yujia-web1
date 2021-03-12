package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniu.yujiaweb.vo.RoleVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author qk
 * @since 2021-03-06
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    //根据用户id查询其对应的角色id
    @Select("SELECT r.role_name FROM t_user_role as ur JOIN t_role as r on r.id = ur.rid WHERE uid = #{id}")
    public String findRoleById(Integer id);

    //根据用户id查询去对应的角色名称
    @Select("SELECT r.role_name " +
            "FROM t_user_role AS ur " +
            "JOIN t_user as u " +
            "ON u.id = ur.uid " +
            "JOIN t_role AS r " +
            "ON r.id = ur.rid " +
            "WHERE u.username='#{username}'")
    public String findRolenameByUserName(String username);

    //根据用户id修改其角色
    @Update("UPDATE t_user_role AS ur SET ur.rid = (SELECT r.id FROM t_role as r  WHERE r.role_name=#{uproleName}) WHERE ur.uid = #{id}")
    public Integer updateRole(RoleVo roleVo);
}
