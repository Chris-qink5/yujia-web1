package com.woniu.yujiaweb.mapper;

import com.woniu.yujiaweb.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.bytebuddy.description.field.FieldDescription;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
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
public interface PermissionMapper extends BaseMapper<Permission> {

    //通过角色id查询某个角色所拥有的权限
    @Select("SELECT p.* FROM " +
            "t_permission as p " +
            "JOIN t_role_permission AS rp " +
            "ON rp.pid = p.id " +
            "WHERE rp.rid=#{rid}")
    public List<Permission> getAllPermissionByUserId(Integer rid);

    //通过角色id查询某个角色所拥有的权限
    @Select("SELECT p.* FROM " +
            "t_permission as p ")
    public List<Permission> getAllPermission();

    //查询该用户所拥有的权限id
    @Select("SELECT p.id FROM " +
            "t_permission as p " +
            "JOIN t_role_permission AS rp " +
            "on rp.pid = p.id " +
            "JOIN t_role AS r " +
            "ON r.id = rp.rid " +
            "WHERE r.id = #{id}")
    List<Permission> getHasPermissionIds(Integer id);


    //删除用户所对应的权限
    @Delete("DELETE FROM t_role_permission WHERE rid = #{id}")
    public void delPermissionByid(Integer id);

    //根据权限id查询对应的信息
    @Select("SELECT * FROM t_permission WHERE id=#{pid}")
    public Permission queryPermissionByPid(Integer pid);

    //添加用户选择权限
    @Insert("INSERT t_role_permission(rid,pid) VALUES(#{rid},#{pid})")
    public void addPermissionByrid(Integer rid, Integer pid);
}
